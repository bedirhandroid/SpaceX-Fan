package com.bedirhandroid.spacexfan.network.response.rockets

data class PayloadWeight(
    val id: String,
    val kg: Int,
    val lb: Int,
    val name: String
)