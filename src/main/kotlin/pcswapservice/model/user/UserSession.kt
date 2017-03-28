package pcswapservice.model.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection="session")
data class UserSession(
        @Id var id: String?,
        var sessionId: String,
        var userId: String,
        var createDate: Date,
        var updatedDate: Date,
        var timeoutDelay: Int)