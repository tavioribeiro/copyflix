package com.example.copyflix.Controller

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.copyflix.FilmeActivity
import com.example.copyflix.R
import com.example.teste.Model.Filme

class FilmeAdapter2(private val context: Context, private val listaFilmes: List<Filme>) : RecyclerView.Adapter<FilmeAdapter2.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutInflater.from(context).inflate(R.layout.item_filme, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/original" + listaFilmes[position].posterPath)
            .placeholder(R.drawable.loading)
            .error(R.drawable.logo)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int
    {
        return listaFilmes.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        val imageView: ImageView = itemView.findViewById(R.id.imageView_filme)

        init {
            // add um listener de clique ao itemview
            itemView.setOnClickListener(this)
        }

        // implementa o método onClick para responder ao clique
        override fun onClick(v: View?)
        {
            //pega a posição do filme no adapter
            val position = adapterPosition

            //ve se a posição é válida
            if (position != RecyclerView.NO_POSITION)
            {
                //pega o filme clicado
                val filmeClicado = listaFilmes[position]

                //cria uma Intent para abrir a atividade
                val intent = Intent(context, FilmeActivity::class.java)


                intent.putExtra("titulo", filmeClicado.originalTitle)
                intent.putExtra("descricao", filmeClicado.overview)
                intent.putExtra("posterPath", filmeClicado.posterPath)
                intent.putExtra("backdropPath", filmeClicado.backdropPath)
                intent.putExtra("releaseDate", "Data de Lançamento: " + filmeClicado.releaseDate)
                intent.putExtra("voteAverage", "Avaliação: " + filmeClicado.voteAverage)
                intent.putExtra("id", filmeClicado.id)
                if(filmeClicado.isAdult)
                {
                    intent.putExtra("isAdult", "Este filme é para adultos.");
                }
                else
                {
                    intent.putExtra("isAdult", "Este filme é para todas as idades.");
                }
                intent.putExtra("originalLanguage","Idioma principal: " + filmeClicado.originalLanguage)
                intent.putExtra("isVideo", filmeClicado.isVideo)
                intent.putExtra("voteCount", filmeClicado.voteCount)
                intent.putExtra("popularity", "Popularidade: " + filmeClicado.popularity)

                
                //inicia a atividade
                context.startActivity(intent)
            }
        }
    }

}
