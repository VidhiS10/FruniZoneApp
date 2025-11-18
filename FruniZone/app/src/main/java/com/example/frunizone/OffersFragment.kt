package com.example.frunizone


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frunizone.adapter.CouponAdapter
import com.example.frunizone.api.CouponApi
import com.example.frunizone.model.CouponModel
import com.example.frunizone.model.CouponOutputModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var rcylCoupon: RecyclerView
//private lateinit var toolbar: Toolbar
private lateinit var rootView: View

/**
 * A simple [Fragment] subclass.
 * Use the [OffersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OffersFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_offers, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcylCoupon = view.findViewById(R.id.rcylCoupon)
        /*toolbar = view.findViewById(R.id.toolbar)

        toolbar.setNavigationOnClickListener {
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(intent)
        }*/

        CouponApi().getCoupon(this)
    }

    fun setCoupon(model: CouponOutputModel) {
        val adapter = CouponAdapter(
            requireActivity(),
            model.coupen ?: ArrayList(),
            object : CouponAdapter.ItemClickListener {
                override fun onClick(position: Int, model: CouponModel) {
                    // Handle item click event here if needed
                }
            }
        )

        rcylCoupon.layoutManager = LinearLayoutManager(requireContext())
        rcylCoupon.adapter = adapter
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OffersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OffersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}