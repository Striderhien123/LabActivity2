package com.example.loginapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var welcomeMessage: TextView
    private lateinit var errorMessage: TextView
    private lateinit var loginFormLayout: LinearLayout
    private lateinit var loginTitleText: TextView
    private lateinit var loginSubtitleText: TextView // Declaring the subtitle TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        usernameEditText = findViewById(R.id.username_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        loginButton = findViewById(R.id.login_button)
        welcomeMessage = findViewById(R.id.welcome_message)
        errorMessage = findViewById(R.id.error_message)
        loginFormLayout = findViewById(R.id.login_form_layout)
        loginTitleText = findViewById(R.id.title_text_view)
        loginSubtitleText = findViewById(R.id.prompt_text_view) // You must use the actual ID here

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            loginViewModel.login(username, password)
        }

        loginViewModel.loginResult.observe(this) { result ->
            when (result) {
                is LoginViewModel.LoginResult.Success -> {
                    loginFormLayout.visibility = View.GONE
                    welcomeMessage.visibility = View.VISIBLE
                    welcomeMessage.text = "Welcome, ${usernameEditText.text}!"
                    errorMessage.visibility = View.GONE
                    loginTitleText.visibility = View.GONE
                    loginSubtitleText.visibility = View.GONE // Hiding the subtitle TextView
                }
                is LoginViewModel.LoginResult.Error -> {
                    errorMessage.text = result.message
                    errorMessage.visibility = View.VISIBLE
                    loginFormLayout.visibility = View.VISIBLE
                    welcomeMessage.visibility = View.GONE
                    passwordEditText.text.clear()
                }
                is LoginViewModel.LoginResult.Idle -> {
                    loginFormLayout.visibility = View.VISIBLE
                    welcomeMessage.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                    loginTitleText.visibility = View.VISIBLE
                    loginSubtitleText.visibility = View.VISIBLE
                }
            }
        }
    }
}