package com.juanroig.composecourse.domain.model.core.error

sealed class CustomFailure(
    data: String
) : Failure.FeatureFailure(data = data) {

    object NoData : CustomFailure("no data")
    object NoUseCaseParams : CustomFailure("no use case params")
    object Unknown : CustomFailure("unknown")
    object NoResponse : CustomFailure("no response")
    object NotFound : CustomFailure("not found")

    object SaveDataFailure : CustomFailure("save data failure")
    object DeleteDataFailure : CustomFailure("delete data failure")
}
