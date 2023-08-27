package com.chauchitalleres.michauchitawork.ui.activitiesworker

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chauchitalleres.michauchitawork.R
import com.chauchitalleres.michauchitawork.databinding.ActivityWorkerMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class WorkerMaps : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener,
    GoogleMap.OnMapLongClickListener {

    private lateinit var binding: ActivityWorkerMapsBinding
    private lateinit var mMap: GoogleMap

    private var indice: String = ""
    private var latitud: String = ""
    private var longitud: String = ""

    private var reference: DatabaseReference = FirebaseDatabase.getInstance()
        .getReferenceFromUrl("https://mi-chauchita-default-rtdb.firebaseio.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkerMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        user = intent.getStringExtra("user_email")!!
        val worker = intent.getStringExtra("worker").toString()
        latitud = intent.getStringExtra("latitude").toString()
        longitud = intent.getStringExtra("longitude").toString()
        indice = intent.getStringExtra("id_contract").toString()

        reference.child("workers").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val getName: String? =
                    snapshot.child(worker).child("name").getValue(String::class.java)

                binding.txtTrabajador.text = "Bienvenido\n${getName}"
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        binding.btnAceptar.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Tu Chauchita Confirmada")
            builder.setMessage("Ponte en contacto con el cliente.")
            builder.setPositiveButton("Aceptar") { dialog, which ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        binding.btnCancelar.setOnClickListener {
            reference.child("contracts").child(indice).removeValue()

            val i = Intent(this, EntryActivity::class.java)
            i.putExtra("worker", worker)
            startActivity(i)
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        mMap.setOnMapClickListener(this)
        mMap.setOnMapLongClickListener(this)

        val ubicacionContrato = LatLng(latitud.toDouble(), longitud.toDouble())

        mMap.addMarker(
            MarkerOptions().position(ubicacionContrato).title("Ubicacion del contrato")
        )
        //agrega un marcador a esa posicion y se actualice el mapa a esa posicion
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacionContrato))
    }

    override fun onMapClick(p0: LatLng) {
        mMap.clear()
        //se va actualizando cada click
        val posiciones = LatLng(p0.latitude, p0.longitude)
        mMap.addMarker(MarkerOptions().position(posiciones))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(posiciones))
    }

    override fun onMapLongClick(p0: LatLng) {}

}