package id.firman.testlawencon

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import id.firman.testlawencon.db.DbHelper
import id.firman.testlawencon.model.ModelBarang
import kotlinx.android.synthetic.main.activity_update_barang.*

class UpdateDataBarangActivity : AppCompatActivity() {

    var dataBarang = ModelBarang()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_barang)

        bindView()

        etNamaBarangEdit.addTextChangedListener(Watcher(inNamaBarangEdit))
        etQuantityEdit.addTextChangedListener(Watcher(inQtyEdit))
        etExpDateEdit.addTextChangedListener(Watcher(inExpDateEdit))
        etHargaEdit.addTextChangedListener(Watcher(inHargaEdit))

        btnEdit.setOnClickListener {

            val namaBarang = etNamaBarangEdit.text.toString()
            val qty = etQuantityEdit.text.toString()
            val expdate = etExpDateEdit.text.toString()
            val harga = etHargaEdit.text.toString()


            if (namaBarang.isEmpty()) {
                inNamaBarangEdit.error = "Masukan nama barang"
                return@setOnClickListener
            }

            if (qty.isEmpty()) {
                inQtyEdit.error = "Masukan qty "
                return@setOnClickListener
            }

            if (expdate.isEmpty()) {
                inExpDateEdit.error = "Masukan expiry date"
                return@setOnClickListener
            }

            if (harga.isEmpty()) {
                inExpDateEdit.error = "Masukan harga"
                return@setOnClickListener
            }


            dataBarang.namaBarang = namaBarang
            dataBarang.qty = qty.toInt()
            dataBarang.expDate = expdate
            dataBarang.harga = harga.toInt()

            val stat = DbHelper.updateData(dataBarang)

            if (stat > 0) {
                val bind = Bundle()
                bind.putParcelable("DATA", dataBarang)

                val intent = Intent()
                intent.putExtras(bind)

                setResult(Activity.RESULT_OK, intent)

                Toast.makeText(this, "Berhasil Mengupdate Data", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Gagal Mengudate Data", Toast.LENGTH_SHORT).show()
            }
        }
        toolbarEdit.title = "Update Data Barang"
    }

    private fun bindView() {
        val bind = intent.extras
        dataBarang = bind?.getParcelable("DATA")!!

        etNamaBarangEdit.setText(dataBarang.namaBarang)
        etQuantityEdit.setText(dataBarang.qty.toString())
        etExpDateEdit.setText(dataBarang.expDate)
        etHargaEdit.setText(dataBarang.harga.toString())


    }


    private class Watcher(textinput: TextInputLayout) : TextWatcher {

        val input = textinput

        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            input.isErrorEnabled = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        DbHelper.closeDatabase()
    }
}
