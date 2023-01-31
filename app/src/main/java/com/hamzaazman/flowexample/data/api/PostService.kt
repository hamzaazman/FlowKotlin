package com.hamzaazman.flowexample.data.api

import com.hamzaazman.flowexample.data.model.Comment
import com.hamzaazman.flowexample.data.model.Post
import retrofit2.http.GET

interface PostService {

    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("comments")
    suspend fun getComments(): List<Comment>
}