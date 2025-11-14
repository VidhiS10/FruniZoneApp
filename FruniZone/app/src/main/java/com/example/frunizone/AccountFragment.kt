package com.example.frunizone


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.frunizone.util.ConstantData

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences

import android.widget.TextView

import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
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

    private lateinit var btnEdit: TextView
    private lateinit var faq: TextView
    private lateinit var order: TextView
    private lateinit var contactus: TextView
    private lateinit var aboutus: TextView
    private lateinit var logout: Button
    private lateinit var proName: TextView
    private lateinit var proEmail: TextView
    private lateinit var proPic: CircleImageView

    /* override fun onCreateView(
         inflater: LayoutInflater, container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         val view = inflater.inflate(R.layout.fragment_account, container, false)

         val btnLogout = view.findViewById<Button>(R.id.logout)
         btnLogout.setOnClickListener {
             // clear login session
             val sharedPref = requireActivity().getSharedPreferences(ConstantData.SP_LOGIN_PREFS, 0)
             val editor = sharedPref.edit()
             editor.clear() // removes all saved login data
             editor.apply()
 //toast
             Toast.makeText(requireContext(), "Logged out successfully!", Toast.LENGTH_SHORT).show()
 // redirect to LoginActivity
             val intent = Intent(requireActivity(), LoginActivity::class.java)
             startActivity(intent)
             requireActivity().finish()
         }

         // Inflate the layout for this fragment
         return view
     }*/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnEdit = view.findViewById(R.id.btnEdit)
        faq = view.findViewById(R.id.faq)
        order = view.findViewById(R.id.order)
        contactus = view.findViewById(R.id.contactus)
        aboutus = view.findViewById(R.id.aboutus)
        logout = view.findViewById(R.id.logout)
        proName = view.findViewById(R.id.pro_name)
        proEmail = view.findViewById(R.id.pro_email)
        proPic = view.findViewById(R.id.pro_pic)

        val sp = requireActivity().getSharedPreferences(ConstantData.SP_LOGIN_PREFS, Context.MODE_PRIVATE)
        val name = sp.getString(ConstantData.KEY_USERNAME, "") ?: ""
        val email = sp.getString(ConstantData.KEY_EMAIL, "") ?: ""
        val pic = sp.getString(ConstantData.KEY_PIC, "") ?: ""
//        Toast.makeText(requireContext(), "Email from SP: $email", Toast.LENGTH_LONG).show()
        proName.text = name
        proEmail.text = email

        if (pic.isEmpty()) {
            proPic.setImageResource(R.drawable.profile)
        } else {
            Glide.with(requireContext())
                .load("${ConstantData.SERVER_ADDRESS}furniture/api/img/$pic")
                .into(proPic)
        }

        contactus.setOnClickListener {
            //Toast.makeText(requireContext(), "Contact Us", Toast.LENGTH_SHORT).show()
            (activity as? HomeActivity)?.openFragment(ContactUsFragment())
        }

        order.setOnClickListener {
            //  Toast.makeText(requireContext(), "order", Toast.LENGTH_SHORT).show()
            (activity as? HomeActivity)?.openFragment(YourOrderFragment())
        }

        btnEdit.setOnClickListener {
//            Toast.makeText(requireContext(), "edit", Toast.LENGTH_SHORT).show()
            (activity as? HomeActivity)?.openFragment(EditProfileFragment())
        }

        faq.setOnClickListener {
//             Toast.makeText(requireContext(), "faq", Toast.LENGTH_SHORT).show()
            (activity as? HomeActivity)?.openFragment(FAQFragment())
        }

        aboutus.setOnClickListener {
//            Toast.makeText(requireContext(), "about us", Toast.LENGTH_SHORT).show()

            (activity as? HomeActivity)?.openFragment(AboutUsFragment())
        }

        logout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        val dialogView = layoutInflater.inflate(R.layout.logout_dailog, null)
        val dialogTitle = dialogView.findViewById<TextView>(R.id.dialog_title)

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(true)
            .setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                logoutUser()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun logoutUser() {
//        val sp = requireActivity().getSharedPreferences(ConstantData.SP_LOGIN_PREFS, Context.MODE_PRIVATE)
//        sp.edit().clear().apply()

        // clear login session
        val sharedPref = requireActivity().getSharedPreferences(ConstantData.SP_LOGIN_PREFS, 0)
        val editor = sharedPref.edit()
        editor.clear() // removes all saved login data
        editor.apply()
//toast
        Toast.makeText(requireContext(), "Logged out successfully!", Toast.LENGTH_SHORT).show()

        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}