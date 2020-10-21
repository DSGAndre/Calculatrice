package com.mbds.newsletter.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbds.newsletter.MainActivity
import com.mbds.newsletter.R
import com.mbds.newsletter.adapters.CategoryCallback
import com.mbds.newsletter.adapters.CategoriesAdapter
import com.mbds.newsletter.models.Category

/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoriesFragment : Fragment(), CategoryCallback {
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view)
        val categories = listOf<Category>(
            Category("Sport", "", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTqHRYCG74D6qQ2IT6TUWpt9Mrn3iBgfS0nww&usqp=CAU"),
            Category("Economic", "", "https://i.kym-cdn.com/entries/icons/facebook/000/029/959/Screen_Shot_2019-06-05_at_1.26.32_PM.jpg"),
            Category("Politic", "", "https://upload.wikimedia.org/wikipedia/commons/0/04/Barack_Obama_Mic_Drop_2016.jpg"),
            Category("Education", "", "https://upload.wikimedia.org/wikipedia/commons/0/04/Barack_Obama_Mic_Drop_2016.jpg"),
            Category("Pandemic", "", "https://upload.wikimedia.org/wikipedia/commons/0/04/Barack_Obama_Mic_Drop_2016.jpg"),
            Category("Sciences", "", "https://upload.wikimedia.org/wikipedia/commons/0/04/Barack_Obama_Mic_Drop_2016.jpg"),
            Category("Ecology", "", "https://upload.wikimedia.org/wikipedia/commons/0/04/Barack_Obama_Mic_Drop_2016.jpg")
        )
        val adapterRecycler = CategoriesAdapter(categories, this)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = adapterRecycler
    }

    override fun onClick(categoryName: String) {
        (activity as? MainActivity)?.changeFragment(ListArticleFragment.newInstance(categoryName))
    }
}