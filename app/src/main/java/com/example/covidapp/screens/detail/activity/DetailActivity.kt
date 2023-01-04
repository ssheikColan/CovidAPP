package com.example.covidapp.screens.detail.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.covidapp.databinding.ActivityDetailBinding
//import com.halil.ozel.covid19stats.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import com.example.covidapp.common.utils.Const.Companion.CASES
import com.example.covidapp.common.utils.Const.Companion.COUNTRY
import com.example.covidapp.common.utils.Const.Companion.DEATHS
import com.example.covidapp.common.utils.Const.Companion.FLAG
import com.example.covidapp.common.utils.Const.Companion.RECOVERED
import com.example.covidapp.common.utils.Const.Companion.TESTS
import com.example.covidapp.common.utils.Const.Companion.TODAY_CASE
import com.example.covidapp.common.utils.Const.Companion.TODAY_DEATH

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
        setCountryInfo()
    }

    private fun setView() {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun setCountryInfo() {
        val countryName = intent.getStringExtra(COUNTRY)
        val todayCase = intent.getStringExtra(TODAY_CASE)
        val todayDeath = intent.getStringExtra(TODAY_DEATH)
        val totalCases = intent.getStringExtra(CASES)
        val totalDeaths = intent.getStringExtra(DEATHS)
        val totalTests = intent.getStringExtra(TESTS)
        val totalRecovered = intent.getStringExtra(RECOVERED)

        binding.tvCountryName.text = countryName
        binding.tvTodayCases.text = todayCase
        binding.tvTodayDeath.text = todayDeath
        binding.tvTotalTests.text = totalTests
        binding.tvTotalCases.text = totalCases
        binding.tvTotalDeaths.text = totalDeaths
        binding.tvTotalRecovered.text = totalRecovered
        Picasso.get().load(intent.getStringExtra(FLAG))
            .into(binding.ivCountryPoster)
    }
}