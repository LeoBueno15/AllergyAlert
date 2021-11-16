package com.example.allergyalert

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AccountRegister : AppCompatActivity() {
    lateinit var registerButton: Button
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var loginText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_register)

        emailEditText = findViewById(R.id.enter_email)
        passwordEditText = findViewById(R.id.enter_password)
        loginText = findViewById(R.id.login)
        registerButton = findViewById(R.id.register_button)

        loginText.setOnClickListener {
            startActivity(Intent(this, AccountSignIn::class.java))
        }

        registerButton.setOnClickListener {
            when {
                TextUtils.isEmpty(emailEditText.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(passwordEditText.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val email = emailEditText.text.toString().trim { it <= ' '}
                    val password = passwordEditText.text.toString().trim { it <= ' '}

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->
                                if (task.isSuccessful) {
                                    val firebaseUser: FirebaseUser? = task.result?.user
                                    Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show()

                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    if (firebaseUser != null) {
                                        intent.putExtra("user_id", firebaseUser.uid)
                                    }
                                    intent.putExtra("user_email", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                }
            }
        }
    }
}