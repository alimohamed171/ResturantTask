package com.example.api.model

data class ModelCategories(
    val data: List<CategoryData>,
    val status: Boolean
)
data class CategoryData(
    val id: String,
    val name: String
)