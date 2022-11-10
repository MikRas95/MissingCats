package com.example.missingcats

import android.icu.text.DateFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.missingcats.databinding.FragmentDetailsBinding
import com.example.missingcats.databinding.FragmentNewCatBinding
import com.example.missingcats.models.Cat
import com.example.missingcats.models.CatViewModel
import com.example.missingcats.models.UserViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class NewCatFragment : Fragment() {
    private var _binding: FragmentNewCatBinding? = null
    private val binding get() = _binding!!
    private val catViewModel: CatViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()


    fun humanDate(date: Long): String? {
        val formatter = DateFormat.getDateInstance()
        if (date != null) {
            return formatter.format(date * 1000L) // seconds to milliseconds'
        }
        return null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewCatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createButton.setOnClickListener{
            val description = binding.editTextDescription.text.toString().trim()
            val place = binding.editTextPlace.text.toString().trim()
            val name = binding.editTextName.text.toString().trim()
            val reward = binding.editTextReward.text
            val userId = Firebase.auth.currentUser?.uid.toString()
            val date = System.currentTimeMillis() / 1000

            if (description.isEmpty()){
                binding.editTextDescription.error = "Is Empty"
                return@setOnClickListener
            }
            if (place.isEmpty()){
                binding.editTextPlace.error = "Is Empty"
                return@setOnClickListener
            }
            if (name.isEmpty()){
                binding.editTextName.error = "Is Empty"
                return@setOnClickListener
            }
            if (reward == null || reward.toString() == ""){
                binding.editTextReward.error = "Must be above 0"
                return@setOnClickListener
            }


            val newCat = Cat(0,name, description, place, reward.toString().toInt(), userId, date, "")
            catViewModel.add(newCat)
            val snack = Snackbar.make(it, "Kat oprettet",Snackbar.LENGTH_LONG)
            snack.show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}