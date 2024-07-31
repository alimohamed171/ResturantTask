package com.example.api.model

data class ModelPhone(
    val data: PhoneData,
    val status: Boolean
)

data class PhoneData(
    val id: String,
    val phone: String,
    val whatsapp: String
)