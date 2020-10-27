package id.firman.testlawencon

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.firman.testlawencon.db.DbHelper
import id.firman.testlawencon.model.ModelBarang
import kotlinx.android.synthetic.main.detail_barang.*

class DetailBarangDialog : BottomSheetDialogFragment() {
    private var dataBarang = ModelBarang()

    companion object {
        lateinit private var listeners: OnDialogItemClick

        fun newInstance(data: ModelBarang, listener: OnDialogItemClick): DetailBarangDialog {

            listeners = listener
            val detail = DetailBarangDialog()

            val bind = Bundle()
            bind.putParcelable("DATA", data)

            detail.arguments = bind
            return detail

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments

        if (args != null)
            dataBarang = args.getParcelable("DATA")!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.detail_barang, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogNamaBarang.text = dataBarang.namaBarang.toUpperCase()
        dialogHarga.text = dataBarang.harga.toString()
        dialogQuantity.text = dataBarang.qty.toString()
        dialogQuantity.text = dataBarang.expDate

        toolbarDialog.inflateMenu(R.menu.dialog_menu)
        toolbarDialog.setOnMenuItemClickListener {

            when (it.itemId) {

                R.id.dialogEdit -> {
                    listeners.dialogEditCallback(dataBarang)
                    dialog?.dismiss()
                }
                R.id.dialogHapus -> {
                    val build = context?.let { it1 -> AlertDialog.Builder(it1) }
                    build?.setTitle("Hapus Data")
                    build?.setMessage("Apakah Kamu Ingin Menghapus Data ${dataBarang.namaBarang.toUpperCase()}")
                    build?.setPositiveButton("HAPUS", { _, _ ->

                        val stas = DbHelper.deleteData(dataBarang.id)

                        if (stas != 0) {
                            dialog?.dismiss()
                            listeners.dialogDeleteCallback(dataBarang)

                            Toast.makeText(activity, "Berhasil Menghapus Data", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(activity, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show()
                        }

                    })
                    build?.setNegativeButton("BATAL", null)
                    build?.create()?.show()
                }
            }
            true
        }
    }

    interface OnDialogItemClick {
        fun dialogEditCallback(data: ModelBarang)
        fun dialogDeleteCallback(data: ModelBarang)
    }

    override fun onDestroy() {
        super.onDestroy()
        DbHelper.closeDatabase()
    }
}
