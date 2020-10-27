package id.firman.testlawencon.adapter

import id.firman.testlawencon.model.ModelBarang

interface OnItemClickListener {

    fun onClick(data: ModelBarang, position: Int)
}