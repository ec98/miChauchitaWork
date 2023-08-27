package com.chauchitalleres.michauchita.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Servicio(
    var nombre: String,
    var imagen: String,
    var tarifa: Float
) : Parcelable
