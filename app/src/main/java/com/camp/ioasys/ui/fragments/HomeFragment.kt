package com.camp.ioasys.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.camp.ioasys.R
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setupRecycler()

        requestCompanies(args.accessToken!!, args.client!!, args.uid!!, "")
        setupQueryListener()

        mainViewModel.companies.observe(viewLifecycleOwner, Observer { res ->
            when (res) {
                is NetworkResult.Success -> {
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

        return binding.root
    }

    private fun setupQueryListener() {
        binding.homeSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    requestCompanies(args.accessToken!!, args.client!!, args.uid!!, query)
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.fill_to_search),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_companies_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
    }

    private fun setupRecycler() {
        binding.homeCompaniesRecycler.adapter = mAdapter
        binding.homeCompaniesRecycler.layoutManager = LinearLayoutManager(requireContext())
        showShimmer()
    }

    private fun requestCompanies(accessToken: String, client: String, uid: String, query: String?) {
        mainViewModel.loadCompanies(accessToken, client, uid, query)
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