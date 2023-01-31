package com.hamzaazman.flowexample.data.repository

import com.hamzaazman.flowexample.common.Resource
import com.hamzaazman.flowexample.data.api.ConnectivityObserver
import com.hamzaazman.flowexample.data.api.PostService
import com.hamzaazman.flowexample.data.model.Comment
import com.hamzaazman.flowexample.data.model.Post
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class PostRepository @Inject constructor(
    private val service: PostService,
    private val networkObserverRepository: ConnectivityObserver,
) {

    fun getPost(): Flow<Resource<List<Post>>> = flow {
        emit(Resource.Loading)
        delay(2000)
        emit(Resource.Success(service.getPosts()))

    }

    fun getComments(): Flow<Resource<List<Comment>>> = flow {
        emit(Resource.Loading)
        delay(2000)
        emit(Resource.Success(service.getComments()))

    }
}