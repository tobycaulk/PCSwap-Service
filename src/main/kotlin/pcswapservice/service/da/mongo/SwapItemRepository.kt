package pcswapservice.service.da.mongo

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import pcswapservice.model.swap.SwapItem

@Repository
interface SwapItemRepository: MongoRepository<SwapItem, String> {
}