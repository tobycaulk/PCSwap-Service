package pcswapservice.service.swap

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pcswapobjects.request.RequestBase
import pcswapobjects.request.swap.CreateSwapRequest
import pcswapobjects.response.swap.CreateSwapResponse
import pcswapobjects.response.swap.DeleteSwapResponse
import pcswapobjects.swap.Swap
import pcswapobjects.swap.SwapState
import pcswapobjects.user.User
import pcswapobjects.request.swap.*
import pcswapobjects.response.swap.*
import pcswapservice.service.da.mongo.swap.SwapItemRepository
import pcswapservice.service.da.mongo.swap.SwapRepository
import pcswapservice.service.da.mongo.user.UserRepository
import pcswapservice.service.user.UserService
import pcswapservice.service.utilities.generateUUID
import java.util.*

@Service
class SwapService @Autowired constructor(
        var swapRepository: SwapRepository,
        var userRepository: UserRepository,
        var swapItemRepository: SwapItemRepository,
        var userService: UserService) {

    fun createSwap(requestBase: RequestBase<CreateSwapRequest>): CreateSwapResponse {
        var request = requestBase.payload
        var swapId = ""

        var userSession = userService.getUserSession(requestBase.sessionId)
        if (userSession != null) {
            swapId = generateUUID()

            var sellerUserId = userSession.userId

            var tradeForItemIds = arrayListOf<String>()
            for(tradeItem in request.tradeForItems) {
                var tradeForItemId = generateUUID()
                tradeItem.swapItemId = tradeForItemId
                tradeItem.userId = sellerUserId
                swapItemRepository.save(tradeItem)
                tradeForItemIds.add(tradeForItemId)
            }

            var sellItemId = generateUUID()
            request.sellItem.swapItemId = sellItemId
            request.sellItem.userId = sellerUserId
            swapItemRepository.save(request.sellItem)

            var swap = Swap(
                    swapId=swapId,
                    sellItem=sellItemId,
                    tradeForItems=tradeForItemIds,
                    offerItems=arrayListOf<String>(),
                    sellerUserId=sellerUserId,
                    createDate=Date(),
                    swapState= SwapState.AVAILABLE)
            swapRepository.insert(swap)

            var user: User? = userRepository.findByUserId(sellerUserId)
            if(user != null) {
                user.sellSwaps.add(swapId)
                userRepository.save(user)
            }
        }

        return CreateSwapResponse(swapId)
    }

    fun getSwap(request: GetSwapRequest) =
            GetSwapResponse(swapRepository.findBySwapId(request.swapId))

    fun getSwapItem(request: GetSwapItemRequest) =
            GetSwapItemResponse(swapItemRepository.findBySwapItemId(request.swapItemId))

    fun deleteSwap(request: DeleteSwapRequest): DeleteSwapResponse {
        var success = false;

        var swap: Swap? = swapRepository.findBySwapId(request.swapId)
        if(swap != null) {
            swapRepository.delete(swap)
            success = true
        }

        return DeleteSwapResponse(success)
    }

    fun getSwapListingsBySeller(request: GetSwapListingsBySellerRequest) =
            GetSwapListingsBySellerResponse(swapRepository.findBySellerUserId(request.sellerUserId))

    fun getRecentSwapListings(request: GetRecentSwapListingsRequest) =
            GetRecentSwapListingsResponse(swapRepository.findAllByOrderByCreateDateDesc())

    fun offerSwapItem(request: OfferSwapItemRequest): OfferSwapItemResponse {
        var status = OfferSwapItemResponseStatus.FAILURE

        var swap = swapRepository.findBySwapId(request.swapId)
        if(swap != null) {
            var offerItemId = generateUUID()
            request.swapItem.swapItemId = offerItemId
            swapItemRepository.save(request.swapItem)

            if(swap.offerItems.contains(offerItemId)) {
                status = OfferSwapItemResponseStatus.ALREADY_OFFERED
            } else {
                swap.offerItems.add(offerItemId)
                swapRepository.save(swap)
                status = OfferSwapItemResponseStatus.SUCCESS
            }
        }

        return OfferSwapItemResponse(status)
    }

    fun acceptSwapItemOffer(requestBase: RequestBase<AcceptSwapItemOfferRequest>): AcceptSwapItemOfferResponse {
        var request = requestBase.payload
        var status = AcceptSwapItemOfferResponseStatus.FAILURE;

        var userSession = userService.getUserSession(requestBase.sessionId)
        if(userSession != null) {
            var swap = swapRepository.findBySwapId(request.swapId)
            if (swap != null) {
                var swapItem = swapItemRepository.findBySwapItemId(request.swapItemId)
                if (swapItem != null) {
                    var seller = userRepository.findByUserId(swap.sellerUserId)
                    var buyer = userRepository.findByUserId(userSession.userId)
                    if (seller != null && buyer != null) {
                        seller.soldSwaps.add(swap.swapId!!)
                        userRepository.save(seller);
                        buyer.boughtSwaps.add(swap.swapId!!)
                        userRepository.save(buyer)

                        swap.swapState = SwapState.SOLD
                        swapRepository.save(swap)

                        status = AcceptSwapItemOfferResponseStatus.SUCCESS
                    }
                }
            }
        }

        return AcceptSwapItemOfferResponse(status)
    }

    fun getTotalSwapCount(request: GetTotalSwapCountRequest) =
            GetTotalSwapCountResponse(swapRepository.findBySwapState(SwapState.SOLD).count())
}