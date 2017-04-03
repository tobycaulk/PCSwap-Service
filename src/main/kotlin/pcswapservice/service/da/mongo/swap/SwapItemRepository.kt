package pcswapservice.service.da.mongo.swap

import org.springframework.data.mongodb.repository.MongoRepository
import pcswapobjects.swap.SwapItem

interface SwapItemRepository: MongoRepository<SwapItem, String> {
    fun findBySwapItemId(swapItemId: String): SwapItem
}