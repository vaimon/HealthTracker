package me.vaimon.healthtracker.domain.util

interface Mapper<T, E> {
    fun from(e: E): T

    fun to(t: T): E
}