package com.example.covidapp.screens.home.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.BubbleDataEntry
import com.anychart.chart.common.dataentry.DataEntry
import com.example.covidapp.LoginActivity
import com.example.covidapp.R
import com.example.covidapp.common.models.GraphModel
import com.example.covidapp.common.utils.Status
import com.example.covidapp.databinding.ActivityChartBinding
import com.example.covidapp.screens.home.viewmodel.HomeViewModel
import com.github.mikephil.charting.data.BubbleData
import com.github.mikephil.charting.data.BubbleDataSet
import com.github.mikephil.charting.data.BubbleEntry
import com.github.mikephil.charting.data.Entry
import com.google.android.material.navigation.NavigationView
import com.lazday.sharedpreferences.helper.PrefHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChartActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityChartBinding
    private var countriesResponseList: List<GraphModel>? = null

    lateinit var x: ArrayList<Entry>
    lateinit var y: ArrayList<String>


    var bubbleData: BubbleData? = null
    var bubbleDataSet: BubbleDataSet? = null
    var bubbleEntries: ArrayList<BubbleEntry>? = null

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    private lateinit var mDrawerLayout: DrawerLayout

    lateinit var prefHelper: PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChartBinding.inflate(layoutInflater)
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
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                }
                R.id.nav_stack -> {
                    mDrawerLayout.closeDrawers()
                }
                R.id.nav_logout -> {
                    dialogDelete()
                }
            }
            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here

            true
        }

        bubbleEntries = ArrayList()


        val anyChartView = findViewById<AnyChartView>(R.id.any_chart_view)
        anyChartView.setProgressBar(findViewById(R.id.progress_bar))

        val bubble = AnyChart.bubble()

        bubble.animation(true)

        bubble.title().enabled(true)
        bubble.title().useHtml(true)
        bubble.title()
            .padding(0.0, 0.0, 10.0, 0.0)
            .text(
                "Best sportsmen training data ' +\n" +
                        "        '<br/><span  style=\"color:#929292; font-size: 12px;\">' +\n" +
                        "        '(bubble size means duration, each bubble represents one training)</span>"
            )

        bubble.padding(20.0, 20.0, 10.0, 20.0)

        bubble.yGrid(true)
            .xGrid(true)
            .xMinorGrid(true)
            .yMinorGrid(true)

        bubble.minBubbleSize(5.0)
            .maxBubbleSize(40.0)

        bubble.xAxis(0)
            .title("Average pulse during training")
            .minorTicks(true)
        bubble.yAxis(0)
            .title("Average power")
            .minorTicks(true)

        bubble.legend().enabled(true)
        bubble.labels().padding(0.0, 0.0, 10.0, 0.0)

        val data: MutableList<DataEntry> = ArrayList()


        homeViewModel.graphData.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { countries ->
                        data.add(CustomBubbleDataEntry(1, it.data.Countries,  it.data.Countries, "ByCountryLive", 120,"15-1-2021"))
                        data.add(CustomBubbleDataEntry(1, it.data.ByCountry,  it.data.ByCountry, "DayOne", 120,"15-1-2021"))
                        data.add(CustomBubbleDataEntry(1, it.data.ByCountryAllStatus,  it.data.ByCountryAllStatus, "DayOneTotal", 120,"15-1-2021"))
                        data.add(CustomBubbleDataEntry(1, it.data.ByCountryTotal,  it.data.ByCountryTotal, "LiveCountryStatus", 120,"15-1-2021"))
                        data.add(CustomBubbleDataEntry(1, it.data.ByCountryTotalAllStatus,  it.data.ByCountryTotalAllStatus, "Stats", 120,"15-1-2021"))
                        data.add(CustomBubbleDataEntry(1, it.data.DayOne,  it.data.DayOne, "Summary", 120,"15-1-2021"))

                        data.add(CustomBubbleDataEntry(1, it.data.DayOneLive,  it.data.DayOneLive, "Summary", 120,"15-1-2021"))
                        data.add(CustomBubbleDataEntry(1, it.data.DayOneTotal,  it.data.DayOneTotal, "Summary", 120,"15-1-2021"))
                        data.add(CustomBubbleDataEntry(1, it.data.DayOneAllStatus,  it.data.DayOneAllStatus, "Summary", 120,"15-1-2021"))

                        data.add(CustomBubbleDataEntry(1, it.data.CountryDayOneTotalAllStatus,  it.data.CountryDayOneTotalAllStatus, "Summary", 120,"15-1-2021"))
                        data.add(CustomBubbleDataEntry(1, it.data.LiveCountryStatus,  it.data.LiveCountryStatus, "Summary", 120,"15-1-2021"))
                        data.add(CustomBubbleDataEntry(1, it.data.LiveCountryStatusAfterDate,  it.data.LiveCountryStatusAfterDate, "Summary", 120,"15-1-2021"))

                        data.add(CustomBubbleDataEntry(1, it.data.WorldDaily,  it.data.WorldDaily, "Summary", 120,"15-1-2021"))
                        data.add(CustomBubbleDataEntry(1, it.data.Stats,  it.data.Stats, "Summary", 120,"15-1-2021"))
                        data.add(CustomBubbleDataEntry(1, it.data.Default,  it.data.Default, "Summary", 120,"15-1-2021"))
                        data.add(CustomBubbleDataEntry(1, it.data.SubmitWebhook,  it.data.SubmitWebhook, "Summary", 120,"15-1-2021"))
                        data.add(CustomBubbleDataEntry(1, it.data.Summary,  it.data.Summary, "Summary", 120,"15-1-2021"))

                        data.add(CustomBubbleDataEntry(1, it.data.PremiumCountry,  it.data.PremiumCountry, "Summary", 120,"15-1-2021"))
                        data.add(CustomBubbleDataEntry(1, it.data.PremiumSummaryCountry,  it.data.PremiumSummaryCountry, "Summary", 120,"15-1-2021"))
                        data.add(CustomBubbleDataEntry(1, it.data.PremiumCountryData,  it.data.PremiumCountryData, "Summary", 120,"15-1-2021"))
                        data.add(CustomBubbleDataEntry(1, it.data.PremiumCountryTests,  it.data.PremiumCountryTests, "Summary", 120,"15-1-2021"))
                        data.add(CustomBubbleDataEntry(1, it.data.PremiumTravelAdvice,  it.data.PremiumTravelAdvice, "Summary", 120,"15-1-2021"))
                    }

                    bubble.bubble(data)
                        .name("Covid APP")

                }
                else -> {}
            }
        }




     /*   data.clear()
        data.add(CustomBubbleDataEntry(2, 165, 145, "10/22/2014", 95))
        data.add(CustomBubbleDataEntry(2, 147, 71, "07/25/2014", 53))
        data.add(CustomBubbleDataEntry(2, 157, 138, "08/18/2014", 115))
        data.add(CustomBubbleDataEntry(2, 179, 107, "07/05/2014", 91))
        data.add(CustomBubbleDataEntry(2, 187, 65, "06/21/2014", 90))
        data.add(CustomBubbleDataEntry(2, 142, 139, "11/05/2014", 94))
        data.add(CustomBubbleDataEntry(2, 136, 90, "06/22/2014", 90))
        data.add(CustomBubbleDataEntry(2, 166, 70, "09/18/2014", 54))
        data.add(CustomBubbleDataEntry(2, 161, 131, "01/07/2015", 121))

        bubble.bubble(data)
            .name("Judy Evans")
*/

    /*    bubble.bubble(data)
            .name("Daniel Williamson")*/

        bubble.tooltip()
            .useHtml(true)
            .fontColor("#fff")
            .format(
                ("function() {\n" +
                        "        return this.getData('data') + '<br/>' +\n" +
                        "          'Counts: <span style=\"color: #d2d2d2; font-size: 12px\">' +\n" +
                        "          this.getData('value') + '</span></strong><br/>' +\n" +
                        "          'Date: <span style=\"color: #d2d2d2; font-size: 12px\">' +\n" +
                        "          this.getData('date') + '</span></strong><br/>' +\n" +
                        "          '</span></strong>';\n" +
                        "      }")
            )

        anyChartView.setChart(bubble)






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


    //appbar - toolbar button click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private class CustomBubbleDataEntry internal constructor(
        training: Int?,
        x: Int?,
        value: Int?,
        data: String?,
        size: Int?,
        date: String?,
    ) :
        BubbleDataEntry(x, value, size) {
        init {
            setValue("training", training)
            setValue("data", data)
            setValue("date", date)
        }
    }

}
