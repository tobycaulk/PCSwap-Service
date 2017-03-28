package pcswapservice.service.swap

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pcswapservice.model.request.swap.*
import pcswapservice.model.response.swap.*
import pcswapservice.model.swap.Swap
import pcswapservice.model.swap.SwapItem
import pcswapservice.model.user.User
import pcswapservice.service.da.mongo.swap.SwapItemRepository
import pcswapservice.service.da.mongo.swap.SwapRepository
import pcswapservice.service.da.mongo.user.UserRepository
import pcswapservice.service.utilities.generateUUID
import java.util.*

@Service
class SwapService @Autowired constructor(var swapRepository: SwapRepository, var userRepository: UserRepository, var swapItemRepository: SwapItemRepository) {

    fun createSwap(request: CreateSwapRequest): CreateSwapResponse {
        var swapId = generateUUID()

        var tradeForItemIds = arrayListOf<String>()
        for(tradeItem in request.tradeForItems) {
            var tradeForItemId = generateUUID()
            tradeItem.swapItemId = tradeForItemId
            swapItemRepository.save(tradeItem)
            tradeForItemIds.add(tradeForItemId)
        }

        var sellItemId = generateUUID()
        request.sellItem.swapItemId = sellItemId
        swapItemRepository.save(request.sellItem)

        var swap = Swap(
                id=null,
                swapId=swapId,
                sellItem=sellItemId,
                tradeForItems=tradeForItemIds,
                offerItems=arrayListOf<String>(),
                sellerUserId=request.sellerUserId,
                createDate=Date())
        swapRepository.insert(swap)

        var user: User? = userRepository.findByUserId(request.sellerUserId)
        if(user != null) {
            user.sellSwaps.add(swapId)
        }
        userRepository.save(user)

        return CreateSwapResponse(swapId)
    }

    fun getSwap(request: GetSwapRequest) = GetSwapResponse(swapRepository.findBySwapId(request.swapId))

    fun deleteSwap(request: DeleteSwapRequest): DeleteSwapResponse {
        var success = false;

        var swap: Swap? = swapRepository.findBySwapId(request.swapId)
        if(swap != null) {
            swapRepository.delete(swap)
            success = true
        }

        return DeleteSwapResponse(success)
    }

    fun getSwapListingsBySeller(request: GetSwapListingsBySellerRequest) = GetSwapListingsBySellerResponse(swapRepository.findBySellerUserId(request.sellerUserId))

    fun getRecentSwapListings(request: GetRecentSwapListingsRequest) = GetRecentSwapListingsResponse(swapRepository.findAllOrderByCreateDate())

    fun offerSwapItem(request: OfferSwapItemRequest): OfferSwapItemResponse {
        var status = OfferSwapItemResponseStatus.FAILURE

        var swap = swapRepository.findBySwapId(request.swapId)
        if(swap != null) {

            var offerItemId = generateUUID()
            request.swapItem.swapItemId = offerItemId
            swapItemRepository.save(request.swapItem)

            if(swap.offerItems.contains(request.swapItem)) {
                status = OfferSwapItemResponseStatus.ALREADY_OFFERED
            } else {
                swap.offerItems.add(request.swapItem)
                swapRepository.save(swap)
                status = OfferSwapItemResponseStatus.SUCCESS
            }
        }

        return OfferSwapItemResponse(status)
    }
}