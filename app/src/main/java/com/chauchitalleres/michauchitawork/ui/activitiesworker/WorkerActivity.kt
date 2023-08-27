package com.chauchitalleres.michauchitawork.ui.activitiesworker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.chauchitalleres.michauchitawork.R
import com.chauchitalleres.michauchitawork.databinding.ActivityWorkerBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class WorkerActivity : AppCompatActivity() {
    private var reference: DatabaseReference = FirebaseDatabase.getInstance()
        .getReferenceFromUrl("https://mi-chauchita-default-rtdb.firebaseio.com/")
    private lateinit var binding: ActivityWorkerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWorkerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            var user: String = binding.txtUser.text.toString()
            var password: String = binding.txtPassword.text.toString()

            if (user.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this@WorkerActivity,
                    "Credenciales incorrectas",
                    Toast.LENGTH_SHORT,
                ).show()
            } else {
                reference.child("workers").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.hasChild(user)) {

                            val getPassword: String? =
                                snapshot.child(user).child("password").getValue(String::class.java)

                            if (getPassword.equals(password)) {
                                Toast.makeText(
                                    this@WorkerActivity,
                                    "Bienvenido",
                                    Toast.LENGTH_SHORT,
                                ).show()
                                val intento =
                                    Intent(this@WorkerActivity, EntryActivity::class.java)
                                intento.putExtra("worker", binding.txtUser.text.toString())
                                startActivity(intento)
                            } else {
                                Toast.makeText(
                                    this@WorkerActivity,
                                    "Password incorrecto",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }

                        } else {
                            Toast.makeText(
                                this@WorkerActivity,
                                "Credenciales incorrectas",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initMain()
    }

    private fun initMain() {

        binding.btnRegistrarCuenta.setOnClickListener {
            val intent = Intent(this, WorkerRegisterActivity::class.java)
            startActivity(intent)
        }
        binding.btnRecuperarContra.setOnClickListener {
            //code...
        }
    }
}