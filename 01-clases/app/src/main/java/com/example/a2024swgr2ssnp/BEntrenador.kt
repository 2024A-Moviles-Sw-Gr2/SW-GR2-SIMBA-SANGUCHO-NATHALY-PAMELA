package com.example.a2024swgr2ssnp

import android.os.Parcel
import android.os.Parcelable
// esta clase va a deserializar el contenido que se envie serializable
class BEntrenador(
    var id:Int,
    var nombre: String,
    var descripcion: String?
        ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()
    ) {
    }

    override fun toString(): String {
        return "$nombre ${descripcion}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeString(descripcion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BEntrenador> {
        override fun createFromParcel(parcel: Parcel): BEntrenador {
            return BEntrenador(parcel)
        }

        override fun newArray(size: Int): Array<BEntrenador?> {
            return arrayOfNulls(size)
        }
    }

}