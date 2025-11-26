package com.example.frunizone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.frunizone.model.FurnitureModel
import java.time.LocalDate
import java.time.LocalTime
import com.example.frunizone.util.ConstantData

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.ImageView

import android.widget.Toast

import com.example.frunizone.api.OrderApi
import com.example.frunizone.model.OrderModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var subCategorySlider: ImageSlider
private lateinit var subColor: TextView
private lateinit var subName: TextView
private lateinit var subRating: TextView
private lateinit var subDimension: TextView
private lateinit var subWarranty: TextView
private lateinit var subWeight: TextView
private lateinit var subMaterial: TextView
private lateinit var subSku: TextView
private lateinit var subSaveRupees: TextView
private lateinit var subRupees: TextView
private lateinit var subDescription: TextView
private lateinit var subSpecification: TextView
private lateinit var btnSubCart: Button

/**
 * A simple [Fragment] subclass.
 * Use the [SubCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SubCategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
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
        return inflater.inflate(R.layout.fragment_sub_category, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val furnitureModel = arguments?.getSerializable("product") as FurnitureModel
        val back = view.findViewById<ImageView>(R.id.ivBack)
        val title = view.findViewById<TextView>(R.id.tvTitle)

        title.text = "Product Details"

        back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        subCategorySlider = view.findViewById(R.id.sub_category_slider)
        subColor = view.findViewById(R.id.sub_color)
        subName = view.findViewById(R.id.sub_name)
        subRating = view.findViewById(R.id.Sub_rating)
        subDimension = view.findViewById(R.id.sub_dimention)
        subWarranty = view.findViewById(R.id.sub_warranty)
        subWeight = view.findViewById(R.id.sub_wieght)
        subMaterial = view.findViewById(R.id.sub_material)
        subSku = view.findViewById(R.id.sub_sku)
        subSaveRupees = view.findViewById(R.id.sub_saverupees)
        subRupees = view.findViewById(R.id.sub_rupees)
        subDescription = view.findViewById(R.id.sub_description)
        subSpecification = view.findViewById(R.id.sub_specification)
        btnSubCart = view.findViewById(R.id.btn_sub_cart)

        // set data
        subColor.text = furnitureModel.sub_cat_color
        subName.text = furnitureModel.sub_cat_name
        subRating.text = furnitureModel.sub_cat_product_rating
        subDimension.text = furnitureModel.sub_cat_dimention
        subWarranty.text = furnitureModel.sub_cat_warenty
        subWeight.text = furnitureModel.sub_cat_weight
        subMaterial.text = furnitureModel.sub_cat_primary_material
        subSku.text = furnitureModel.sub_cat_sku
        subSaveRupees.text = furnitureModel.sub_cat_discount
        subRupees.text = furnitureModel.sub_cat_price
        subDescription.text = furnitureModel.sub_cat_description
        subSpecification.text = furnitureModel.sub_cat_specification

        // Slider
        val list = arrayListOf(
            SlideModel(
                ConstantData.SERVER_IMAGE_ADDRESS + furnitureModel.sub_cat_pic1,
                ScaleTypes.CENTER_CROP
            ),
            SlideModel(ConstantData.SERVER_IMAGE_ADDRESS + furnitureModel.sub_cat_pic2, ScaleTypes.CENTER_CROP),
            SlideModel(ConstantData.SERVER_IMAGE_ADDRESS + furnitureModel.sub_cat_pic3, ScaleTypes.CENTER_CROP),
            SlideModel(ConstantData.SERVER_IMAGE_ADDRESS + furnitureModel.sub_cat_pic4, ScaleTypes.CENTER_CROP)
        )

        subCategorySlider.setImageList(list)

        btnSubCart.setOnClickListener {
            val sp = requireActivity().getSharedPreferences(ConstantData.SP_LOGIN_PREFS, Context.MODE_PRIVATE)
            val uid = sp.getString(ConstantData.KEY_ID, "0") ?: "0"

            val currentDate = LocalDate.now()
            val currentTime = LocalTime.now()

            if (uid == "0") {
                Toast.makeText(requireContext(), "Login to Continue", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
            } else {
                val orderModel = OrderModel(
                    "",
                    uid,
                    furnitureModel.sub_cat_id,
                    furnitureModel.sub_cat_name,
                    furnitureModel.sub_cat_pic1,
                    furnitureModel.sub_cat_price,
                    furnitureModel.sub_cat_price,
                    "0",
                    "1",
                    currentDate.toString(),
                    currentTime.toString(),
                    "0",
                    ""
                )

                OrderApi().addOrder(orderModel, requireActivity())
            }
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SubCategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SubCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}