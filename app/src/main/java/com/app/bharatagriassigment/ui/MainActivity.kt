package com.app.bharatagriassigment.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.bharatagriassigment.Constants
import com.app.bharatagriassigment.GenericCallback
import com.app.bharatagriassigment.R
import com.app.bharatagriassigment.adapters.NewsListAdapter
import com.app.bharatagriassigment.databinding.NewsArticleLayoutBinding
import com.app.bharatagriassigment.models.NewsArticle
import com.app.bharatagriassigment.services.Resource
import com.app.bharatagriassigment.ui.viewmodels.MainActViewModel
import com.app.bharatagriassigment.ui.viewmodels.MainRepository
import com.app.bharatagriassigment.utils.LocalUtils
import com.app.bharatagriassigment.utils.PageScrollListener

class MainActivity : AppCompatActivity(), NewsListAdapter.ListItemClickListener {
    private lateinit var binding: NewsArticleLayoutBinding
    private lateinit var adapter: NewsListAdapter
    private var isLastPage = false
    private var isLoading = false
    var page = 1
    private lateinit var viewModel: MainActViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.news_article_layout)
        supportActionBar?.hide()

        initViews()

    }

    private fun initViews() {
        registerAndObserveLiveData()
        fetchNewsArticles(page)
        //adding scroll listener to handle lazy loading
        binding.recycler.addOnScrollListener(object :
            PageScrollListener(binding.recycler.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                page++;
                isLoading = true;
                fetchNewsArticles(page)
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })
    }

    private fun registerAndObserveLiveData() {
        //registering view model and accessing it with Repository pattern
        viewModel = ViewModelProvider(this)[MainActViewModel::class.java]
        MainRepository.getInstance(viewModel)
            .getNewsLiveData()
            .observe(this, { result ->
                result.let {
                    when (result.status) {
                        Resource.Status.SUCCESS -> {
                            hideLoadingIndicator()
                            bindData(result.data)
                        }
                        Resource.Status.ERROR -> {
                            hideLoadingIndicator()
                            if (::adapter.isInitialized) adapter.removeLoader()
                            Toast.makeText(this, "Api limit reached", Toast.LENGTH_SHORT).show()
                        }
                        Resource.Status.LOADING -> {
                            showLoadingIndicator()

                        }
                    }
                }
            })


    }

    private fun fetchNewsArticles(pageNumber: Int) {
        if (LocalUtils.isOnline(binding.root.context)) {
            showLoadingIndicator()
            //providing a delay to get good user experience
            Handler(Looper.getMainLooper()).postDelayed({
                MainRepository.getInstance(viewModel)
                    .fetchNewsArticles(Constants.DEFAULT_PAGE_LIMIT, pageNumber)
            }, 1000)

        } else {
            showErrorMessage()
        }

    }


    private fun showErrorMessage() {
        LocalUtils.showAlertDialog(
            this,
            getString(R.string.connection_error),
            getString(R.string.internet_connection_error),
            object : GenericCallback<String> {
                override fun callback(data: String) {
                    fetchNewsArticles(page)
                }

            })
    }

    //show loading indicator
    private fun showLoadingIndicator() {
        if (!isLoading)
            binding.loadingIndicator.visibility = View.VISIBLE
        else if (!isLastPage) {
            adapter.showLoader()
            isLoading = true
        }
    }

    //hide loading indicator
    private fun hideLoadingIndicator() {
        if (!isLoading)
            binding.loadingIndicator.visibility = View.INVISIBLE
        else if (!isLastPage) {
            adapter.removeLoader()
            isLoading = false
        }

    }

    private fun bindData(data: NewsArticle?) {
        /* if adapter is initialized then already some items are present in the recycler
        so updating the content else set adapter*/
        if (::adapter.isInitialized && page != 1) {
            if (adapter.itemList.size >= data?.totalResults ?: 0) {
                isLastPage = true
            }
            val prevSize = adapter.itemList.size
            adapter.itemList = data?.articles ?: ArrayList()
            //to maintain order and to remove duplicates used linked hashsets
            adapter.itemList = ArrayList(LinkedHashSet(adapter.itemList))
            adapter.notifyItemRangeChanged(prevSize, adapter.itemList.size - 1)
        } else {
            //to maintain order and to remove duplicates used linked hashset
            data?.articles = ArrayList(LinkedHashSet(data?.articles ?: ArrayList()))
            adapter = NewsListAdapter(data?.articles ?: ArrayList(), this)
            binding.adapter = adapter
        }

    }

    override fun onClick(results: NewsArticle.Article, view: View) {
        //opening the details screen activity with scene transition animation
        val options = ActivityOptions.makeSceneTransitionAnimation(this, view, "image")
        val intent = Intent(this, ArticleDetailActivity::class.java)
        intent.putExtra("ARTICLE_DATA", results)
        startActivity(intent, options.toBundle())
    }
}
