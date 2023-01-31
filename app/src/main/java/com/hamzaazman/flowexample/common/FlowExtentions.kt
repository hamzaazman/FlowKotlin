package com.hamzaazman.flowexample.common

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.collectWithLifecycle(
    owner: LifecycleOwner,
    crossinline onCollect: suspend (T) -> Unit
) = owner.lifecycleScope.launch {
    owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        collect {
            onCollect(it)
        }
    }
}

fun <T> Flow<T>.collect(coroutineScope: CoroutineScope, function: (T) -> Unit) {
    coroutineScope.launch {
        collect {
            function(it)
        }
    }
}