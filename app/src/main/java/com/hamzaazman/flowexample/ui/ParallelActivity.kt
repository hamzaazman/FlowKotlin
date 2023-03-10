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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamzaazman.flowexample.common.NetworkConnectivityObserver
import com.hamzaazman.flowexample.common.Resource
import com.hamzaazman.flowexample.data.api.ConnectivityObserver
import com.hamzaazman.flowexample.databinding.ActivityParallelBinding
import com.hamzaazman.flowexample.ui.adapter.CommentAdapter
import com.hamzaazman.flowexample.ui.adapter.PostAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ParallelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParallelBinding

    private val viewModel: MainViewModel by viewModels()
    private val postAdapter: PostAdapter by lazy { PostAdapter() }
    private val commentAdapter: CommentAdapter by lazy { CommentAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParallelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRv()
        initCollect()

    }

    private fun initCollect() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
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

                NetworkConnectivityObserver(this@ParallelActivity).observe().collect {
                    when (it) {
                        ConnectivityObserver.Status.Available -> {
                            Log.d("Networkkkk", "??nternet var")
                            viewModel.apply {
                                getComments()
                                getPosts()
                            }
                        }
                        ConnectivityObserver.Status.Losing -> {
                            Log.d("Networkkkk", "??nterneti kaybediyoruz seni")
                        }
                        ConnectivityObserver.Status.Lost -> {
                            Log.d("Networkkkk", "??nterneti kaybettik")
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
                addItemDecoration(
                    DividerItemDecoration(
                        this@ParallelActivity, LinearLayoutManager.VERTICAL
                    )
                )
            }

            paralelCommantRecyclerView.apply {
                adapter = commentAdapter
                addItemDecoration(
                    DividerItemDecoration(
                        this@ParallelActivity, LinearLayoutManager.VERTICAL
                    )
                )
            }
        }
    }

}