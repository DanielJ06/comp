package com.camp.ioasys.bindingAdapters

import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import coil.load
import com.camp.ioasys.R

class CompaniesBindingAdapter {

    companion object {

        @BindingAdapter("onCompanyClickListener")
        @JvmStatic
        fun onCompanyClickListener(
            rowLayout: ConstraintLayout, companyId: Int
        ) {
            rowLayout.setOnClickListener {
                Log.i("CompanyId", companyId.toString())
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun onLoadImage(
            image: ImageView, imageUrl: String
        ) {
            image.load("https://thispersondoesnotexist.com/image") {
                crossfade(600)
                error(R.color.darker_pink)
            }
        }

    }

}