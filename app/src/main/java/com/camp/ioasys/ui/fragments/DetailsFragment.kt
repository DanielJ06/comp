package com.camp.ioasys.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import coil.load
import com.camp.ioasys.R
import com.camp.ioasys.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        setupToolbar()

        binding.detailCompanyTitle.text = args.companyName
        binding.detailCompanyDesc.text = args.companyDesc
        binding.detailCompanyCity.text = args.companyCity
        binding.detailCompanyImage.load("https://empresas.ioasys.com.br/${args.companyImageUrl}") {
            crossfade(200)
            error(R.color.darker_pink)
        }

        return binding.root
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).run {
            setSupportActionBar(binding.detailsToolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setTitle(args.companyName)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}