package pcswapservice.service.da.mongo

import org.springframework.data.mongodb.repository.MongoRepository
import pcswapservice.model.swap.Swap

interface SwapRepository: MongoRepository<Swap, String> {
    fun findBySwapId(swapId: String): Swap
    fun findBySellerUserId(sellerUserId: String): List<Swap>
    fun findAllOrderByCreateDateDesc(): List<Swap>
}