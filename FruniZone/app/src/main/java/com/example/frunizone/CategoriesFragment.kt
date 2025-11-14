package com.example.frunizone



import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.example.frunizone.adapter.CategoryAdapter
import com.example.frunizone.api.CategoryApi
import com.example.frunizone.model.CategoryModel
import com.example.frunizone.model.CategoryOutputModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoriesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private lateinit var rcylDisplayCategory: RecyclerView
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcylDisplayCategory = view.findViewById(R.id.rcylDisplayCategory)
        // toolbar = view.findViewById(R.id.toolbar)

//        toolbar.setNavigationOnClickListener {
//            val intent = Intent(activity, HomeActivity::class.java)
//            startActivity(intent)
//        }

        CategoryApi().getCategoryAll(this)
    }

    fun setCategory(model: CategoryOutputModel) {
        val categoryAdapter = CategoryAdapter(
            requireActivity(),
            model.category ?: arrayListOf(),
            object : CategoryAdapter.ItemClickListener {
                override fun onClick(position: Int, model: CategoryModel) {
                    val bundle = Bundle().apply {
                        putString("id", model.cat_id)
                    }
                    val fragment = ProductFragment().apply {
                        arguments = bundle
                    }
                    (activity as? HomeActivity)?.openFragment(fragment)
                }
            }
        )

        rcylDisplayCategory.layoutManager = GridLayoutManager(context, 3)
        rcylDisplayCategory.adapter = categoryAdapter
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoriesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoriesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}