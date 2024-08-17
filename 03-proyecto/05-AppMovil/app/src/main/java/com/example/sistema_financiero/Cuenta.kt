package com.example.sistema_financiero

import android.os.Parcel
import android.os.Parcelable

class Cuenta(
    var id: Int,
    var nombreCuenta: String,
    var montoInicial: Double,
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readDouble()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombreCuenta)
        parcel.writeDouble(montoInicial)
    }

    companion object CREATOR : Parcelable.Creator<Cuenta> {
        override fun createFromParcel(parcel: Parcel): Cuenta {
            return Cuenta(parcel)
        }

        override fun newArray(size: Int): Array<Cuenta?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString():String {
        return "$id - $nombreCuenta"
    }

}