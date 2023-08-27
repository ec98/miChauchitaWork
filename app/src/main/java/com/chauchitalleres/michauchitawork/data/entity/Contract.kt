package com.chauchitalleres.michauchita.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contract(
    var id: String,
    var cliente: String,
    var trabajador: String,
    var servicio: String,
    var tarifa: Float,
    var tiempo: Int,
    var latitud: String,
    var longitud: String
) : Parcelable