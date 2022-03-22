package com.app.bharatagriassigment.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class NewsArticle(
    @SerializedName("status")
    var status: String? = null,

    @SerializedName("totalResults")
    var totalResults: Int = 0,

    @SerializedName("articles")
    var articles: ArrayList<Article>? = null
) : Serializable {


    class Source(
        @SerializedName("id")
        var id: String? = null,

        @SerializedName("name")
        var name: String? = null
    ) : Serializable

    class Article(
        @SerializedName("source")
        var source: Source? = null,

        @SerializedName("author")
        var author: String? = null,

        @SerializedName("title")
        var title: String? = null,

        @SerializedName("description")
        var description: String? = null,

        @SerializedName("url")
        var url: String? = null,

        @SerializedName("urlToImage")
        var urlToImage: String? = null,

        @SerializedName("publishedAt")
        var publishedAt: String? = null,

        @SerializedName("content")
        var content: String? = "",

        var type: String? = null
    ) : Serializable {

        fun getPublishedDate(): String? {
            return try {
                val utcFormat: DateFormat =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

                val date: Date? = utcFormat.parse(publishedAt ?: "")

                val pstFormat: DateFormat =
                    SimpleDateFormat(" dd MMMM yyyy", Locale.getDefault())
                return pstFormat.format(date ?: "")
            } catch (ignored: Exception) {
                publishedAt;
            }

        }

        fun getFormattedTitle(): String {
            title?.let { text ->
                return if (text.contains("-"))
                    text.split("-")[0];
                else
                    text
            }
            return ""
        }

    }

}