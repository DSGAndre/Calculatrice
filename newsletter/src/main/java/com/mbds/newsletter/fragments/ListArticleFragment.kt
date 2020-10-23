package com.mbds.newsletter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbds.newsletter.R
import com.mbds.newsletter.adapters.ArticleAdapter
import com.mbds.newsletter.http.repositories.ArticleRepository
import com.mbds.newsletter.models.Article
import com.mbds.newsletter.models.Resource
import com.mbds.newsletter.models.Status.*
import kotlinx.coroutines.Dispatchers


/**
 * A simple [Fragment] subclass.
 * Use the [ListArticleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListArticleFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    private lateinit var category: String
    private val repository = ArticleRepository()
    private lateinit var adapter: ArticleAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleText: TextView = view.findViewById(R.id.category_title)
        titleText.text = category
        recyclerView = view.findViewById(R.id.recycler_view)
        adapter = ArticleAdapter(mutableListOf())
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = adapter
        fetchData().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
//                        progressBar.visibility = View.GONE
                        resource.data?.let { articles -> retrieveList(articles.articles) }
                    }
                    ERROR -> {
                        recyclerView.visibility = View.VISIBLE
//                        progressBar.visibility = View.GONE
                    }
                    LOADING -> {
//                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun fetchData() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.list(category)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    private fun retrieveList(articles: List<Article>) {
        articles.forEach { println(it) }
        adapter.apply {
            addArticles(articles)
            notifyDataSetChanged()
        }
    }

    companion object {
        fun newInstance(category: String): ListArticleFragment {
            return ListArticleFragment().apply {
                this.category = category
            }
        }
    }
}