package com.chauchitalleres.michauchitawork.ui.adapters

import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chauchitalleres.michauchita.data.entity.Contract
import com.chauchitalleres.michauchitawork.R
import com.chauchitalleres.michauchitawork.databinding.ContratoBinding
import java.io.IOException
import java.util.Locale


class ContractAdapter(
    var items: List<Contract>, private var fnClick: (Contract) -> Unit

) : RecyclerView.Adapter<ContractAdapter.ContractViewHolder>() {

    inner class ContractViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ContratoBinding = ContratoBinding.bind(view)

        fun render(item: Contract, fnClick: (Contract) -> Unit) {
            binding.service.text = item.servicio

            if (item.tiempo == 1) {
                binding.time.text = item.tiempo.toString() + " hora"
            } else {
                binding.time.text = item.tiempo.toString() + " horas"
            }

            val address = getAddressFromLocation(item.latitud.toDouble(), item.longitud.toDouble())
            binding.map.text = address

            itemView.setOnClickListener {
                fnClick(item)
            }
        }

        private fun getAddressFromLocation(latitud: Double, longitud: Double): CharSequence? {
            val geocoder = Geocoder(itemView.context, Locale.getDefault())
            var addressText = ""

            try {
                val addresses: MutableList<android.location.Address>? =
                    geocoder.getFromLocation(latitud, longitud, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address: android.location.Address = addresses[0]
                    addressText = address.getAddressLine(0) ?: "Dirección desconocida"
                } else {
                    addressText = "Dirección no disponible"
                }
            } catch (e: IOException) {
                addressText = "Error al obtener la dirección"
            }

            return addressText
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContractViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return ContractViewHolder(
            inflater.inflate(
                R.layout.contrato,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ContractViewHolder, position: Int) {
        holder.render(items[position], fnClick)
    }

    override fun getItemCount(): Int = items.size

    fun replaceListAdapter(newItems: List<Contract>) {
        this.items = newItems
        notifyDataSetChanged()
    }

}