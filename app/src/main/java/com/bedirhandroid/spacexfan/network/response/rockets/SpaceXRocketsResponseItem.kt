package com.bedirhandroid.spacexfan.network.response.rockets

data class SpaceXRocketsResponseItem(
    val description: String = "",
    val flickr_images: List<String> = listOf(),
    val id: Int = 0,
    val rocket_id: String = "",
    val rocket_name: String = "",
    val rocket_type: String = ""
): java.io.Serializable
