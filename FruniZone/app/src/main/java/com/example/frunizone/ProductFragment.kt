package com.example.frunizone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frunizone.adapter.FurnitureAdapter1
import com.example.frunizone.api.FurnitureFromCategoryApi
import com.example.frunizone.model.FurnitureModel
import com.example.frunizone.model.FurnitureOutputModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var rcylProd: RecyclerView
//    private lateinit var toolbar: Toolbar
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
        return inflater.inflate(R.layout.fragment_product, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcylProd = view.findViewById(R.id.rcyl_prod)
//        toolbar = view.findViewById(R.id.toolbar)
/*
        toolbar.setNavigationOnClickListener {
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
        }
*/
        val id = arguments?.getString("id", "0") ?: "0"

        if (id != "0") {
            FurnitureFromCategoryApi().getFurnitureFromCat(this, id)
        }
    }

    fun setFurniture(model: FurnitureOutputModel) {

        val adapter = FurnitureAdapter1(
            requireActivity(),
            model.sub_category,
            object : FurnitureAdapter1.ItemClickListener {
                override fun onClick(position: Int, model: FurnitureModel) {

                    val bundle = Bundle().apply {
                        putSerializable("product", model)
                    }

                    val fragment = SubCategoryFragment()
                    fragment.arguments = bundle

                    (requireActivity() as HomeActivity).openFragment(fragment)
                }
            }
        )

        rcylProd.layoutManager = GridLayoutManager(requireContext(), 2)
        rcylProd.adapter = adapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProductFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}