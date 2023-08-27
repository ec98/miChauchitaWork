package com.chauchitalleres.michauchitawork.ui.activitiesworker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.chauchitalleres.michauchita.logic.list.ListItems
import com.chauchitalleres.michauchitawork.R
import com.chauchitalleres.michauchitawork.databinding.ActivityWorkerRegisterBinding
import com.chauchitalleres.michauchitawork.ui.adapters.MultiSelectAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class WorkerRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkerRegisterBinding

    private lateinit var database: FirebaseDatabase
    private var reference: DatabaseReference = FirebaseDatabase.getInstance()
        .getReferenceFromUrl("https://mi-chauchita-default-rtdb.firebaseio.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkerRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var recycler = binding.recyclerView
        var options = ListItems().returnServicesList().map { it.nombre }

        val adapter = MultiSelectAdapter(options)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter


        // Obtener el numero de elementos
//        reference.child("workers").addListenerForSingleValueEvent(object :
//            ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                Log.d("COUNT", snapshot.childrenCount.toString())
//            }
//
//            override fun onCancelled(error: DatabaseError) {}
//        })

        binding.btnRegister.setOnClickListener {

            var name: String = binding.name.text.toString()
            var username: String = binding.username.text.toString()
            var email: String = binding.email.text.toString()
            var password: String = binding.password.text.toString()
            var confpassword: String = binding.confpassword.text.toString()

            if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confpassword.isEmpty()) {
                Toast.makeText(
                    baseContext,
                    "Por favor llena todos los campos requeridos.",
                    Toast.LENGTH_SHORT,
                ).show()
            } else {
                if (password.equals(confpassword)) {
                    reference.child("workers").addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.hasChild(username)) {
                                Toast.makeText(
                                    this@WorkerRegisterActivity,
                                    "Usuario ya registrado",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            } else {
                                reference.child("workers").child(username).child("name")
                                    .setValue(name)
                                reference.child("workers").child(username).child("email")
                                    .setValue(email)
                                reference.child("workers").child(username).child("password")
                                    .setValue(password)



                                reference.child("workers").child(username).child("services")
                                    .setValue(adapter.getSelectedOptions())

                                Toast.makeText(
                                    this@WorkerRegisterActivity,
                                    "Te has registrado.",
                                    Toast.LENGTH_SHORT,
                                ).show()

                                val intent =
                                    Intent(this@WorkerRegisterActivity, WorkerActivity::class.java)
                                startActivity(intent)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
                } else {
                    Toast.makeText(
                        baseContext,
                        "La contraseña no coincide.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
//            val helper = HelperClass(name, email, username, password)
//            reference.child(username).setValue(helper)
        }
    }
}