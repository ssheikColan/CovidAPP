package com.example.covidapp.screens.home.activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.SearchManager
import android.content.Intent

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covidapp.LoginActivity
import com.example.covidapp.R
//import com.example.covidapp.databinding.ActivityMainBinding
import com.example.covidapp.screens.detail.viewmodel.DetailViewModel
import com.example.covidapp.screens.home.viewmodel.HomeViewModel
//import com.halil.ozel.covid19stats.R
import com.example.covidapp.common.models.SummaryModel
//import com.halil.ozel.covid19stats.databinding.ActivityMainBinding
import com.example.covidapp.screens.home.adapter.CountryAdapter
import com.example.covidapp.common.utils.Status
import com.example.covidapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.lazday.sharedpreferences.helper.PrefHelper

import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    private val homeViewModel: HomeViewModel by viewModels()
    private val usersDetailViewModel: DetailViewModel by viewModels()
    private var searchView: SearchView? = null
    private var countryAdapter: CountryAdapter? = null
    private var countriesResponseList: List<SummaryModel>? = null
    private lateinit var binding: ActivityMainBinding


    lateinit var toggle: ActionBarDrawerToggle

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    lateinit var prefHelper: PrefHelper

    private lateinit var mDrawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        prefHelper = PrefHelper(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        }

        mDrawerLayout = findViewById(R.id.my_drawer_layout)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()

            // Handle navigation view item clicks here.
            when (menuItem.itemId) {

                R.id.nav_dashboard -> {
                    mDrawerLayout.closeDrawers()
                }
                R.id.nav_stack -> {
                    val intent = Intent(this, ChartActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                R.id.nav_logout -> {
                    dialogDelete()
                }
            }
            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here

            true
        }




        binding.rvCountry.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        val layoutManager = GridLayoutManager(this, 2)

       binding.rvCountry.layoutManager = layoutManager

        binding.globalLayout.NewConfirmed.text=""
//        binding.rvCountry.layoutManager = LinearLayoutManager(this)
        countryAdapter = CountryAdapter()
        binding.rvCountry.adapter = countryAdapter
        countriesResponseList = ArrayList()

        homeViewModel.countryData.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
//                    it.data?.let { countries ->
//                        countryAdapter?.setCountryList(applicationContext, countries)
//                    }

                   if (it.data?.Countries?.isNotEmpty() == true){
                       it.data?.Countries.let{ countries ->
                           countryAdapter?.setCountryList(applicationContext, countries)
                       }
                       binding.globalLayout.NewConfirmed.text= it.data?.Global?.NewConfirmed.toString()
                       binding.globalLayout.TotalConfirmed.text= it.data?.Global?.TotalConfirmed.toString()
                       binding.globalLayout.NewDeaths.text= it.data?.Global?.NewDeaths.toString()
                       binding.globalLayout.TotalDeaths.text= it.data?.Global?.TotalDeaths.toString()
                       binding.globalLayout.TotalRecovered.text= it.data?.Global?.TotalRecovered.toString()

                   }else{

                   }



                }
                else -> {}
            }
        }






   /*     binding.apply {
            toggle = ActionBarDrawerToggle(this@MainActivity, drawerLayout, R.string.nav_open, R.string.nav_close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

//            supportActionBar?.setDisplayHomeAsUpEnabled(true)
          navView.setNavigationItemSelectedListener(this@MainActivity)
//            navView.setNavigationItemSelectedListener {
//
//            }
        }*/

/*        countryAdapter?.setOnItemClickListener(object : CountryAdapter.OnItemClickListener {
            override fun onItemClick(item: SummaryModel) {
                usersDetailViewModel._countryLiveData.postValue(item.country)

                Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra(COUNTRY, item.country)
                    putExtra(TODAY_CASE, item.todayCases)
                    putExtra(TODAY_DEATH, item.todayDeaths)
                    putExtra(FLAG, item.countryInfo!!.flag)
                    putExtra(CASES, item.cases)
                    putExtra(DEATHS, item.deaths)
                    putExtra(TESTS, item.tests)
                    putExtra(RECOVERED, item.recovered)
                    startActivity(this)
                }
            }
        })*/
    }

    private fun dialogDelete(){
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle(R.string.dialogTitle)
        //set message for alert dialog
//        builder.setMessage(R.string.dialogMessage)
//        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            prefHelper.clear()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        //performing cancel action
//        builder.setNeutralButton("Cancel"){dialogInterface , which ->
//            Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
//        }
        //performing negative action
        builder.setNegativeButton("No"){dialogInterface, which ->

        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }





    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search)
            .actionView as SearchView
        searchView?.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )
        searchView?.maxWidth = Int.MAX_VALUE
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                countryAdapter?.filter?.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                countryAdapter?.filter?.filter(query)
                return false
            }
        })
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (!searchView!!.isIconified) {
            searchView?.isIconified = true
            return
        }
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }



}