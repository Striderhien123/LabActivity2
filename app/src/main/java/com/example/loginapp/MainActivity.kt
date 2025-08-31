package com.example.loginapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var logoutButton: Button // Declare the logout button
    private lateinit var welcomeMessage: TextView
    private lateinit var errorMessage: TextView
    private lateinit var loginFormLayout: LinearLayout
    private lateinit var loginTitleText: TextView
    private lateinit var loginSubtitleText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        // Initialize views. Note the change to TextInputEditText for type safety.
        usernameEditText = findViewById(R.id.username_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        loginButton = findViewById(R.id.login_button)
        logoutButton = findViewById(R.id.logout_button) // Initialize the logout button
        welcomeMessage = findViewById(R.id.welcome_message)
        errorMessage = findViewById(R.id.error_message)
        loginFormLayout = findViewById(R.id.login_form_layout)
        loginTitleText = findViewById(R.id.title_text_view)
        loginSubtitleText = findViewById(R.id.prompt_text_view)

        // Set the initial visibility of the logout button to GONE
        logoutButton.visibility = View.GONE

        // Set up the click listeners
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            loginViewModel.login(username, password)
        }

        logoutButton.setOnClickListener {
            loginViewModel.resetLoginState()
        }

        // Observe the loginResult LiveData
        loginViewModel.loginResult.observe(this) { result ->
            when (result) {
                is LoginViewModel.LoginResult.Success -> {
                    // Hide the login form and show the welcome message and logout button
                    loginFormLayout.visibility = View.GONE
                    welcomeMessage.visibility = View.VISIBLE
                    welcomeMessage.text = "Welcome to PornHub sir ivan!"
                    errorMessage.visibility = View.GONE
                    loginTitleText.visibility = View.GONE
                    loginSubtitleText.visibility = View.GONE
                    logoutButton.visibility = View.VISIBLE // Show the logout button
                }
                is LoginViewModel.LoginResult.Error -> {
                    // Show an error message and keep the login form visible
                    errorMessage.text = result.message
                    errorMessage.visibility = View.VISIBLE
                    loginFormLayout.visibility = View.VISIBLE
                    welcomeMessage.visibility = View.GONE
                    passwordEditText.text?.clear()
                    logoutButton.visibility = View.GONE // Hide the logout button on error
                }
                is LoginViewModel.LoginResult.Idle -> {
                    // Reset the UI to its initial state
                    loginFormLayout.visibility = View.VISIBLE
                    welcomeMessage.visibility = View.GONE
                    errorMessage.visibility = View.GONE
                    loginTitleText.visibility = View.VISIBLE
                    loginSubtitleText.visibility = View.VISIBLE
                    logoutButton.visibility = View.GONE // Ensure the logout button is hidden
                    usernameEditText.text?.clear()
                    passwordEditText.text?.clear()
                }
            }
        }
    }
}