package com.parafuso.parkingchallenge.core.data.mapper

interface Mapper<S, T> {
    fun map(source: S): T
}