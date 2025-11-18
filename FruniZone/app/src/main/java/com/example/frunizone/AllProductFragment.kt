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
import com.example.frunizone.adapter.FurnitureAdapter1
import com.example.frunizone.api.FurnitureApi
import com.example.frunizone.model.FurnitureModel
import com.example.frunizone.model.FurnitureOutputModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AllProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AllProductFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private lateinit var rcylProductSeeAll: RecyclerView
    private lateinit var toolbar: Toolbar

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
        return inflater.inflate(R.layout.fragment_all_product, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcylProductSeeAll = view.findViewById(R.id.rcylproductSeeAll)
        toolbar = view.findViewById(R.id.toolbar)

        toolbar.setNavigationOnClickListener {
            val intent = Intent(requireActivity(), HomeActivity::class.java)
            startActivity(intent)
        }

        FurnitureApi().getFurnitureAll(this)
    }

    fun setFurniture(model: FurnitureOutputModel) {

        val adapter = FurnitureAdapter1(
            requireActivity(),
            model.sub_category,
            object : FurnitureAdapter1.ItemClickListener {
                override fun onClick(position: Int, item: FurnitureModel) {

                    val bundle = Bundle().apply {
                        putSerializable("product", item)
                    }

                    val fragment = SubCategoryFragment().apply {
                        arguments = bundle
                    }

                    (requireActivity() as HomeActivity).openFragment(fragment)
                }
            }
        )

        rcylProductSeeAll.layoutManager = GridLayoutManager(requireContext(), 2)
        rcylProductSeeAll.adapter = adapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AllProductFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AllProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}