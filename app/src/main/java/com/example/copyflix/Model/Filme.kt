package com.example.teste.Model

data class Filme(
    val isAdult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val isVideo: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)
