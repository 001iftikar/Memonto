package com.iftikar.memonto.feature.global

import com.iftikar.memonto.core.model.User

data class ShowUserNameState(
    val user: User? = null,
    val isLoading: Boolean = true
)
