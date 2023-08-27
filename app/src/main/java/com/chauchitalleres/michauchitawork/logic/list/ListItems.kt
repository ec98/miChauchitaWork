package com.chauchitalleres.michauchita.logic.list

import com.chauchitalleres.michauchita.data.entity.Servicio
import com.chauchitalleres.michauchita.data.entity.Trabajador

class ListItems {

    fun returnServicesList(): List<Servicio> {
        val items = listOf(
            Servicio(
                "Limpieza del hogar",
                "https://www.65ymas.com/uploads/s1/36/16/74/bigstock-wife-housekeeping-and-cleaning-357648977.jpeg",
                5.2f
            ),
            Servicio(
                "Cerrajería o plomería",
                "https://muchosnegociosrentables.com/wp-content/uploads/2021/07/negocio-cerrajeria.jpg",
                3.50f
            ),
            Servicio(
                "Cuidado de niños o adultos mayores",
                "https://www.enciclopedia-infantes.com/sites/default/files/child-care_360.jpg",
                4.50f
            ),
            Servicio(
                "Jardinería",
                "https://www.decoramosjardines.com/wp-content/uploads/2022/03/jardinero-a-domicilio-quito.jpg",
                3.50f
            ),
            Servicio(
                "Paseo de animales de compañía",
                "https://www.escueladesarts.com/wp-content/uploads/guarderia-perros.jpg",
                5.50f
            )
        )
        return items
    }
    fun returnTrabajadoresList(servicio: String): List<Trabajador> {
        val items = listOf(
            Trabajador(
                "Juan Martínez",
                servicio,
                "https://img.freepik.com/vector-gratis/ilustracion-arte-dibujos-animados-dibujados-mano-dibujos-animados-personaje-ingeniero-maquinas-seguridad-trabajador-profesional_56104-1052.jpg",
                5.0f
            ),
            Trabajador(
                "María García",
                servicio,
                "https://img.freepik.com/vector-gratis/ilustracion-mujeres-iranies-dibujadas-mano_23-2149856014.jpg",
                4.88f
            ),
            Trabajador(
                "José González",
                servicio,
                "https://img.freepik.com/vector-gratis/carpintero-madera-martillo_1308-73442.jpg",
                5.0f
            ),
            Trabajador(
                "Luis Silva",
                servicio,
                "https://img.freepik.com/vector-gratis/hombre-muestra-gesto-gran-idea_10045-637.jpg",
                4.5f
            ),
            Trabajador(
                "Daniela Sánchez",
                servicio,
                "https://img.freepik.com/vector-gratis/ilustracion-arte-dibujos-animados-dibujados-mano-hermosa-mujer-joven_56104-1088.jpg",
                5.0f
            ),

        )
        return items
    }
}