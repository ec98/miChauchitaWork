package com.chauchitalleres.michauchita.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Trabajador (
var nombre: String,
var servicio: String,
var imagen: String,
var puntuacion: Float
) : Parcelable

