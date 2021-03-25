package com.camp.ioasys.bindingAdapters

import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.camp.ioasys.models.Company

class CompaniesBindingAdapter {

    companion object {

        @BindingAdapter("onCompanyClickListener")
        @JvmStatic
        fun onCompanyClickListener(
            rowLayout: ConstraintLayout, company: Company
        ) {
            rowLayout.setOnClickListener {
                Log.i("Company", company.toString())
            }
        }

    }

}