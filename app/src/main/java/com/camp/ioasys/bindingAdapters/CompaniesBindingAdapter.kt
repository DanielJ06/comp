package com.camp.ioasys.bindingAdapters

import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.camp.ioasys.R
import com.camp.ioasys.models.Company
import com.camp.ioasys.ui.HomeFragmentDirections

class CompaniesBindingAdapter {

    companion object {

        @BindingAdapter("onCompanyClickListener")
        @JvmStatic
        fun onCompanyClickListener(
            rowLayout: ConstraintLayout, company: Company
        ) {
            rowLayout.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                    company.enterpriseName,
                    company.description,
                    company.city,
                    company.photo
                )
                rowLayout.findNavController().navigate(action)
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun onLoadImage(
            image: ImageView, imageUrl: String
        ) {
            image.load("https://thispersondoesnotexist.com/image") {
                crossfade(200)
                error(R.color.darker_pink)
            }
        }

    }

}