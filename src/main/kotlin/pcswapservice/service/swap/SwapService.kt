package pcswapservice.service.swap

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pcswapservice.model.request.swap.*
import pcswapservice.model.response.swap.*
import pcswapservice.model.swap.Swap
import pcswapservice.service.da.mongo.SwapRepository
import java.util.*

@Service
class SwapService @Autowired constructor(var swapRepository: SwapRepository) {

    fun createSwap(request: CreateSwapRequest): CreateSwapResponse {
        var swapId = UUID.randomUUID().toString()

        var swap = Swap(
                id=null,
                swapId=swapId,
                sellItem=request.sellItem,
                tradeForItems=request.tradeForItems,
                sellerUserId=request.sellerUserId,
                createDate=Date())
        swapRepository.insert(swap)

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

    fun getRecentSwapListings(request: GetRecentSwapListingsRequest) = GetRecentSwapListingsResponse(swapRepository.findAllOrderByCreateDateDesc())
}