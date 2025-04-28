package com.aloel.dev.github.features.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.aloel.dev.github.core.networks.Resource
import com.aloel.dev.github.features.home.databinding.ActivityHomeBinding
import com.aloel.dev.github.features.home.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by Alul on 27/04/2025.
 * Senior Android Developer
 */

@AndroidEntryPoint
class HomeActivity: AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    private var adapter: HomeAdapter = HomeAdapter()

    private var page = 1
    private var isLoading = false
    private var query: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        initViewClick()
        initView()
    }

    private fun initView() = with(binding) {
        adapter = HomeAdapter(
            onItemClick = { user, imageView ->
                val intent = Intent(this@HomeActivity, DetailActivity::class.java).apply {
                    putExtra("user", user)
                }

                val options = user.avatar_url?.let {
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@HomeActivity,
                        imageView, // The view you want to transition (ImageView in RecyclerView item)
                        it // The transition name
                    )
                }

                startActivity(intent, options?.toBundle())
            }
        )

        recyclerView.layoutManager = GridLayoutManager(this@HomeActivity, 2)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val visibleThreshold = 4

                if (!isLoading) {
                    if (totalItemCount <= (lastVisibleItem + visibleThreshold) && page > 0) {
                        page++
                        viewModel.doSearch(searchView.query.toString(), page)
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun initViewClick() = with(binding) {
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchRunnable?.let { handler.removeCallbacks(it) }
                searchRunnable = Runnable {
                    // Trigger the search (this is where you'd perform your search)
                    if (newText == query) return@Runnable
                    resetPage()
                    query = newText
                    newText?.let { viewModel.doSearch(it, page) }
                }
                searchRunnable?.let {
                    // Delay of 2 seconds (2000ms)
                    handler.postDelayed(it, 500)
                }
                return true
            }
        })
    }

    private fun resetPage() {
        adapter?.list?.clear()
        page = 1
    }

    private fun initObserver() {
        coroutineScope.launch {
            viewModel.search.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        isLoading = true
                    }

                    is Resource.Success -> {
                        if (it.data.items.isEmpty()) {
                            page = -1
                        }

                        if (binding.searchView.query.isEmpty()) {
                            showSearchFirst()
                        } else {
                            showEmpty(it.data.items.isEmpty())
                        }

                        adapter?.list?.addAll(it.data.items)
                        adapter?.notifyDataSetChanged()

                        isLoading = false
                    }

                    is Resource.Error -> {
                        isLoading = false
                        page = -1

                        if (binding.searchView.query.isEmpty()) {
                            showSearchFirst()
                        } else {
                            showEmpty()
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun showEmpty(show: Boolean = false) = with(binding) {
        groupEmpty.visibility = if (show) View.VISIBLE else View.GONE
        recyclerView.visibility = if (show) View.GONE else View.VISIBLE

        tvEmpty.text = getString(R.string.label_empty_user)
        ivEmpty.setImageResource(R.drawable.ic_info)
    }

    private fun showSearchFirst() = with(binding) {
        groupEmpty.visibility = View.VISIBLE
        recyclerView.visibility = View.VISIBLE

        tvEmpty.text = getString(R.string.label_empty_search)
        ivEmpty.setImageResource(R.drawable.ic_search)
    }
}