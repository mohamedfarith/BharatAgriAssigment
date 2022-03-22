package com.app.bharatagriassigment

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

object BindingAdapters {


    @JvmStatic
    @BindingAdapter("bind:imageUrl")
    fun bindImageUrl(view: ImageView, url: String?) {
        val requestOptions = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
        Glide.with(view.context)
            .load(url)
            .apply(requestOptions)
            .into(view)

    }

    fun preloadImage(context: Context, url: String?) {
        val requestOptions = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
        Glide.with(context)
            .load(url)
            .apply(requestOptions)
            .submit()
    }

}