package me.vaimon.healthtracker.domain.util

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(val exception: Throwable) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}

fun <T : Any, U : Any> Resource<List<T>>.map(transformer: (T) -> U): Resource<List<U>> {
    return when (this) {
        is Resource.Success -> Resource.Success(this.data.map { transformer(it) })
        is Resource.Error -> Resource.Error(this.exception)
        is Resource.Loading -> Resource.Loading
    }
}