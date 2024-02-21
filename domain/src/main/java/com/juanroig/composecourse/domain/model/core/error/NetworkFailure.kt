package com.juanroig.composecourse.domain.model.core.error

sealed class NetworkFailure(
    message: String? = null
) : Failure.FeatureFailure(message) {
    class ServerFailure(val code: String? = "0", message: String? = null) : NetworkFailure(message)
    object Timeout : NetworkFailure("network failure tiemout")
    object UnknownHost : NetworkFailure("unknown host")
    object TokenExpired : NetworkFailure("token expired")
    class JsonFormat(msg: String?) : NetworkFailure(msg)
    class UnexpectedNetworkError(message: String) : NetworkFailure()

    object NoInternetConnection : NetworkFailure("no internet connection")
}
