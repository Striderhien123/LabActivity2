package com.example.loginapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

// This is the main Activity for our app.
// It will handle the UI and observe the state from the ViewModel.
class MainActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var welcomeMessage: TextView
    private lateinit var errorMessage: TextView
    private lateinit var loginFormLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the ViewModel.
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // Get references to all the UI elements.
        usernameEditText = findViewById(R.id.username_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        loginButton = findViewById(R.id.login_button)
        welcomeMessage = findViewById(R.id.welcome_message)
        errorMessage = findViewById(R.id.error_message)
        loginFormLayout = findViewById(R.id.login_form_layout)

        // Set an OnClickListener for the LOGIN button.
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            loginViewModel.login(username, password)
        }

        // Observe the loginResult LiveData from the ViewModel.
        // This will automatically update the UI whenever the state changes.
        loginViewModel.loginResult.observe(this) { result ->
            when (result) {
                is LoginViewModel.LoginResult.Success -> {
                    // On success, hide the form and show the welcome message.
                    loginFormLayout.visibility = View.GONE
                    welcomeMessage.visibility = View.VISIBLE
                    welcomeMessage.text = "Welcome, ${usernameEditText.text}!"
                    errorMessage.visibility = View.GONE
                }
                is LoginViewModel.LoginResult.Error -> {
                    // On error, display the error message and show the form.
                    errorMessage.text = result.message
                    errorMessage.visibility = View.VISIBLE
                    loginFormLayout.visibility = View.VISIBLE
                    welcomeMessage.visibility = View.GONE

                    // Clear the password field for security.
                    passwordEditText.text.clear()
                }
                is LoginViewModel.LoginResult.Idle -> {
                    // Initial state, ensure everything is in the correct initial visibility.
                    loginFormLayout.visibility = View.VISIBLE
                    welcomeMessage.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                }
            }
        }
    }
}
