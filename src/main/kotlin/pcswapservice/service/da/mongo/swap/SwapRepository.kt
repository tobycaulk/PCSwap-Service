package pcswapservice.service.da.mongo.swap

import org.springframework.data.mongodb.repository.MongoRepository
import pcswapobjects.swap.Swap
import pcswapobjects.swap.SwapState

interface SwapRepository: MongoRepository<Swap, String> {
    fun findBySwapId(swapId: String): Swap?
    fun findBySellerUserId(sellerUserId: String): List<Swap>?
    fun findAllByOrderByCreateDateDesc(): List<Swap>?
    fun findBySwapState(swapState: SwapState): List<Swap>
}