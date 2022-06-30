package net.rentalhost.plugins.services

import org.apache.commons.lang.StringUtils
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

object TypeService {
    private val nullType = prependGlobalNamespace(mutableListOf("null"))

    fun splitTypes(types: String?): Stream<String?> {
        return Arrays.stream(StringUtils.split(types, "|"))
    }

    fun exceptNull(types: String?): Stream<String?> {
        return splitTypes(types).filter { s: String? -> !nullType.contains(s) }
    }

    fun joinTypesStream(types: Stream<String?>): String {
        return types.collect(Collectors.joining("|"))
    }

    private fun prependGlobalNamespace(types: MutableList<String?>): List<String?> {
        types.addAll(types.stream()
            .map { s: String? -> "\\" + s }
            .collect(Collectors.toList()))
        
        return types
    }
}
