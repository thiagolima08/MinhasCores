package com.example.minhascores

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class FormActivity: AppCompatActivity() {
    private lateinit var etNomeCor: TextView
    private lateinit var barRed: SeekBar
    private lateinit var barGreen: SeekBar
    private lateinit var barBlue: SeekBar
    private lateinit var mostrarCorButton: Button
    private lateinit var btnSalvar: Button
    private lateinit var btnCancelar: Button
    private lateinit var cor: Cor
    private lateinit var modo: String
    private var corHexFormato = "#000000";


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_main)

        this.etNomeCor = findViewById(R.id.etNomeCor)
        this.barRed = findViewById(R.id.seekVermelho)
        this.barGreen = findViewById(R.id.seekVerde)
        this.barBlue = findViewById(R.id.seekAzul)
        this.mostrarCorButton = findViewById(R.id.mostrarCor)
        this.btnSalvar = findViewById(R.id.btnSalvar)
        this.btnCancelar = findViewById(R.id.btnCancelar)

        if (intent.hasExtra("editarCor")) {
            this.modo = "EDITAR"
            this.cor = intent.getSerializableExtra("editarCor") as Cor
        } else {
            this.modo = "NOVA"
            this.cor = Cor("", 0);
        }

        btnSalvar.setOnClickListener({ salvarCor(it) })
        btnCancelar.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        mostrarCorButton.text = corHexFormato
        mostrarCorButton.setOnClickListener { onColorDisplayClicked(it) }

        fillForm(cor)
    }

    private fun fillForm(cor: Cor) {
        etNomeCor.text = cor.nome
        barRed.progress = Color.red(cor.codigo)
        barGreen.progress = Color.green(cor.codigo)
        barBlue.progress = Color.blue(cor.codigo)
    }

    private fun onColorDisplayClicked(it: View?) {
        val clipBoardService = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Hex Color", corHexFormato)
        clipBoardService.setPrimaryClip(clip)

        Toast.makeText(this, "Cor copiada para área de transferência", Toast.LENGTH_SHORT).show()
    }

    private fun salvarCor(it: View?) {
        this.cor.nome = etNomeCor.text.toString().trim()
        this.cor.codigo = Color.rgb(barRed.progress, barGreen.progress, barBlue.progress)
        val returnIntent = Intent()

        if (modo == "NOVA") {
            returnIntent.putExtra("novaCor", this.cor)
            setResult(1, returnIntent)
            finish()
        }

        if (modo == "EDITAR") {
            returnIntent.putExtra("editarCor", this.cor)
            setResult(2, returnIntent)
            finish()
        }

    }

    fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        mostrarCorButton.setBackgroundColor(
                Color.rgb(
                        barRed.progress,
                        barGreen.progress,
                        barBlue.progress
                )
        )
        mostrarCorButton.text = String.format(
                "#%06X",
                (0xFFFFFF and Color.rgb(barRed.progress, barGreen.progress, barBlue.progress))
        )
    }

}
