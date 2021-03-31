package com.camp.ioasys.util

import androidx.recyclerview.widget.DiffUtil
import com.camp.ioasys.models.CompaniesResponse
import com.camp.ioasys.models.Company

class MyDiffUtil(
    private val oldList: List<Company>,
    private val newList: CompaniesResponse
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.companies.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList.companies[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition] != newList.companies[newItemPosition] -> {
                false
            }
            else -> true
        }
    }

}