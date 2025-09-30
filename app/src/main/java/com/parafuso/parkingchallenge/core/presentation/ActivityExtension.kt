package com.parafuso.parkingchallenge.core.presentation

import android.view.View
import androidx.activity.ComponentActivity
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.core.app.ActivityCompat
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

private const val BIND_METHOD_NAME = "bind"

inline fun <reified T : ViewBinding> ComponentActivity.viewBinding(
    @IdRes viewBindingRootId: Int
): ReadOnlyProperty<ComponentActivity, T> = ActivityBindingProperty { activity ->
    ActivityCompat.requireViewById<View>(activity, viewBindingRootId).bind(T::class.java)
}

class ActivityBindingProperty<T : ViewBinding>(
    private val viewBindingCreator: (ComponentActivity) -> T,
) : ReadOnlyProperty<ComponentActivity, T> {

    private var viewBinding: T? = null

    @MainThread
    override fun getValue(thisRef: ComponentActivity, property: KProperty<*>): T {
        return viewBinding ?: viewBindingCreator(thisRef)
            .also { viewBinding = it }
    }
}

@Suppress("UNCHECKED_CAST")
fun <T : ViewBinding> View.bind(viewBindingClass: Class<T>): T {
    val bindMethod: Method = viewBindingClass.getMethod(BIND_METHOD_NAME, View::class.java)
    return bindMethod.invoke(null, this) as T
}