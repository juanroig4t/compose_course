package com.juanroig.composecourse.domain.model.core.error

sealed class Failure(val data: Any? = null) {

    class GenericFailure(val code: String? = "0", data: Any? = null) : Failure(data)

    abstract class FeatureFailure(data: Any? = null) : Failure(data)
}
