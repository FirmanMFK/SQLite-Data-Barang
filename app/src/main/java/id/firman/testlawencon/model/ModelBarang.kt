package id.firman.testlawencon.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize

data class ModelBarang (var id: Int, var namaBarang: String, var qty: Int, var expDate: String, var harga: Int): Parcelable {
    constructor() : this (0,"",0,"",0)
}
