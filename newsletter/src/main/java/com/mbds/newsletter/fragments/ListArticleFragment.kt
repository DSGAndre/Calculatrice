package com.mbds.newsletter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbds.newsletter.R
import com.mbds.newsletter.adapters.ArticleAdapter
import com.mbds.newsletter.http.repositories.ArticleRepository
import com.mbds.newsletter.models.Article
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * A simple [Fragment] subclass.
 * Use the [ListArticleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListArticleFragment : Fragment(), CoroutineScope {
    lateinit var recyclerView: RecyclerView
    private lateinit var category: String
    private val repository = ArticleRepository()
    private lateinit var data: List<Article>
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
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
        launch {
            val loaderData = async(Dispatchers.IO) {
                data = repository.list(category)
            }

            view.findViewById<TextView>(R.id.categoryTitle).text = category
            recyclerView = view.findViewById(R.id.recycler_view)
            loaderData.await()
            val adapterRecycler = ArticleAdapter(data)
            recyclerView.layoutManager = LinearLayoutManager(view.context)
            recyclerView.adapter = adapterRecycler
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