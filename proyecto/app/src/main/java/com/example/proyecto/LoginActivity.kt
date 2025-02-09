package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var btnIngresar: Button
    private lateinit var tvCrearCuenta: TextView
    private lateinit var etUsuario: EditText
    private lateinit var etContraseña: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Inicializar FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Obtener las vistas
        btnIngresar = findViewById(R.id.btnIngresar)
        tvCrearCuenta = findViewById(R.id.tvCrearCuenta)
        etUsuario = findViewById(R.id.etUsuario) // Asegúrate de asignar los ids correctos
        etContraseña = findViewById(R.id.etContraseña)

        // Configurar el listener para el botón de ingreso
        btnIngresar.setOnClickListener {
            val email = etUsuario.text.toString().trim()
            val password = etContraseña.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signInUser(email, password)
            } else {
                Toast.makeText(this, "Por favor ingresa tus credenciales", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar el listener para crear cuenta
        tvCrearCuenta.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Si el inicio de sesión es exitoso, redirigir a OpcionesDeTrabajoActivity
                    val user = auth.currentUser
                    startActivity(Intent(this, OpcionesDeTrabajoActivity::class.java))
                    finish() // Finalizar la actividad de login
                } else {
                    // Si el inicio de sesión falla, mostrar un mensaje de error
                    Toast.makeText(this, "Error al iniciar sesión: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
