package com.example.minhascores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CorConfigBaseAdapter(private val context: Context, private val listaCores: ListaCores) :  BaseAdapter() {

    private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
            return listaCores.count()
    }

    override fun getItem(position: Int): Any {
            return listaCores.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val cor = this.getItem(position) as Cor;
            val row = convertView ?: inflater.inflate(R.layout.palette_image_colors, null)
            val titleTextView = row.findViewById(R.id.color_list_title) as TextView
            val subtitleTextView = row.findViewById(R.id.color_list_subtitle) as TextView
            val thumbnailImageView = row.findViewById(R.id.color_thumbnail) as ImageView
            titleTextView.text = cor.nome;
            subtitleTextView.text = cor.toHex();
            thumbnailImageView.setColorFilter(cor.codigo)

            return row
    }
}