package com.mbds.newsletter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mbds.newsletter.R
import com.mbds.newsletter.models.Article

class ArticleAdapter(private val dataset: MutableList<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    class ViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {
        fun bind(item: Article) {
            val txtTitle = root.findViewById<TextView>(R.id.title)
            val img = root.findViewById<AppCompatImageView>(R.id.image)
            val txtDesc = root.findViewById<TextView>(R.id.desc)
            txtTitle.text = item.title
            txtDesc.text = item.description
            Glide.with(root).load(item.urlToImage).into(img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun getItemCount(): Int = dataset.size

    fun addArticles(articles: List<Article>) {
        this.dataset.apply{
            clear()
            addAll(articles)
        }
    }

}