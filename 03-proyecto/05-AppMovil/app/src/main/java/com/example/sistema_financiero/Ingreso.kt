package com.example.sistema_financiero

import android.os.Parcel
import android.os.Parcelable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Ingreso(
    var idIngresos: Int,
    var fechaIngreso: Date,
    var montoIngreso: Double,
    var cuentaOrigen: String,
    var cuentaDestino: String
) : Parcelable {

    companion object {
        private val DATE_FORMAT = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        @JvmField
        val CREATOR: Parcelable.Creator<Ingreso> = object : Parcelable.Creator<Ingreso> {
            override fun createFromParcel(parcel: Parcel): Ingreso {
                return Ingreso(parcel)
            }

            override fun newArray(size: Int): Array<Ingreso?> {
                return arrayOfNulls(size)
            }
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        DATE_FORMAT.parse(parcel.readString() ?: "") ?: Date(),
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idIngresos)
        parcel.writeString(DATE_FORMAT.format(fechaIngreso))
        parcel.writeDouble(montoIngreso)
        parcel.writeString(cuentaOrigen)
        parcel.writeString(cuentaDestino)
    }

    override fun describeContents(): Int {
        return 0
    }
}