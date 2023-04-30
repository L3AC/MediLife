package com.example.medilife

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.medilife.databinding.ActivityMainInsideBinding

@Suppress("UNREACHABLE_CODE")
class MainInside : AppCompatActivity() {
    var idCuenta: Int = 0
    var idTipo: Int = 0

    private lateinit var binding: ActivityMainInsideBinding

    @SuppressLint("ResourceType")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val extras = intent.extras
        idCuenta = extras?.getInt("idCuenta")!!
        idTipo = extras?.getInt("idTipo")!!

        binding = ActivityMainInsideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main_inside)

        val bundle = Bundle().apply {
            putInt("idcu", idCuenta)
        }

        if (idTipo == 1) {
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.homeDoctor,R.id.cuentaGeneral
                )
            )

            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
            navController.navigate(R.id.homeDoctor,bundle)

            val it1 = navView.menu.findItem(R.id.homeCliente)
            val it2 = navView.menu.findItem(R.id.homeSecretaria)
            it1.isVisible=false
            it2.isVisible=false

            navView.setOnNavigationItemSelectedListener { item ->
                navController.navigate(item.itemId, bundle)
                true
            }
        }
        if (idTipo == 2) {
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.homeSecretaria, R.id.cuentaGeneral
                )
            )

            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
            navController.navigate(R.id.homeSecretaria,bundle)

            val it1 = navView.menu.findItem(R.id.homeCliente)
            val it2 = navView.menu.findItem(R.id.homeDoctor)
            it1.isVisible=false
            it2.isVisible=false

            navView.setOnNavigationItemSelectedListener { item ->
                navController.navigate(item.itemId, bundle)
                true
            }

        }
        if (idTipo == 3) {
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.homeCliente, R.id.cuentaGeneral
                )
            )

            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
            navController.navigate(R.id.homeCliente,bundle)

            val it1 = navView.menu.findItem(R.id.homeSecretaria)
            val it2 = navView.menu.findItem(R.id.homeDoctor)
            it1.isVisible=false
            it2.isVisible=false

            navView.setOnNavigationItemSelectedListener { item ->
                navController.navigate(item.itemId, bundle)
                true
            }
        }

    }


    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onBackPressed() {
        // Deja vacío este método
    }

}