package com.example.missingcats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.missingcats.databinding.FragmentSecondBinding
import com.example.missingcats.models.UserViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {


    private var _binding: FragmentSecondBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = userViewModel.userLiveData.value
        if (currentUser != null) {
            //binding.emailInputField.setText(currentUser.email) // half automatic login
            // current user exists: No need to login again
            findNavController().popBackStack()
        }
        val errorData = userViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { m ->
            binding.messageView.text = m
        }
        //binding.messageView.text = "Current user ${currentUser?.email}"
        binding.signIn.setOnClickListener {
            val email = binding.emailInputField.text.toString().trim()
            val password = binding.passwordInputField.text.toString().trim()
            if (email.isEmpty()) {
                binding.emailInputField.error = "No email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.passwordInputField.error = "No password"
                return@setOnClickListener
            }
            userViewModel.login(email, password)
            //TODO Gør sådan at kan ikke skifter væk fra login, hvis man skriver forkert
            if(errorData.toString() != ""){
                val snack = Snackbar.make(it, "Login successful", Snackbar.LENGTH_LONG)
                snack.show()
                findNavController().popBackStack()
            }

        }
        binding.buttonCreateUser.setOnClickListener {
            val email = binding.emailInputField.text.toString().trim()
            val password = binding.passwordInputField.text.toString().trim()
            if (email.isEmpty()) {
                binding.emailInputField.error = "No email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.passwordInputField.error = "No password"
                return@setOnClickListener
            }
            userViewModel.signUp(email, password)
            //TODO gør så der kommer en god besked når man har oprettet bruger
            val snack = Snackbar.make(it, "User Created", Snackbar.LENGTH_LONG)
            snack.show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}