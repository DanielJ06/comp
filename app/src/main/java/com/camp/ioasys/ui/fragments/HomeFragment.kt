package com.camp.ioasys.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.camp.ioasys.R
import com.camp.ioasys.adapters.CompaniesAdapter
import com.camp.ioasys.databinding.FragmentHomeBinding
import com.camp.ioasys.util.NetworkResult
import com.camp.ioasys.util.observeOnce
import com.camp.ioasys.viewmodels.CompaniesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val args: HomeFragmentArgs by navArgs()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var companiesViewModel: CompaniesViewModel
    private val mAdapter by lazy { CompaniesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        companiesViewModel =
            ViewModelProvider(requireActivity()).get(CompaniesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        setupRecycler()
        setupQueryListener()

        binding.homeSearchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                Log.i("debug", "has")
                binding.homeLogoImage.visibility = View.INVISIBLE
            } else {
                Log.i("debug", "no")
                binding.homeLogoImage.visibility = View.VISIBLE
            }
        }

        if (companiesViewModel.hasInternetConnection()) {
            requestCompanies(args.accessToken!!, args.client!!, args.uid!!)
        } else {
            readDatabase()
        }

        return binding.root
    }

    private fun setupRecycler() {
        binding.homeCompaniesRecycler.adapter = mAdapter
        binding.homeCompaniesRecycler.layoutManager = LinearLayoutManager(requireContext())
        showShimmer()
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            companiesViewModel.readCompanies.observeOnce(viewLifecycleOwner, { db ->
                if (db.isNotEmpty()) {
                    Log.d("HomeFragment", "readDatabase called")
                    mAdapter.setData(db[0].company)
                    hideShimmerEffect()
                } else {
                    requestCompanies(args.accessToken!!, args.client!!, args.uid!!)
                }
            })
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            companiesViewModel.readCompanies.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].company)
                }
            })
        }
    }

    private fun requestCompanies(accessToken: String, client: String, uid: String) {
        Log.d("HomeFragment", "requestApi called")
        companiesViewModel.loadCompanies(accessToken, client, uid)
        companiesViewModel.companies.observe(viewLifecycleOwner, { res ->
            when (res) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    if (res.data?.companies?.isEmpty() == true) {
                        binding.emptyIcon.visibility = View.VISIBLE
                        binding.emptyText.visibility = View.VISIBLE
                        binding.homeCompaniesRecycler.visibility = View.INVISIBLE
                    } else {
                        binding.emptyIcon.visibility = View.INVISIBLE
                        binding.emptyText.visibility = View.INVISIBLE
                        binding.homeCompaniesRecycler.visibility = View.VISIBLE
                    }
                    res.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Loading -> {
                    showShimmer()
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    binding.homeCompaniesRecycler.visibility = View.INVISIBLE
                    binding.emptyIcon.visibility = View.VISIBLE
                    binding.emptyText.visibility = View.VISIBLE
                    binding.emptyText.text = res.message
                    hideShimmerEffect()
                }
            }
        })
    }

    private fun searchCompanies(accessToken: String, client: String, uid: String, query: String?) {
        companiesViewModel.searchCompanies(accessToken, client, uid, query)
        companiesViewModel.companies.observe(viewLifecycleOwner, { res ->
            when (res) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    if (res.data?.companies?.isEmpty() == true) {
                        binding.emptyIcon.visibility = View.VISIBLE
                        binding.emptyText.visibility = View.VISIBLE
                        binding.homeCompaniesRecycler.visibility = View.INVISIBLE
                    } else {
                        binding.emptyIcon.visibility = View.INVISIBLE
                        binding.emptyText.visibility = View.INVISIBLE
                        binding.homeCompaniesRecycler.visibility = View.VISIBLE
                    }
                    res.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Loading -> {
                    showShimmer()
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    binding.emptyIcon.visibility = View.VISIBLE
                    binding.homeCompaniesRecycler.visibility = View.INVISIBLE
                    binding.emptyText.visibility = View.VISIBLE
                    binding.emptyText.text = res.message
                    hideShimmerEffect()
                }
            }
        })
    }

    private fun setupQueryListener() {
        binding.homeSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchCompanies(args.accessToken!!, args.client!!, args.uid!!, query)
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

    private fun showShimmer() {
        binding.homeCompaniesRecycler.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.homeCompaniesRecycler.hideShimmer()
    }
}