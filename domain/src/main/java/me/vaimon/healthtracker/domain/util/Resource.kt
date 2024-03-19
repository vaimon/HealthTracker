package me.vaimon.healthtracker.domain.util

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(val exception: Throwable) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}

fun <T : Any, U : Any> Resource<T>.map(transformer: (T) -> U): Resource<U> {
    return when (this) {
        is Resource.Success -> Resource.Success(transformer(this.data))
        is Resource.Error -> Resource.Error(this.exception)
        is Resource.Loading -> Resource.Loading
    }
}

fun <T : Any, U : Any> Resource<List<T>>.mapList(transformer: (T) -> U): Resource<List<U>> =
    this.map { it.map(transformer) }