package com.example.missingcats

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.missingcats.databinding.FragmentFirstBinding
import com.example.missingcats.models.CatViewModel
import com.example.missingcats.models.MyAdapter
import com.example.missingcats.models.UserViewModel


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val catViewModel: CatViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        catViewModel.catLiveData.observe(viewLifecycleOwner) { cats ->
            //Log.d("APPLE", "observer $cats")
            binding.progressbar.visibility = View.GONE
            binding.recyclerView.visibility = if (cats == null) View.GONE else View.VISIBLE
            if (cats != null) {
                val adapter = MyAdapter(cats) { position ->
                    val action =
                        FirstFragmentDirections.actionFirstFragmentToDetailsFragment(position)
                    findNavController().navigate(action /*R.id.action_FirstFragment_to_SecondFragment*/)
                }
                // binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                var columns = 2
                val currentOrientation = this.resources.configuration.orientation
                if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    columns = 4
                } else if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    columns = 2
                }
                binding.recyclerView.layoutManager = GridLayoutManager(this.context, columns)

                binding.recyclerView.adapter = adapter
            }
        }

        //TODO fab skifter ikke
        binding.fab.setOnClickListener { _ ->
            userViewModel.userLiveData.observe(viewLifecycleOwner) { user ->
                if (user != null) {
                    findNavController().navigate(R.id.newCatFragment)
                } else {
                    binding.fab.setOnClickListener { _ ->
                        findNavController().navigate(R.id.SecondFragment)
                    }
                }
            }

            catViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
                binding.textviewMessage.text = errorMessage
            }

            catViewModel.reload()

            binding.swiperefresh.setOnRefreshListener {
                catViewModel.reload()
                binding.swiperefresh.isRefreshing = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
