package com.juanroig.composecourse.domain.model.core.result

import com.juanroig.composecourse.domain.model.core.error.Failure

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val failure: Failure) : Result<Nothing>
}