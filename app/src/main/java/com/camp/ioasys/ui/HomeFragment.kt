package com.camp.ioasys.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.camp.ioasys.adapters.CompaniesAdapter
import com.camp.ioasys.databinding.FragmentHomeBinding
import com.camp.ioasys.util.NetworkResult
import com.camp.ioasys.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val args: HomeFragmentArgs by navArgs()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private val mAdapter by lazy { CompaniesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupRecycler()
        requestCompanies(args.accessToken!!, args.client!!, args.uid!!)

        return binding.root
    }

    private fun setupRecycler() {
        binding.homeCompaniesRecycler.adapter = mAdapter
        binding.homeCompaniesRecycler.layoutManager = LinearLayoutManager(requireContext())
        showShimmer()
    }

    private fun requestCompanies(accessToken: String, client: String, uid: String) {
        mainViewModel.loadCompanies(accessToken, client, uid)
        mainViewModel.companies.observe(viewLifecycleOwner, Observer { res ->
            when (res) {
                is NetworkResult.Success -> {
                    Log.i("Debug", "successHome")
                    hideShimmerEffect()
                    res.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Loading -> {
                    showShimmer()
                }
                else -> {
                    Log.i("Data", res.message.toString())
                    hideShimmerEffect()
                }
            }
        })

    }

    private fun showShimmer() {
        binding.homeCompaniesRecycler.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.homeCompaniesRecycler.hideShimmer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}