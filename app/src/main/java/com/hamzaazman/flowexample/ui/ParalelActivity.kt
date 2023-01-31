package com.hamzaazman.flowexample.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hamzaazman.flowexample.common.NetworkConnectivityObserver
import com.hamzaazman.flowexample.common.Resource
import com.hamzaazman.flowexample.data.api.ConnectivityObserver
import com.hamzaazman.flowexample.databinding.ActivityParalelBinding
import com.hamzaazman.flowexample.ui.adapter.CommentAdapter
import com.hamzaazman.flowexample.ui.adapter.PostAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ParalelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParalelBinding

    private val viewModel: MainViewModel by viewModels()
    private val postAdapter: PostAdapter by lazy { PostAdapter() }
    private val commentAdapter: CommentAdapter by lazy { CommentAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParalelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRv()
        initCollect()

    }

    private fun initCollect() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.posts.collect {
                        with(binding) {
                            when (it) {
                                is Resource.Loading -> {
                                    postLoading.isVisible = true
                                }
                                is Resource.Error -> {
                                    postLoading.isGone = true
                                    postErrorAnim.playAnimation()
                                }
                                is Resource.Success -> {
                                    postErrorAnim.isGone = true
                                    postLoading.isGone = true
                                    paralelPostRecyclerView.isVisible = true
                                    paralelPostRecyclerView.apply {
                                        postAdapter.submitList(it.data)
                                    }
                                }
                            }
                        }
                    }
                }
                launch {
                    viewModel.comment.collect {
                        with(binding) {
                            when (it) {
                                is Resource.Loading -> {
                                    commentLoading.isVisible = true
                                }
                                is Resource.Error -> {
                                    commentLoading.isGone = true
                                    commentErrorAnim.playAnimation()
                                }
                                is Resource.Success -> {
                                    commentLoading.isGone = true
                                    paralelCommantRecyclerView.isVisible = true
                                    paralelCommantRecyclerView.apply {
                                        commentAdapter.submitList(it.data)
                                    }
                                }
                            }
                        }
                    }
                }

                NetworkConnectivityObserver(this@ParalelActivity).observe().collect {
                    when (it) {
                        ConnectivityObserver.Status.Available -> {
                            Log.d("Networkkkk", "İnternet var")
                            viewModel.apply {
                                getComments()
                                getPosts()
                            }
                        }
                        ConnectivityObserver.Status.Losing -> {
                            Log.d("Networkkkk", "İnterneti kaybediyoruz seni")
                        }
                        ConnectivityObserver.Status.Lost -> {
                            Log.d("Networkkkk", "İnterneti kaybettik")
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun setupRv() {
        with(binding) {
            paralelPostRecyclerView.apply {
                adapter = postAdapter
            }

            paralelCommantRecyclerView.apply {
                adapter = commentAdapter
            }
        }
    }


}