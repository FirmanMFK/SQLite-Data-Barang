package id.firman.testlawencon

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import id.firman.testlawencon.db.DbHelper
import id.firman.testlawencon.model.ModelBarang
import kotlinx.android.synthetic.main.activity_tambah_barang.*


class TambahDataBarangActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_barang)

        toolbar.title = "SQLite Kotlin"

        etNamaBarang.addTextChangedListener(Watcher(inNamaBarang))
        etQty.addTextChangedListener(Watcher(inQty))


        btnInsert.setOnClickListener {

            val namaBarang = etNamaBarang.text.toString()
            val qty = etQty.text.toString()
            val expDate = etExpDate.text.toString()
            val harga = etHarga.text.toString()


            if (namaBarang.isEmpty()) {
                inNamaBarang.error = "Masukan nama barang"
                return@setOnClickListener
            }

            if (qty.isEmpty()) {
                inQty.error = "Masukan qty"
                return@setOnClickListener
            }

            if (expDate.isEmpty()) {
                inExpDate.error = "Masukan Exp Date"
                return@setOnClickListener
            }

            if (harga.isEmpty()) {
                inHarga.error = "Masukan Harga"
                return@setOnClickListener
            }

            val data = ModelBarang()
            data.namaBarang = namaBarang
            data.qty = qty.toInt()
            data.expDate = expDate
            data.harga = harga.toInt()

            val stat = DbHelper.insertData(data)

            if (stat > 0) {
                etNamaBarang.text.clear()
                etQty.text.clear()
                etExpDate.text.clear()
                etHarga.text.clear()

                etNamaBarang.clearFocus()
                etQty.clearFocus()
                etExpDate.clearFocus()
                etHarga.clearFocus()

                Toast.makeText(this, "Berhasil Menambah Data", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Gagal Menambah Data", Toast.LENGTH_SHORT).show()
            }

        }
        btnLihatData.setOnClickListener {
            startActivity(Intent(this, DaftarBarangActivity::class.java))
        }

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
