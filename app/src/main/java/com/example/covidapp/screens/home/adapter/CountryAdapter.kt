package com.example.covidapp.screens.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.covidapp.common.models.Country
import com.example.covidapp.databinding.CountryRowBinding
//import com.halil.ozel.covid19stats.databinding.CountryRowBinding
import com.example.covidapp.screens.home.adapter.CountryAdapter.CountryHolder
import java.util.*

class CountryAdapter : RecyclerView.Adapter<CountryHolder>(), Filterable {
    private var onItemClickListener: OnItemClickListener? = null
    private var countriesList: List<Country>? = null

    private var countriesListNew: List<Country>? = null

    private var countriesListed: List<Country>? = null
    private var context: Context? = null
    fun setCountryList(context: Context?, countriesListNew: List<Country>?) {
        this.context = context
        if (this.countriesListNew == null) {
            this.countriesListNew = (countriesListNew)
            countriesListed = (countriesListNew)
            notifyItemChanged(0, countriesListed!!.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return this@CountryAdapter.countriesListNew!!.size
                }

                override fun getNewListSize(): Int {
                    return listOf( countriesListNew).size
                }



                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return this@CountryAdapter.countriesListNew!![oldItemPosition].Country === countriesListNew!![newItemPosition].Country
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val newMovie = this@CountryAdapter.countriesListNew!![oldItemPosition]
                    val oldMovie = countriesListNew!![newItemPosition]
                    return newMovie.Country === oldMovie.Country
                }
            })
            this.countriesListNew = (countriesListNew)
            countriesListed = (countriesListNew)
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryHolder {
        val view = CountryRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        holder.binding.tvCountryName.text = "Country : "+ countriesListed!![position].Country
        holder.binding.NewConfirmed.text = "NewConfirmed : "+ countriesListed!![position].NewConfirmed.toString()
        holder.binding.TotalConfirmed.text = "TotalConfirmed : "+ countriesListed!![position].TotalConfirmed
        holder.binding.NewDeaths.text = "NewDeaths : "+ countriesListed!![position].NewDeaths
        holder.binding.TotalDeaths.text = "TotalDeaths : "+ countriesListed!![position].TotalDeaths
        holder.binding.TotalRecovered.text = "TotalRecovered : "+ countriesListed!![position].TotalRecovered
//        Picasso.get().load(countriesListed!![position].countryInfo!!.flag)
//            .into(holder.binding.ivCountryPoster)
        holder.itemView.setOnClickListener {
            if (onItemClickListener == null) return@setOnClickListener
            onItemClickListener?.onItemClick(countriesListed!![position])
        }
    }

    override fun getItemCount(): Int {
        return if (countriesListNew != null) {
            countriesListed!!.size
        } else {
            0
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                countriesListed = if (charString.isEmpty()) {
                    countriesListNew
                } else {
                    val filteredList: MutableList<Country> = ArrayList()
                    for (movie in countriesListNew!!) {
                        if (movie.Country!!.lowercase(Locale.getDefault())
                                .contains(charString.lowercase(Locale.getDefault()))
                        ) {
                            filteredList.add(movie)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = countriesListed
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                countriesListed = filterResults.values as ArrayList<Country>
                notifyDataSetChanged()
            }
        }
    }

    inner class CountryHolder(val binding: CountryRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(item: Country)
    }
}