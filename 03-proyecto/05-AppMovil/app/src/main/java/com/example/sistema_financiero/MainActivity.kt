package com.example.sistema_financiero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.sistema_financiero.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(InicioFragment())

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.salir -> {
                    val intent = Intent(this, SalirApp::class.java)
                    startActivity(intent)
                }

                R.id.inicio-> {
                    replaceFragment(InicioFragment())
                }

                R.id.cuentas -> {
                    replaceFragment(CuentasFragment())
                }
            }
            true
        }
    }
        private fun replaceFragment(fragment: Fragment) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentLayout, fragment)
            fragmentTransaction.commit()
        }
}