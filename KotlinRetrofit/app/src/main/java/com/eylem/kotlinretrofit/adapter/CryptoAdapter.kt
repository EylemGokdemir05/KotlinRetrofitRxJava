package com.eylem.kotlinretrofit.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eylem.kotlinretrofit.R
import com.eylem.kotlinretrofit.model.CryptoModel
import kotlinx.android.synthetic.main.row_layout.view.*

class CryptoAdapter(private val cryptoList: ArrayList<CryptoModel>): RecyclerView.Adapter<CryptoAdapter.RowHolder>() {

    private val colors: Array<String> = arrayOf("#eb4034","#c0d6e4","#f3913a","#00ffff","#e5607f")

    class RowHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cryptoModel: CryptoModel, colors: Array<String>,position: Int){
            itemView.setBackgroundColor(Color.parseColor(colors[position % 5]))
            itemView.currency.text=cryptoModel.currency
            itemView.price.text=cryptoModel.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return RowHolder(view)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(cryptoList[position],colors,position)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}