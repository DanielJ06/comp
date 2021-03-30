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
            oldList[oldItemPosition].id != newList.companies[newItemPosition].id -> {
                false
            }
            oldList[oldItemPosition].city != newList.companies[newItemPosition].city -> {
                false
            }
            oldList[oldItemPosition].description != newList.companies[newItemPosition].description -> {
                false
            }
            oldList[oldItemPosition].country != newList.companies[newItemPosition].country -> {
                false
            }
            oldList[oldItemPosition].enterpriseName != newList.companies[newItemPosition].enterpriseName -> {
                false
            }
            oldList[oldItemPosition].enterpriseType != newList.companies[newItemPosition].enterpriseType -> {
                false
            }
            oldList[oldItemPosition].photo != newList.companies[newItemPosition].photo -> {
                false
            }
            else -> true
        }
    }

}