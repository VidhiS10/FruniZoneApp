package com.example.frunizone



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.frunizone.adapter.CategoryAdapter
import com.example.frunizone.adapter.FurnitureAdapter
//import com.example.furnizone.adapter.CategoryAdapter
//import com.example.furnizone.adapter.FurnitureAdpater
import com.example.frunizone.api.BannerApi
import com.example.frunizone.api.CategoryApi
import com.example.frunizone.api.FurnitureApi
import com.example.frunizone.model.BannerOutputModel
import com.example.frunizone.model.CategoryModel
import com.example.frunizone.model.CategoryOutputModel
import com.example.frunizone.model.FurnitureModel
import com.example.frunizone.model.FurnitureOutputModel
import com.example.frunizone.util.ConstantData
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var imageSlider: ImageSlider
    private lateinit var rcylCategory: RecyclerView
    private lateinit var rcylTopSelling: RecyclerView
    private lateinit var tvTopSellingSeeAll: TextView
    private lateinit var toolbar: Toolbar

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageSlider = view.findViewById(R.id.image_slider)
        rcylCategory = view.findViewById(R.id.rcylCategory)
        rcylTopSelling = view.findViewById(R.id.rcylTopSelling)
        tvTopSellingSeeAll = view.findViewById(R.id.tvTopSellingSeeAll)

        //toolbar = view.findViewById(R.id.toolbar)

        /*toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.icon_search) {
                val intent = Intent(context, Search_Activity::class.java)
                startActivity(intent)
            }
            true
        }*/

        tvTopSellingSeeAll.setOnClickListener {
            (activity as HomeActivity).openFragment(AllProductFragment())
        }

        // API calls
        BannerApi().getBanner(this)
        CategoryApi().getCategory(this)
        FurnitureApi().getFurniture(this)
    }

    // --- Banner ---
    fun setBanner(bannerOutputModal: BannerOutputModel) {
        val imageList: MutableList<SlideModel> = ArrayList()
        bannerOutputModal.banner?.forEach { bannerModal ->
            val pic = ConstantData.SERVER_IMAGE_ADDRESS + bannerModal.ban_img?.substring(1)
            imageList.add(SlideModel(pic, ScaleTypes.FIT))
        }

        imageSlider.setImageList(imageList)
        imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT)
    }

    // --- Category ---
    fun setCategory(model: CategoryOutputModel) {

        val categoryAdapter = CategoryAdapter(
            requireActivity(),
            model.category ?: arrayListOf(),
            object : CategoryAdapter.ItemClickListener {
                override fun onClick(position: Int, model: CategoryModel) {
                    val bundle = Bundle()
                    bundle.putString("id", model.cat_id)
                    // Do your fragment navigation here
                    val fragment = ProductFragment()
                    fragment.arguments = bundle
                    (activity as HomeActivity).openFragment(fragment)

                }
            }
        )
        rcylCategory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rcylCategory.adapter = categoryAdapter
    }
    /*
    fun setFurniture(model: FurnitureOutputModel) {

        val list = ArrayList<FurnitureModel>()

        // Add only 8 items safely
        val limit = minOf(model.sub_category.size, 8)
        for (i in 0 until limit) {
            list.add(model.sub_category[i])
        }

        val adapter = FurnitureAdapter(requireActivity(), list,
            object : FurnitureAdapter.ItemClickListener {
                override fun onClick(item: FurnitureModel) {

                    val bundle = Bundle().apply {
                        putSerializable("product", item)
                    }

                    val fragment = SubCategoryFragment().apply {
                        arguments = bundle
                    }

                    (requireActivity() as HomeActivity).openFragment(fragment)
                }
            })

        rcylTopSelling.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        rcylTopSelling.adapter = adapter
    }*/

    fun setFurniture(model: FurnitureOutputModel) {

        val list = ArrayList<FurnitureModel>()

        // SAFETY CHECK – API returned null
        val subList = model.sub_category ?: arrayListOf()

        if (subList.isEmpty()) {
            Toast.makeText(requireActivity(), "No products found", Toast.LENGTH_SHORT).show()
            rcylTopSelling.adapter = null
            return
        }

        val limit = minOf(subList.size, 8)

        for (i in 0 until limit) {
            list.add(subList[i])
        }

        val adapter = FurnitureAdapter(
            requireActivity(),
            list,
            object : FurnitureAdapter.ItemClickListener {
                override fun onClick(item: FurnitureModel) {

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

        rcylTopSelling.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        rcylTopSelling.adapter = adapter
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}