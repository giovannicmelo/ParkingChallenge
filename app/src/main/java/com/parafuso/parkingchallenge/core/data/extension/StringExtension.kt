package com.parafuso.parkingchallenge.core.data.extension

fun String?.orEmpty() = this ?: ""

fun String.formatTime(): String {
    return this
        .replace(" hours", "h")
        .replace(" hour", "h")
        .replace(" minutes", " min")
        .replace(" minute", " min")
        .replace("h ", "h")
}