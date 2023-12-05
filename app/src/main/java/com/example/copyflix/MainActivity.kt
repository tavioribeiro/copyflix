package com.example.copyflix

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.copyflix.Controller.FilmeAdapter2
import com.example.teste.Model.ListaDeCarrosseis
import com.example.teste.classesAuxiliares.ObterListaDeFilmes
import kotlinx.coroutines.runBlocking


class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //==============================================================================================================

        if (isNetworkAvailable())
        {
            val listaFilmes = ListaDeCarrosseis();

            val mainContainer: LinearLayout = findViewById(R.id.mainContainer)

            for (i in listaFilmes.carrosseis.indices)
            {
                try {
                    Log.d(
                        "MainActivity",
                        "===================================================================="
                    )

                    val inflater = LayoutInflater.from(this)
                    val carrosselLayout =
                        inflater.inflate(R.layout.carrossel, mainContainer, false) as LinearLayout


                    val textView: TextView = carrosselLayout.findViewById(R.id.textView_carrossel)
                    textView.id =
                        "textView_carrossel_${listaFilmes.carrosseis[i].nomeDoCarrossel}".hashCode()
                    textView.text = listaFilmes.carrosseis[i].nomeDoCarrossel


                    mainContainer.addView(carrosselLayout)

                    runBlocking {

                        val obterListaDeFilmes = ObterListaDeFilmes(
                            listaFilmes.carrosseis[i].url,
                            listaFilmes.carrosseis[i].token
                        )

                        val lista = obterListaDeFilmes.fazerRequisicao();
                        val rjson = obterListaDeFilmes.getJson();

                        Log.d("MainActivity", "recyclerView_carrossel_${listaFilmes.carrosseis[i].nomeDoCarrossel}");


                        val recyclerView: RecyclerView =
                            carrosselLayout.findViewById(R.id.recyclerView_carrossel)
                        recyclerView.layoutManager = LinearLayoutManager(
                            this@MainActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        recyclerView.isNestedScrollingEnabled = false;

                        val adapter = FilmeAdapter2(this@MainActivity, rjson);
                        recyclerView.adapter = adapter;
                    }
                }
                catch (e: Exception)
                {
                    Log.d("MainActivity", "Erro: ${e.message}");
                    Toast.makeText(this, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else
        {
            Toast.makeText(this, "Não há conexão com a internet.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isNetworkAvailable(): Boolean
    {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when
            {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }
        else
        {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo?.isConnected == true
        }
    }
}