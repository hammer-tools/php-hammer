package net.rentalhost.plugins.php.hammer.services

class CacheService {
    companion object {
        val storage = mutableMapOf<String, Pair<String, Any>>()

        inline fun <reified T> remember(id: String, controller: String, function: () -> T): T {
            val storageData = storage[id]

            if (storageData != null && storageData.first == controller && storageData.second is T)
                return storageData.second as T

            val functionResult = function.invoke()

            storage[id] = Pair(controller, functionResult as Any)

            return functionResult
        }
    }
}
