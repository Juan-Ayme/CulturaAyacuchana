package com.amver.cultura_ayacucho.data.model

data class MovieResult(
    val page: Int,
    val results: List<MovieModel>,
    val total_pages: Int,
    val total_results: Int
)
