package app.actionsfun.common.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.startWith(value: T): Flow<T> = onStart { emit(value) }