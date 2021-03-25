package com.camp.ioasys.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.camp.ioasys.databinding.CompanyRowLayoutBinding
import com.camp.ioasys.models.CompaniesResponse
import com.camp.ioasys.models.Company

class CompaniesAdapter: RecyclerView.Adapter<CompaniesAdapter.CompaniesViewHolder>() {

    private var companies = emptyList<Company>()

    class CompaniesViewHolder(
        private val binding: CompanyRowLayoutBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(company: Company) {
            binding.company = company
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CompaniesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CompanyRowLayoutBinding.inflate(layoutInflater, parent, false)
                return CompaniesViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompaniesViewHolder {
        return CompaniesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CompaniesViewHolder, position: Int) {
        val currentPosition = companies[position]
        holder.bind(currentPosition)
    }

    override fun getItemCount(): Int {
        return companies.size
    }

    fun setData(data: CompaniesResponse) {
        companies = data.companies
        notifyDataSetChanged()
    }

}