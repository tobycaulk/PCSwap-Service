package pcswapservice.controller.swap

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import pcswapservice.controller.processRequest
import pcswapservice.model.request.RequestBase
import pcswapservice.model.request.swap.*
import pcswapservice.model.response.swap.*
import pcswapservice.service.swap.SwapService

@RestController
class SwapController @Autowired constructor(var swapService: SwapService) {

    @PostMapping("/createSwap")
    fun createSwap(@RequestBody request: RequestBase<CreateSwapRequest>) =
            processRequest<CreateSwapRequest, CreateSwapResponse>(request, { swapService.createSwap(request.payload) })

    @PostMapping("/getSwap")
    fun getSwap(@RequestBody request: RequestBase<GetSwapRequest>) =
            processRequest<GetSwapRequest, GetSwapResponse>(request, { swapService.getSwap(request.payload) })

    @PostMapping("/deleteSwap")
    fun deleteSwap(@RequestBody request: RequestBase<DeleteSwapRequest>) =
            processRequest<DeleteSwapRequest, DeleteSwapResponse>(request, { swapService.deleteSwap(request.payload) })

    @PostMapping("/getSwapListingsBySeller")
    fun getSwapListingsBySeller(@RequestBody request: RequestBase<GetSwapListingsBySellerRequest>) =
            processRequest<GetSwapListingsBySellerRequest, GetSwapListingsBySellerResponse>(request, { swapService.getSwapListingsBySeller(request.payload) })

    @PostMapping("/getRecentSwapListings")
    fun getRecentSwapListings(@RequestBody request: RequestBase<GetRecentSwapListingsRequest>) =
            processRequest<GetRecentSwapListingsRequest, GetRecentSwapListingsResponse>(request, { swapService.getRecentSwapListings(request.payload) })
}