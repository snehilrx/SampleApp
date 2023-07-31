package com.dailyrounds.marrow.assignment.data

data class InputState<T>(
    val value: T,
    val error: String
) {
    fun isValid() = error.isBlank()
}
