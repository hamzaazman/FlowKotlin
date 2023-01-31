package com.hamzaazman.flowexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamzaazman.flowexample.common.Resource
import com.hamzaazman.flowexample.data.api.ConnectivityObserver
import com.hamzaazman.flowexample.data.model.Comment
import com.hamzaazman.flowexample.data.model.Post
import com.hamzaazman.flowexample.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val _posts: MutableStateFlow<Resource<List<Post>>> = MutableStateFlow(Resource.Loading)
    val posts get() = _posts.asStateFlow()

    private val _comments: MutableStateFlow<Resource<List<Comment>>> =
        MutableStateFlow(Resource.Loading)
    val comment get() = _comments.asStateFlow()

    init {
        getPosts()
        getComments()
    }



    fun getPosts() = viewModelScope.launch {
        repository.getPost()
            .flowOn(Dispatchers.IO)
            .catch { e ->
                _posts.value = Resource.Error(e)
            }
            .collect {
                _posts.value = it
            }
    }

     fun getComments() = viewModelScope.launch {
        repository.getComments()
            .flowOn(Dispatchers.IO)
            .catch { e ->
                _comments.value = Resource.Error(e)
            }
            .collect {
                _comments.value = it
            }
    }

}