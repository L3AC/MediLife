package com.example.medilife

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.medilife.databinding.ActivityMainInsideBinding

class MainInside : AppCompatActivity() {
    var idCuenta: Int = 0
    var idTipo:Int=0
    private lateinit var binding: ActivityMainInsideBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras
        idCuenta = extras?.getInt("idCuenta")!!
        idTipo = extras?.getInt("idTipo")!!



        binding = ActivityMainInsideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main_inside)


        if (idTipo == 1) {

            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.homeDoctor, R.id.cuentaGeneral
                )
            )
            //PASAR PARAMETROS
            val bundle = Bundle().apply {
                putInt("idoc", idCuenta)
            }
            val fragmentoDestino = HomeDoctor()
            fragmentoDestino.arguments =bundle
            //INICIAR CON OTRO FRAGMENT
            navController.navigate(R.id.homeDoctor)
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }
        if(idTipo==2){
            val appBarConfiguration = AppBarConfiguration(
                setOf(

                    R.id.homeSecretaria, R.id.cuentaGeneral
                )
            )
            val bundle = Bundle().apply {
                putInt("idoc", idCuenta)
            }
            val fragmentoDestino = HomeDoctor()
            fragmentoDestino.arguments =bundle
            navController.navigate(R.id.homeSecretaria)
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)

        }
        if(idTipo==3){
            val appBarConfiguration = AppBarConfiguration(
                setOf(

                    R.id.homeCliente, R.id.cuentaGeneral
                )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }
    }
    override fun onBackPressed() {
        // Deja vacío este método
    }

}