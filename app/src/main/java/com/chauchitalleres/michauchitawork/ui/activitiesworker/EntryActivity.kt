package com.chauchitalleres.michauchitawork.ui.activitiesworker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chauchitalleres.michauchita.data.entity.Contract
import com.chauchitalleres.michauchitawork.databinding.ActivityEntryBinding
import com.chauchitalleres.michauchitawork.ui.adapters.ContractAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEntryBinding
    private lateinit var worker: String

    private lateinit var reviAdapter: ContractAdapter
    private lateinit var lManager: LinearLayoutManager

    private var reference: DatabaseReference = FirebaseDatabase.getInstance()
        .getReferenceFromUrl("https://mi-chauchita-default-rtdb.firebaseio.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    override fun onStart() {
        super.onStart()

        worker = intent.getStringExtra("worker")!!

        reference.child("workers").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val getName: String? =
                    snapshot.child(worker).child("name").getValue(String::class.java)

                binding.nameWorker.text = "Bienvenido\n${getName}"
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        reference.child("contracts").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val getContracts = snapshot.children

                var contratos = mutableListOf<Contract>()

                getContracts.forEach {
                    val trabajador = it.child("trabajador").getValue(String::class.java)
                    val confirmacion = it.child("confirmacion").getValue(Boolean::class.java)

                    if (trabajador != null && trabajador == worker && confirmacion == true) {
                        it.child("tarifa").getValue(Float::class.java)?.let { it1 ->
                            it.child("tiempo").getValue(Int::class.java)?.let { it2 ->
                                contratos.add(
                                    Contract(
                                        it.key!!,
                                        it.child("usuario").getValue(String::class.java)!!,
                                        trabajador,
                                        it.child("servicio").getValue(String::class.java)!!,
                                        it1,
                                        it2,
                                        it.child("latitud").getValue(String::class.java)!!,
                                        it.child("longitud").getValue(String::class.java)!!
                                    )
                                )
                            }
                        }
                    }
                }

                if (contratos.isEmpty()) {
                    binding.textView3.text = "No hay contratos disponibles"
                }
                reviAdapter = ContractAdapter(
                    contratos
                ) {
                    sendContractItem(it)
                }

                binding.recyclerView.apply {
                    this.adapter = reviAdapter
                    this.layoutManager = lManager
                }

            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun sendContractItem(item: Contract) {
        val intent = Intent(this, WorkerMaps::class.java)
        intent.putExtra("id_contract", item.id)
        intent.putExtra("worker", item.trabajador)
        intent.putExtra("latitude", item.latitud)
        intent.putExtra("longitude", item.longitud)
        startActivity(intent)
    }
}