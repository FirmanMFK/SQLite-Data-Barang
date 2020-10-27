package id.firman.testlawencon

import android.app.Application
import id.firman.testlawencon.db.DbHelper

class MyApplication: Application(){

    override fun onCreate() {
        super.onCreate()
        DbHelper.initDatabaseInstance(this)
    }

}