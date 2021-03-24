package com.camp.ioasys.bindingAdapters

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.camp.ioasys.util.NetworkResult
import okhttp3.Headers

class LoginBingingAdapter {

    companion object {

        @BindingAdapter("readApiResponse")
        @JvmStatic
        fun errorTextVisibility(
            textView: TextView,
            apiResponse: NetworkResult<Headers>?
        ) {
            when (apiResponse) {
                is NetworkResult.Error -> {
                    textView.visibility = View.VISIBLE
                    textView.text = apiResponse.message.toString()
                }
                is NetworkResult.Success -> {
                    textView.visibility = View.INVISIBLE
                }
                else -> {
                    Log.i("Data", apiResponse.toString())
                }
            }
        }

    }

}