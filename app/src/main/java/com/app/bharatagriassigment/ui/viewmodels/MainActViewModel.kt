package com.app.bharatagriassigment.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bharatagriassigment.models.NewsArticle
import com.app.bharatagriassigment.services.Resource
import com.app.bharatagriassigment.services.Retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActViewModel : ViewModel() {
    private val livedata: MutableLiveData<Resource<NewsArticle>> by lazy {
        MutableLiveData<Resource<NewsArticle>>()
    }

    private var newsData: Resource<NewsArticle>? = null

    fun getNewsListLiveData(): MutableLiveData<Resource<NewsArticle>> {
        return livedata
    }

    fun getNewsDetails(
        pageSize: Int, pageNumber: Int
    ) {
        loadData(pageSize, pageNumber);

    }

    private fun loadData(pageSize: Int, pageNumber: Int) {
        viewModelScope.launch {
            val response = Retrofit.getInstance().getNewsData("in", pageSize, pageNumber)
            withContext(Dispatchers.Main) {
                if (response.code() == 200) {
                    if (newsData == null) {
                        newsData = Resource.success(response.body())

                    } else {
                        response.body()?.articles?.let {
                            newsData?.data?.articles?.addAll(it)
                        }

                    }
                    livedata.postValue(newsData)
                } else {
                    livedata.value = Resource.error("Server error", null)
                }
            }

        }
    }

}
