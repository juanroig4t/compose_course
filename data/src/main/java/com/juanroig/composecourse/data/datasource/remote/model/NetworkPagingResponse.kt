package com.juanroig.composecourse.data.datasource.remote.model

data class NetworkPagingResponse<T>(
    val page: Int,
    val results: T,
    val totalPages: Int,
    val totalResults: Int
)
