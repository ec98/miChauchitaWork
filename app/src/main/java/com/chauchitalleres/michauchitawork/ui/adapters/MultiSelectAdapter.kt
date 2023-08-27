package com.chauchitalleres.michauchitawork.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chauchitalleres.michauchitawork.R
import com.chauchitalleres.michauchitawork.databinding.ServiciosWorkerBinding

class MultiSelectAdapter(private var options: List<String>) :
    RecyclerView.Adapter<MultiSelectAdapter.MultiSelectViewHolder>() {

    class MultiSelectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ServiciosWorkerBinding = ServiciosWorkerBinding.bind(itemView)

        val optionTextView: TextView = binding.textOption
        val checkBox: CheckBox = binding.checkBox
    }

    private val selectedOptions = BooleanArray(options.size)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiSelectViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.servicios_worker, parent, false)
        return MultiSelectViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MultiSelectViewHolder, position: Int) {
        val option = options[position]
        holder.optionTextView.text = option
        holder.checkBox.isChecked = selectedOptions[position]

        holder.itemView.setOnClickListener {
            selectedOptions[position] = !selectedOptions[position]
            holder.checkBox.isChecked = selectedOptions[position]
        }

        holder.checkBox.setOnClickListener {
            selectedOptions[position] = holder.checkBox.isChecked
        }
    }

    override fun getItemCount(): Int = options.size

    fun getSelectedOptions(): List<String> {
        val selected = mutableListOf<String>()
        for (i in options.indices) {
            if (selectedOptions[i]) {
                selected.add(options[i])
            }
        }
        return selected
    }

}
