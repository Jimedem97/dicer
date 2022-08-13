package de.jimedem.dicer.common

fun String.toSanitizedInt(): Int{
    return filter { it.isDigit() }.toInt()
}