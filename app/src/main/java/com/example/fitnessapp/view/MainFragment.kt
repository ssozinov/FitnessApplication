package com.example.fitnessapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.fitnessapp.MyApp
import com.example.fitnessapp.databinding.FragmentMainBinding
import com.example.fitnessapp.model.Item
import com.example.fitnessapp.viewmodel.LoadState
import com.example.fitnessapp.viewmodel.MainFragmentViewModel
import com.example.fitnessapp.viewmodel.TrainingInfoAdapter
import javax.inject.Inject


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainFragmentViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val trainingAdapter = TrainingInfoAdapter()
        binding.trainingAdapter.adapter = trainingAdapter

        viewModel.trainingInfoLiveData.observe(viewLifecycleOwner) { loadState ->
            when (loadState) {
                is LoadState.Success<*> -> {
                    binding.progressBar.visibility = GONE
                    val trainingInfo = loadState.data
                    trainingAdapter.setData(trainingInfo as List<Item>)
                }

                is LoadState.Error -> {
                    val error = loadState.error
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                }

                is LoadState.Loading -> {
                    binding.progressBar.visibility = VISIBLE
                }
            }
        }
    }
}
