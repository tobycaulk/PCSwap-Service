package pcswapservice.service.utilities

import org.mindrot.jbcrypt.BCrypt
import java.util.*

fun generateUUID() = UUID.randomUUID().toString()

fun hashPassword(password: String) = BCrypt.hashpw(password, BCrypt.gensalt())

fun validatePassword(password: String, hashedPassword: String) = BCrypt.checkpw(password, hashedPassword)