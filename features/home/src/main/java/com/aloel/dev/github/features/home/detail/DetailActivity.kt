package com.aloel.dev.github.features.home.detail

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.transition.TransitionInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.aloel.dev.github.core.extensions.dpToPixel
import com.aloel.dev.github.core.models.User
import com.aloel.dev.github.core.networks.Resource
import com.aloel.dev.github.features.home.R
import com.aloel.dev.github.features.home.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
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
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private var adapter: DetailAdapter? = null

    private var user: User? = null

    private var page = 1
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getParcelableExtra("user")

        val animation = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
        window.sharedElementEnterTransition = animation
        window.sharedElementReturnTransition = animation

        postponeEnterTransition()

        setTransition()
        initObserver()
        initView()
        initViewClick()

        viewModel.getUserDetail(user?.id)
        viewModel.getRepoList(user?.id, 1)

    }

    private fun initViewClick() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initObserver() {
        coroutineScope.launch {
            viewModel.user.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        setUser(it.data)
                    }

                    else -> {}
                }
            }
        }

        coroutineScope.launch {
            viewModel.repo.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        isLoading = true
                    }

                    is Resource.Success -> {
                        if (it.data.isEmpty()) {
                            page = -1
                        }

                        val list = it.data.filter { it.fork == false }

                        adapter?.list?.addAll(list)
                        adapter?.notifyDataSetChanged()

                        isLoading = false
                    }

                    is Resource.Error -> {
                        isLoading = false
                        page = -1
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setUser(data: User) = with(binding) {
        tvUserName.text = data.name
        tvUserLogin.text = data.login
        tvDesc.text = data.bio
        tvFollowers.text = Html.fromHtml(getString(R.string.desc_followers, data.followers.toString(), data.following.toString()), Html.FROM_HTML_MODE_COMPACT)
    }

    private fun initView() = with(binding) {
        user?.let { setUser(it) }

        adapter = DetailAdapter(
            onItemClick = {
                val intent = CustomTabsIntent.Builder().build()
                intent.launchUrl(this@DetailActivity, Uri.parse(it.html_url))
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this@DetailActivity)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val visibleThreshold = 4

                if (!isLoading) {
                    if (totalItemCount <= (lastVisibleItem + visibleThreshold) && page > 0) {
                        page++
                        viewModel.getRepoList(user?.id, page)
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun setTransition() {
        binding.ivUser.transitionName = user?.avatar_url

        Glide.with(this@DetailActivity)
            .load(user?.avatar_url)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.ivUserSmall)

        Glide.with(this@DetailActivity)
            .load(user?.avatar_url)
            .apply(
                RequestOptions().transforms(
                    RoundedCorners(8.dpToPixel(this)),
                )
            )
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

            })
            .into(binding.ivUser)
    }
}