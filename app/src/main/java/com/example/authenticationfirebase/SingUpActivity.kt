package com.example.authenticationfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.authenticationfirebase.databinding.ActivitySingUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.util.regex.Pattern

class SingUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySingUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = Firebase.auth

        binding.btnSignup.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            if (checkAllField()) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                    // se a conta for criada com sucesso
                    // também está conectado
                    if (it.isSuccessful) {
                        auth.signOut()
                        Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_LONG)
                            .show()
                    }
                    else {
                        //account note created
                        Log.e("error: ", it.exception.toString())
                    }
                }
            }
        }
    }


    private fun checkAllField(): Boolean {
        val email = binding.edtEmail.text.toString()
        if (binding.edtEmail.text.toString() == "") {
            binding.textInputLayoutEmail.error = "Este é um campo obrigatório"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.textInputLayoutEmail.error = "Verifique o formato do e-mail"
            return false
        }

        //observe também que a senha deve ter pelo menos 6 caracteres
        if (binding.edtPassword.text.toString() == "") {
            binding.textInputLayoutPassword.error = "Este é um campo obrigatório"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }

        if (binding.edtPassword.length()<= 6){
            binding.textInputLayoutPassword.error = "A senha deve ter pelo menos 8 caracteres"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }
        if (binding.edtConfirmPassword.text.toString() == "") {
            binding.textInputLayoutConfirmPassword.error = "Este é um campo obrigatório"
            binding.textInputLayoutConfirmPassword.errorIconDrawable = null
            return false
        }
        if (binding.edtPassword.text.toString() != binding.edtConfirmPassword.text.toString()) {
            binding.textInputLayoutPassword.error = "Senha não corresponde"
            return false
        }
        return true

    }
}