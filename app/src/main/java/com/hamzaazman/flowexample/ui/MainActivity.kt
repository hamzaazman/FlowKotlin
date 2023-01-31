package com.hamzaazman.flowexample.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hamzaazman.flowexample.databinding.ActivityMainBinding
import com.hamzaazman.flowexample.ui.adapter.PostAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val postAdapter: PostAdapter by lazy { PostAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonParalel.setOnClickListener {
            val intent = Intent(this@MainActivity, ParalelActivity::class.java)
            startActivity(intent)
        }

    }
/*
    private fun initCollect() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.posts.collect {
                    with(binding) {
                        when (it) {
                            is Resource.Loading -> {
                                showLoading()
                            }
                            is Resource.Error -> {
                                showError(it.throwable)
                            }
                            is Resource.Success -> {
                                postLoading.isGone = true
                                postError.isGone = true

                                postRecyclerView.apply {
                                    isVisible = true
                                    postAdapter.submitList(it.data)
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupRv() {
        binding.postRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity, LinearLayoutManager.VERTICAL
                )
            )
            adapter = postAdapter
        }
    }

    private fun showError(e: Throwable) {
        with(binding) {
            postLoading.isGone = true
            postRecyclerView.isGone = true
            postError.apply {
                text = e.toString()
                isVisible = true
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            postRecyclerView.isGone = true
            postError.isGone = true
            postLoading.isVisible = true
        }
    }


 */
}