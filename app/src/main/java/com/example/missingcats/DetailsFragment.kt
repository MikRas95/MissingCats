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
import com.example.missingcats.models.Cat
import com.example.missingcats.models.CatViewModel
import com.example.missingcats.models.UserViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val catViewModel: CatViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    private val args: DetailsFragmentArgs by navArgs()

    fun humanDate(date: Long): String? {
        val formatter = DateFormat.getDateInstance()
        if (date != null) {
            return formatter.format(date * 1000L)
        } // seconds to milliseconds'
        return null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = requireArguments()
        val detailsFragmentArgs: DetailsFragmentArgs = DetailsFragmentArgs.fromBundle(bundle)
        val position = detailsFragmentArgs.position
        val cat = catViewModel[position]
        Log.d("APPLE", "observer ${cat?.id}")
        if (cat == null) {
            binding.textviewMessage.text = "No such cat!"
            return
        }

        binding.textviewDescription.text = cat.description
        binding.textviewName.text = cat.name
        binding.textviewPlace.text = cat.place
        binding.textviewDate.text = humanDate(cat.date)
        binding.textviewReward.text = cat.reward.toString().trim()

        binding.DeleteButton.setOnClickListener{
            catViewModel.delete(cat.id)
            val snack = Snackbar.make(it, "Kat Slettet", Snackbar.LENGTH_LONG)
            snack.show()
            findNavController().popBackStack()
        }

        if(Firebase.auth.currentUser?.uid != cat.userId){
            binding.DeleteButton.visibility = View.GONE
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}