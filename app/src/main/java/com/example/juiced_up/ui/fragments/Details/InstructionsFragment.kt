package com.example.juiced_up.ui.fragments.Details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.juiced_up.R
import com.example.juiced_up.databinding.FragmentInstructionsBinding
import com.example.juiced_up.utils.Constants
import com.example.juiced_up.utils.Constants.Companion.INSTRUCTIONS


class InstructionsFragment : Fragment() {
    private var binding: FragmentInstructionsBinding? = null
    private var instruction:String=""
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val pref =
            requireContext().getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        instruction = pref.getString(INSTRUCTIONS,null).toString()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentInstructionsBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.instructionsTv?.text=instruction
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InstructionsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InstructionsFragment().apply {
                arguments = Bundle().apply {

                }
            }

        fun newInstance() = InstructionsFragment().apply {

        }
    }
}