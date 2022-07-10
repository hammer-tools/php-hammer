package net.rentalhost.plugins.extensions

import com.intellij.openapi.extensions.PluginDescriptor

fun PluginDescriptor.getVersionWithoutPatch(): String =
    version.substringBeforeLast(".")
