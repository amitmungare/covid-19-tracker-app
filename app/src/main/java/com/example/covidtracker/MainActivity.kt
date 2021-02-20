package com.example.covidtracker


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.activeTv
import kotlinx.android.synthetic.main.activity_main.confirmedTv
import kotlinx.android.synthetic.main.activity_main.deathTv
import kotlinx.android.synthetic.main.activity_main.recoveredTv
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.Level.parse
import kotlin.math.min

class MainActivity : AppCompatActivity() {

    lateinit var stateAdapter:StateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header,list,false))
        fetchResults()
    }

    private fun fetchResults() {

        GlobalScope.launch {
            val response = withContext(Dispatchers.IO) {Client.api.execute()}
            if(response.isSuccessful){

                val data = Gson().fromJson(response.body?.string(),Response::class.java)

                launch(Dispatchers.Main){
                    bindCombinedData(data.statewise[0])
                    bindStateWiseData(data.statewise.subList(0,data.statewise.size))
                }
            }
        }
    }

    private fun bindStateWiseData(subList: List<StatewiseItem>) {

        stateAdapter = StateAdapter(subList)
        list.adapter= stateAdapter

    }

    private fun bindCombinedData(data: StatewiseItem) {

        val lastUpdatedTime = data.lastupdatedtime
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        lastUpdateTv.text = "Last Updated\n ${getTimeAgo(simpleDateFormat.parse(lastUpdatedTime))}"

        confirmedTv.text = data.confirmed
        activeTv.text = data.active
        recoveredTv.text = data.recovered
        deathTv.text = data.deaths

    }

    fun getTimeAgo(past: Date):String{
        val now =Date()
        val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
        val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)

        return when {
            seconds<60 ->{
                "Few seconds ago"
            }
            minutes < 60 ->{
                "$minutes minutes age"
            }
            hours < 24 ->{
                "$hours hour ${minutes%60}min ago"
            }else -> {
                SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(past).toString()
            }
        }

    }

}