package com.chauchitalleres.michauchitawork.logic

import com.chauchitalleres.michauchitawork.data.LoginUser

class LoginValidator {

    fun checkLogin(name: String, password: String): Boolean {
        val admin = LoginUser()
        return (admin.name == name && admin.password == password)
    }

}