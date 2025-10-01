package com.parafuso.parkingchallenge.core.presentation

import android.view.View
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method

private const val BIND_METHOD_NAME = "bind"

@Suppress("UNCHECKED_CAST")
fun <T : ViewBinding> View.bind(viewBindingClass: Class<T>): T {
    val bindMethod: Method = viewBindingClass.getMethod(BIND_METHOD_NAME, View::class.java)
    return bindMethod.invoke(null, this) as T
}