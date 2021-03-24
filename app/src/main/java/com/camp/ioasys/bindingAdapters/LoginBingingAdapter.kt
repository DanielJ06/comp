package com.camp.ioasys.bindingAdapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.camp.ioasys.util.NetworkResult

class LoginBingingAdapter {

    companion object {

        @BindingAdapter("apiResponse")
        @JvmStatic
        fun errorTextVisibility(
            textView: TextView,
            apiResponse: NetworkResult<Any>
        ) {
            if (apiResponse is NetworkResult.Error) {
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            } else if (apiResponse is NetworkResult.Success) {
                textView.visibility = View.INVISIBLE
            }
        }

    }

}