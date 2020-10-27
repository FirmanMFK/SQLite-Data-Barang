package id.firman.testlawencon.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import id.firman.testlawencon.model.ModelBarang

class DbHelper(ctx: Context) :
    SQLiteOpenHelper(ctx, DbConstant.DATABASE_NAME, null, DbConstant.DATABASE_VERSION) {

    companion object {
        private lateinit var INSTANCE: DbHelper
        private lateinit var database: SQLiteDatabase
        private var databaseOpen: Boolean = false

        fun closeDatabase() {
            if (database.isOpen && databaseOpen) {
                database.close()
                databaseOpen = false
            }
        }


        fun initDatabaseInstance(ctx: Context): DbHelper {
            INSTANCE = DbHelper(ctx)
            return INSTANCE
        }

        fun insertData(modelBarang: ModelBarang): Long {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true
                Log.i("Database", "Database Open")
            }

            val values = ContentValues()
            values.put(DbConstant.ROW_NAMA_BARANG, modelBarang.namaBarang)
            values.put(DbConstant.ROW_QTY, modelBarang.qty)
            values.put(DbConstant.ROW_EXP_DATE, modelBarang.expDate)
            values.put(DbConstant.ROW_HARGA, modelBarang.harga)
            return database.insert(DbConstant.DATABASE_TABLE, null, values)
        }

        fun updateData(modelBarang: ModelBarang): Int {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true
                Log.i("Database", "Database Open")
            }

            val values = ContentValues()
            values.put(DbConstant.ROW_NAMA_BARANG, modelBarang.namaBarang)
            values.put(DbConstant.ROW_QTY, modelBarang.qty)
            values.put(DbConstant.ROW_EXP_DATE, modelBarang.expDate)
            values.put(DbConstant.ROW_HARGA, modelBarang.harga)
            return database.update(
                DbConstant.DATABASE_TABLE,
                values,
                "${DbConstant.ROW_ID} = ${modelBarang.id}",
                null
            )
        }


        fun getAllData(): MutableList<ModelBarang> {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true
                Log.i("Database", "Database Open")
            }

            val data: MutableList<ModelBarang> = ArrayList()
            val cursor = database.rawQuery("SELECT * FROM ${DbConstant.DATABASE_TABLE}", null)
            cursor.use { cur ->
                if (cursor.moveToFirst()) {
                    do {
                        val barang = ModelBarang()
                        barang.id = cur.getInt(cur.getColumnIndex(DbConstant.ROW_ID))
                        barang.namaBarang =
                            cur.getString(cur.getColumnIndex(DbConstant.ROW_NAMA_BARANG))
                        barang.qty = cur.getInt(cur.getColumnIndex(DbConstant.ROW_QTY))
                        barang.expDate = cur.getString(cur.getColumnIndex(DbConstant.ROW_EXP_DATE))
                        barang.harga = cur.getInt(cur.getColumnIndex(DbConstant.ROW_HARGA))
                        data.add(barang)
                    } while (cursor.moveToNext())
                }
            }
            return data
        }

        fun deleteData(id: Int): Int {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true
            }

            return database.delete(DbConstant.DATABASE_TABLE, "${DbConstant.ROW_ID} = $id", null)
        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(DbConstant.QUERY_CREATE)
        Log.i("Database", "Database Created")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(DbConstant.QUERY_UPGRADE)
        Log.i("Database", "Database Updated")
    }

}