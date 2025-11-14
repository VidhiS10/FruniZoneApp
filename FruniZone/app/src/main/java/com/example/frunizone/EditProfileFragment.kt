package com.example.frunizone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.frunizone.api.UpdateProfileApi
import com.google.android.material.textfield.TextInputEditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var edUname: TextInputEditText
private lateinit var edEmail: TextInputEditText
private lateinit var btnDone: Button
private lateinit var btnCancel: Button
/**
 * A simple [Fragment] subclass.
 * Use the [EditProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditProfileFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edUname = view.findViewById(R.id.edUname)
        edEmail = view.findViewById(R.id.edEmail)
        btnDone = view.findViewById(R.id.done)
        btnCancel = view.findViewById(R.id.cancel)

        // Cancel Button
        btnCancel.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // Done Button
        btnDone.setOnClickListener {
            val name = edUname.text.toString().trim()
            val email = edEmail.text.toString().trim()

            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            updateProfile(name, email)
        }
    }

    private fun updateProfile(name: String, email: String) {

        UpdateProfileApi().updateProfile(name, email, requireActivity()) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()

                // Go back after update
                requireActivity().onBackPressedDispatcher.onBackPressed()
            } else {
                Toast.makeText(requireContext(), "Failed to update", Toast.LENGTH_SHORT).show()
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
         * @return A new instance of fragment EditProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}