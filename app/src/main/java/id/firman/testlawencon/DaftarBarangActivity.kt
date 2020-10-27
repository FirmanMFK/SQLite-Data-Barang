package id.firman.testlawencon

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.firman.testlawencon.adapter.ListAdapter
import id.firman.testlawencon.adapter.OnItemClickListener
import id.firman.testlawencon.db.DbHelper
import id.firman.testlawencon.model.ModelBarang
import kotlinx.android.synthetic.main.activity_daftar_barang.*
import kotlinx.android.synthetic.main.detail_barang.*

class DaftarBarangActivity  : AppCompatActivity(), OnItemClickListener, DetailBarangDialog.OnDialogItemClick {

    private var dataDaftarBarang: MutableList<ModelBarang> = ArrayList()
    private var positionStats = 0
    lateinit private var adapterDaftarBarang: ListAdapter

    override fun dialogDeleteCallback(data: ModelBarang) {
        this.dataDaftarBarang.remove(data)
        adapterDaftarBarang.notifyDataSetChanged()

        if (this.dataDaftarBarang.size > 0) {
            textEmpty.visibility = View.GONE
        } else {
            textEmpty.visibility = View.VISIBLE
        }

    }

    override fun dialogEditCallback(data: ModelBarang) {

        val bind = Bundle()
        bind.putParcelable("DATA", data)

        val edit = Intent(this, UpdateDataBarangActivity::class.java)
        edit.putExtras(bind)
        startActivityForResult(edit, 1)
    }

    override fun onClick(data: ModelBarang, position: Int) {
        DetailBarangDialog.newInstance(data, this).show(supportFragmentManager, "DETAIL")
        positionStats = position
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_barang)

        dataDaftarBarang = DbHelper.getAllData()

        adapterDaftarBarang = ListAdapter(dataDaftarBarang, this)

        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapterDaftarBarang

        toolbars.title = "Daftar Barang"

        if (dataDaftarBarang.size > 0) {
            textEmpty.visibility = View.GONE
        } else {
            textEmpty.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            if (data != null) {
                val dataBarang: ModelBarang? = data.extras!!.getParcelable("DATA")
                dataDaftarBarang[positionStats] = dataBarang!!
                adapterDaftarBarang.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        DbHelper.closeDatabase()
    }

}