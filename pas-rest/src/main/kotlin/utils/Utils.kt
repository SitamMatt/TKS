package utils

fun <T> coalesce(obj: T?, default: T): T {
    return obj ?: default
}