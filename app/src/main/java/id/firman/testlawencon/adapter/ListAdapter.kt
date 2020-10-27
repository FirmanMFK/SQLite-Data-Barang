package id.firman.testlawencon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import id.firman.testlawencon.R
import id.firman.testlawencon.model.ModelBarang

class ListAdapter(data: MutableList<ModelBarang>, listener: OnItemClickListener) : RecyclerView.Adapter<ListHolder>() {

    private val datas = data
    private val listeners = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.row_data_barang, parent, false)
        return ListHolder(view)
    }
    override fun getItemCount(): Int = datas.size
    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bind(datas[position], listeners, position)
    }


}