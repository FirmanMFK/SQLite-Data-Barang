package id.firman.testlawencon.adapter

import android.view.View
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import id.firman.testlawencon.model.ModelBarang
import kotlinx.android.synthetic.main.activity_daftar_barang.view.*
import kotlinx.android.synthetic.main.row_data_barang.view.*

class ListHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(data: ModelBarang, listener: OnItemClickListener, position: Int) = with(itemView) {
        rowAv.text = data.namaBarang.substring(0,1).capitalize()
        rowNamaBarang.text = data.namaBarang
        rowQtyBarang.text = data.qty.toString()
        rowExpDate.text = data.expDate
        rowHarga.text = data.harga.toString()

        setOnClickListener { listener.onClick(data, position)}
    }
}