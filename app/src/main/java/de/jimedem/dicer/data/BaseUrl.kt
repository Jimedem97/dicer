package de.jimedem.dicer.data

import kotlinx.coroutines.flow.MutableStateFlow

sealed class BaseUrl(val name: String, val hostname: String, var reachable: MutableStateFlow<Boolean> = MutableStateFlow(false)) {
    object RaspberryPi : BaseUrl("Raspberry Pi", "joni-media")
    object Laptop : BaseUrl("Laptop", "joni-laptop-alt")
}
