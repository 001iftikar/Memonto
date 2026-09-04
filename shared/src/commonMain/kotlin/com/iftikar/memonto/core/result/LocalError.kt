package com.iftikar.memonto.core.result

enum class LocalError : Error {
    NOT_FOUND,
    STORAGE_FULL,
    DATABASE_ERROR,
    UNKNOWN
}