package pcswapservice.controller.swap

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pcswapservice.controller.ControllerUtil
import pcswapservice.service.swap.SwapService
import pcswapobjects.request.swap.*
import pcswapobjects.response.swap.*
import pcswapobjects.request.*

@RestController
class SwapController @Autowired constructor(var swapService: SwapService, var util: ControllerUtil) {

    @PostMapping("/createSwap")
    fun createSwap(@RequestBody request: RequestBase<CreateSwapRequest>) =
            util.processRequest<CreateSwapRequest, CreateSwapResponse>(request, { swapService.createSwap(request) })

    @PostMapping("/getSwap")
    fun getSwap(@RequestBody request: RequestBase<GetSwapRequest>) =
            util.processRequest<GetSwapRequest, GetSwapResponse>(request, { swapService.getSwap(request.payload) })

    @PostMapping("/getSwapItem")
    fun getSwapItem(@RequestBody request: RequestBase<GetSwapItemRequest>) =
        util.processRequest<GetSwapItemRequest, GetSwapItemResponse>(request, { swapService.getSwapItem(request.payload) })

    @PostMapping("/deleteSwap")
    fun deleteSwap(@RequestBody request: RequestBase<DeleteSwapRequest>) =
            util.processRequest<DeleteSwapRequest, DeleteSwapResponse>(request, { swapService.deleteSwap(request.payload) })

    @PostMapping("/getSwapListingsBySeller")
    fun getSwapListingsBySeller(@RequestBody request: RequestBase<GetSwapListingsBySellerRequest>) =
            util.processRequest<GetSwapListingsBySellerRequest, GetSwapListingsBySellerResponse>(request, { swapService.getSwapListingsBySeller(request.payload) })

    @PostMapping("/getRecentSwapListings")
    fun getRecentSwapListings(@RequestBody request: RequestBase<GetRecentSwapListingsRequest>) =
            util.processRequest<GetRecentSwapListingsRequest, GetRecentSwapListingsResponse>(request, { swapService.getRecentSwapListings(request.payload) })

    @PostMapping("/offerSwapItem")
    fun offerSwapItem(@RequestBody request: RequestBase<OfferSwapItemRequest>) =
        util.processRequest<OfferSwapItemRequest, OfferSwapItemResponse>(request, { swapService.offerSwapItem(request.payload) })

    @PostMapping("/acceptSwapItemOffer")
    fun acceptSwapItemOffer(@RequestBody request: RequestBase<AcceptSwapItemOfferRequest>) =
            util.processRequest<AcceptSwapItemOfferRequest, AcceptSwapItemOfferResponse>(request, { swapService.acceptSwapItemOffer(request) })

    @PostMapping("/getTotalSwapCount")
    fun getTotalSwapCount(@RequestBody request: RequestBase<GetTotalSwapCountRequest>) =
            util.processRequest<GetTotalSwapCountRequest, GetTotalSwapCountResponse>(request, { swapService.getTotalSwapCount(request.payload) })
}