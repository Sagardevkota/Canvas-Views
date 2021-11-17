package com.example.canvaspractice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.clockview.ClockView
import com.sagardev.BarChartView.ChartView
import com.sagardev.piechartview.PieChartView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
        val chartView = findViewById<ChartView>(R.id.pieChartView)
        chartView.setItems(listOf(
            ChartView.Item("Sunil",20),
            ChartView.Item("Sagar",50),
            ChartView.Item("Sunita",10),
            ChartView.Item("Sandip",80),
            ChartView.Item("Sarnith",120),
            ChartView.Item("Sunita",10),
            ChartView.Item("Sandip",80),
        ))
//
//        val pieChartView = findViewById<PieChartView>(R.id.pieChartView)
//        pieChartView.setPoints(intArrayOf(25,25,25,25))


    }
}