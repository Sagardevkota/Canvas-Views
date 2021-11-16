package com.example.canvaspractice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val chartView = findViewById<ChartView>(R.id.chartView)
        chartView.setItems(listOf(
            ChartView.Item("Sunil",20),
            ChartView.Item("Sagar",50),
            ChartView.Item("Sunita",10),
            ChartView.Item("Sandip",80),
            ChartView.Item("Sarnith",120),
            ChartView.Item("Sunita",10),
            ChartView.Item("Sandip",80),
        ))


    }
}