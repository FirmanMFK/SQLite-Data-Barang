package id.firman.testlawencon.db

class DbConstant {

    companion object {
        val DATABASE_NAME = "DB_BARANG"
        val DATABASE_VERSION = 1
        val DATABASE_TABLE = "tbl_barang"
        val ROW_ID = "_id"
        val ROW_NAMA_BARANG= "nama_barang"
        val ROW_QTY= "qty"
        val ROW_EXP_DATE = "exp_date"
        val ROW_HARGA = "harga"

        val QUERY_CREATE = "CREATE TABLE IF NOT EXISTS $DATABASE_TABLE ($ROW_ID INTEGER PRIMARY KEY AUTOINCREMENT, $ROW_NAMA_BARANG TEXT , $ROW_QTY TEXT , $ROW_EXP_DATE TEXT, $ROW_HARGA TEXT)"
        val QUERY_UPGRADE = "DROP TABLE IF EXISTS $DATABASE_TABLE"

    }
}