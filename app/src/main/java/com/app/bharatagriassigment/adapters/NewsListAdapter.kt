package com.app.bharatagriassigment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.bharatagriassigment.R
import com.app.bharatagriassigment.databinding.AdapterItemBinding
import com.app.bharatagriassigment.databinding.LoaderViewBinding
import com.app.bharatagriassigment.models.NewsArticle

class NewsListAdapter(
    var itemList: ArrayList<NewsArticle.Article>,
    var clickListener: ListItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val LOADER_VIEW = 1234

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            LOADER_VIEW -> {
                LoaderViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.loader_view,
                        parent,
                        false
                    )
                )
            }
            else -> {
                UserListViewHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.adapter_item,
                        parent,
                        false
                    )
                )
            }

        }


    }

    override fun getItemViewType(position: Int): Int {
        if (itemList[position].type == "loader")
            return LOADER_VIEW
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserListViewHolder) {
            holder.bindData(itemList[holder.bindingAdapterPosition], clickListener)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size;
    }

    //add loader to adapter
    fun showLoader() {
        val item = NewsArticle.Article()
        item.type = "loader"
        itemList.add(item)
        notifyItemChanged(itemList.size - 1)
    }

    //remove loader from adapter
    fun removeLoader() {
        for (index in 0 until itemList.size) {
            if (itemList[index].type.equals("loader")) {
                itemList.remove(itemList[index])
                notifyItemChanged(index)
                break;
            }
        }
    }

    class UserListViewHolder(var itemBinding: AdapterItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindData(result: NewsArticle.Article, clickListener: ListItemClickListener) {
            itemBinding.newsArticle = result
            itemBinding.root.tag = result
            itemBinding.root.setOnClickListener {
                clickListener.onClick(it.tag as NewsArticle.Article, itemBinding.imageView)
            }
            itemBinding.executePendingBindings()
        }

    }

    class LoaderViewHolder(itemBinding: LoaderViewBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    interface ListItemClickListener {
        fun onClick(results: NewsArticle.Article, view: View)
    }
}