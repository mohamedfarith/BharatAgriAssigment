package com.app.bharatagriassigment.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import com.app.bharatagriassigment.models.NewsArticle
import com.app.bharatagriassigment.services.Resource


class MainRepository {

    private lateinit var viewModel: MainActViewModel

    companion object {
        private val mainRepository: MainRepository by lazy {
            MainRepository()
        }

        fun getInstance(viewModel: MainActViewModel): MainRepository {
            mainRepository.setViewModel(viewModel)
            return mainRepository
        }

    }

    private fun setViewModel(model: MainActViewModel) {
        this.viewModel = model
    }

    fun getNewsLiveData(): MutableLiveData<Resource<NewsArticle>> {
        return viewModel.getNewsListLiveData()
    }

    fun fetchNewsArticles(pageSize: Int, pageNumber: Int) {
        viewModel.getNewsDetails(pageSize, pageNumber)

    }




}