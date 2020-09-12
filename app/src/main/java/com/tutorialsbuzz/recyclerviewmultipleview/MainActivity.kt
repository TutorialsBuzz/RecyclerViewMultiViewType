package com.tutorialsbuzz.recyclerviewmultipleview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.tutorialsbuzz.kotlinhelloworld.CustomAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataList = readFromAssets();

        val adapter = CustomAdapter(dataList, this);

        rcv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcv.adapter = adapter;
        rcv.addItemDecoration(SimpleDividerItemDecoration(this))
    }

    private fun readFromAssets(): PostModel {
        val bufferReader = application.assets.open("response.json").bufferedReader()

        val json_string = bufferReader.use {
            it.readText()
        }
        val gson = Gson()
        val postModel = gson.fromJson(json_string, PostModel::class.java)
        return postModel
    }
}

