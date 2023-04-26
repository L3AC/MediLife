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
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import androidx.navigation.navArgs
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
        val extras = intent.extras
        idCuenta = extras?.getInt("idCuenta")!!
        idTipo = extras?.getInt("idTipo")!!

        binding = ActivityMainInsideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView:BottomNavigationView= binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main_inside)
        val bundle = Bundle().apply {
            putInt("idcu", idCuenta)
        }

        /*if (idTipo == 1) {
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.homeDoctor, R.id.cuentaGeneral
                )
            )
            //PASAR PARAMETROS
            val fragmentoDestino = HomeDoctor()
            fragmentoDestino.arguments = bundle
            //INICIAR CON OTRO FRAGMENT
            navController.navigate(R.id.homeDoctor, bundle)
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }
        if (idTipo == 2) {
            val appBarConfiguration = AppBarConfiguration(
                setOf(

                    R.id.homeSecretaria, R.id.cuentaGeneral
                )
            )
            val fragmentoDestino = HomeSecretaria()
            fragmentoDestino.arguments = bundle
            navController.navigate(R.id.homeSecretaria, args = bundle)
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)

        }*/
        if (idTipo == 3) {
            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.homeCliente, R.id.cuentaGeneral
                )
            )

            val fragmentoDestino = HomeCliente()
            fragmentoDestino.arguments = bundle
            navController.navigate(R.id.homeCliente, bundle)
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)

            navView.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.homeCliente -> {
                        val fragmentoDestino = HomeCliente()
                        fragmentoDestino.arguments = bundle
                        navController.navigate(R.id.homeCliente, bundle)
                        setupActionBarWithNavController(navController, appBarConfiguration)
                        navView.setupWithNavController(navController)
                        true
                    }
                    R.id.cuentaGeneral -> {
                        val fragmentoCuenta = CuentaGeneral()
                        fragmentoCuenta.arguments = bundle
                        navController.navigate(R.id.cuentaGeneral, bundle)
                        setupActionBarWithNavController(navController, appBarConfiguration)
                        navView.setupWithNavController(navController)
                        true
                    }
                    else -> false
                }
            }
        }

    }


    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }

    override fun onBackPressed() {
        // Deja vacío este método
    }

}