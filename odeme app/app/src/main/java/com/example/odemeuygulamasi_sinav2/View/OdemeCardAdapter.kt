package com.example.odemeuygulamasi_sinav2.View

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.odemeuygulamasi_sinav2.Model.Odeme
import com.example.odemeuygulamasi_sinav2.databinding.OdemeGecmisiItemBinding

class OdemeCardAdapter(var context: Context, var odemeList: List<Odeme>, var cListener:(Int)->Unit) : RecyclerView.Adapter<OdemeCardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OdemeCardViewHolder {
        val from = LayoutInflater.from(context)
        val binding = OdemeGecmisiItemBinding.inflate(from,parent,false)
        return OdemeCardViewHolder(binding, cListener)
    }


    override fun onBindViewHolder(holder: OdemeCardViewHolder, position: Int) {
        holder.bindOdemeGecmisi(odemeList[position], position)
    }


    override fun getItemCount(): Int {
        return odemeList.size
    }



}