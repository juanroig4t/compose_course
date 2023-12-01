package com.juanroig.composecourse.domain.model.movie

data class Person(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val knownFor: List<Movie>,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String
)