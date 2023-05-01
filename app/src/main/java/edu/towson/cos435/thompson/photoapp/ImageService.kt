package edu.towson.cos435.thompson.photoapp

interface ImageService {
    @GET("v2/list")
    suspend fun getImages(): List<ImageResponse>
}
