package com.example.minhascores

class ListaCores {

    private lateinit var corLista: ArrayList<Cor>

    init {
        this.corLista = arrayListOf();
    }
    fun add(corConfaig: Cor) {
        this.corLista.add(corConfaig)
    }
    fun remove(corConfaig: Cor) {
        this.corLista.remove(corConfaig)
    }
    fun replace(corConfaig: Cor) {
        val encontraCor = this.corLista.find { it.id == corConfaig.id  }
        val index = this.corLista.indexOf(encontraCor)
        this.corLista.set(index, corConfaig)
    }
    operator fun get(index: Int): Cor {
        return this.corLista[index]
    }
    fun get(): ArrayList<Cor> {
        return this.corLista
    }
    fun set(cors: ArrayList<Cor>) {
        this.corLista = cors
    }
    fun count(): Int {
        return this.corLista.count()
    }
}