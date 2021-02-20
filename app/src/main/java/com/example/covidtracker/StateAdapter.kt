package com.example.covidtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item_list.view.*

class StateAdapter (val list: List<StatewiseItem>): BaseAdapter(){
    override fun getCount()=list.size

    override fun getItem(position: Int)= list[position]

    override fun getItemId(position: Int)=position.toLong()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)

        val item:StatewiseItem = list[position]

        view.confirmedTv.text = spanniableDelta("${item.confirmed}\n ↑ ${item.deltaconfirmed?:"0"} ","#D32F2F",item.confirmed ?.length?:0)
        view.activeTv.text = spanniableDelta("${item.active}\n ↑ ${item.deltaactive?:"0"} ","#1976D2",item.active ?.length?:0)
        view.recoveredTv.text = spanniableDelta("${item.recovered}\n ↑ ${item.deltarecovered?:"0"} ","#388E3C",item.recovered ?.length?:0)
        view.deathTv.text = spanniableDelta("${item.deaths}\n ↑ ${item.deltadeaths?:"0"} ","#FBC02D",item.deaths ?.length?:0)
        view.stateTv.text = item.state

        return view
    }



}
















