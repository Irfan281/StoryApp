package com.irfan.storyapp.view.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.irfan.storyapp.R
import com.irfan.storyapp.data.datastore.SessionPreferences
import com.irfan.storyapp.view.ViewModelFactory
import com.irfan.storyapp.view.home.HomeActivity
import com.irfan.storyapp.view.login.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setupViewModel()
        setupView()
        setupAction()
    }

    private fun setupAction() {
        Handler(Looper.getMainLooper()).postDelayed({
            splashViewModel.getUserToken().observe(this) {
                Log.d("session", "token ${it.token}")
                if (it.token.isEmpty()) {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, resources.getString(R.string.time_splash).toLong())
    }

    private fun setupViewModel() {
        splashViewModel = ViewModelProvider(
            this,
            ViewModelFactory(SessionPreferences.getInstance(dataStore))
        )[SplashViewModel::class.java]
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}