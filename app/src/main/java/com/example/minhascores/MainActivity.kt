package com.example.minhascores

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton

const val ADD = 1
const val EDIT = 2
class MainActivity : AppCompatActivity() {
    private lateinit var dao: CorDAO
    private lateinit var lvCores: ListView
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var lista: ListaCores

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.dao = CorDAO(this)

        this.lista = ListaCores()

        this.lvCores = findViewById(R.id.lvMainCores)
        this.fabAdd = findViewById(R.id.fabMainAdd)

        this.lvCores.adapter = CorConfigBaseAdapter(this, lista)

        this.lvCores.setOnItemClickListener(ClickLista())
        this.lvCores.setOnItemLongClickListener(ClickLongLista())

        this.fabAdd.setOnClickListener({ add(it) })
    }

    private fun add(view: View){
        val intent = Intent(this, FormActivity::class.java)
        startActivityForResult(intent, ADD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            1 -> {
                val newColor = data?.getSerializableExtra("novaCor") as Cor?


                if (newColor != null) {
                    this.dao.insert(newColor)
                }
            }
            2 -> {
                val editedColor = data?.getSerializableExtra("editarCor") as Cor?

                if (editedColor != null) {
                    this.dao.update(editedColor)

                }
            }
        }

        this.updateLista()

    }

    private fun updateLista() {
        this.lista.set(this.dao.select())
        (lvCores.adapter as CorConfigBaseAdapter).notifyDataSetChanged()
    }

    inner class ClickLista: AdapterView.OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val cor = this@MainActivity.lista[position]
            val intent = Intent(this@MainActivity, FormActivity::class.java)
            intent.putExtra("COR", cor)
            startActivityForResult(intent, EDIT)
        }
    }

    inner class ClickLongLista: AdapterView.OnItemLongClickListener{
        override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long
        ): Boolean {
            val cor = this@MainActivity.lista[position]
            (this@MainActivity.lvCores.adapter as ArrayAdapter<Cor>).remove(cor)
            this@MainActivity.dao.delete(cor.id)
            this@MainActivity.updateLista()
            return true
        }
    }

}


