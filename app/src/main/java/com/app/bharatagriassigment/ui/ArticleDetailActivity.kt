package com.app.bharatagriassigment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.app.bharatagriassigment.R
import com.app.bharatagriassigment.databinding.ArticleDetailLayoutBinding
import com.app.bharatagriassigment.models.NewsArticle

class ArticleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ArticleDetailLayoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.article_detail_layout)

        initViews()
    }


    private fun initViews() {
        window.statusBarColor =
            ContextCompat.getColor(this, android.R.color.transparent)

        if (intent?.extras?.containsKey("ARTICLE_DATA") == true) {
            val articleData = intent?.extras?.getSerializable("ARTICLE_DATA") as NewsArticle.Article
            binding.newArticle = articleData
        }


    }
}