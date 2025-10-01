package com.parafuso.parkingchallenge.core.presentation

import android.view.View
import androidx.activity.ComponentActivity
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.core.app.ActivityCompat
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

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