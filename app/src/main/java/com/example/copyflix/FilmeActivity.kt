package com.example.copyflix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class FilmeActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filme_info)

        //ref elementos do layout
        val posterImageViewDetalhes: ImageView = findViewById(R.id.posterImageViewDetalhes)
        val tituloTextView: TextView = findViewById(R.id.tituloTextView)
        val descricaoTextView: TextView = findViewById(R.id.descricaoTextView)
        val releaseDateTextView: TextView = findViewById(R.id.releaseDateTextView)
        val voteAverageTextView: TextView = findViewById(R.id.voteAverageTextView)
        val isAdultTextView: TextView = findViewById(R.id.isAdultTextView)
        val originalLanguageTextView: TextView = findViewById(R.id.originalLanguageTextView)
        val popularityTextView: TextView = findViewById(R.id.popularityTextView)
    

        // Recebe os dados da Intent
        val titulo = intent.getStringExtra("titulo")
        val descricao = intent.getStringExtra("descricao")
        val posterPath = intent.getStringExtra("posterPath")
        val releaseDate = intent.getStringExtra("releaseDate")
        val voteAverage = intent.getStringExtra("voteAverage")
        val isAdult = intent.getStringExtra("isAdult")
        val originalLanguage = intent.getStringExtra("originalLanguage")
        val popularity = intent.getStringExtra("popularity")


        //configura os dados nos elementos do layout
        tituloTextView.text = titulo
        descricaoTextView.text = descricao
        releaseDateTextView.text = releaseDate
        voteAverageTextView.text = voteAverage
        isAdultTextView.text = isAdult.toString()
        originalLanguageTextView.text = originalLanguage
        popularityTextView.text = popularity
        

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/original$posterPath")
            .placeholder(R.drawable.loading)
            .error(R.drawable.logo)
            .into(posterImageViewDetalhes)
    }
}