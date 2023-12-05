package com.example.teste.classesAuxiliares

import com.example.teste.Model.Filme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ObterListaDeFilmes(private val url: String, private val apiKey: String)
{
    suspend fun fazerRequisicao(): String?
    {
        return runBlocking{
            val resultadoAssincrono = async(Dispatchers.IO)
            {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url(url)
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer $apiKey")
                    .build()

                val response: Response = client.newCall(request).execute()

                response.body?.string()
            }

            resultadoAssincrono.await()
        }
    }



    //convert para json

    fun parseJson(respostaAPI: String?): List<Filme>
    {
        val listaFilmes = mutableListOf<Filme>()

        try
        {
            val jsonObject = JSONObject(respostaAPI)
            val resultsArray = jsonObject.getJSONArray("results")

            for(i in 0 until resultsArray.length())
            {
                val filmeJson = resultsArray.getJSONObject(i)
                val filme = parseFilme(filmeJson)
                listaFilmes.add(filme)
            }
        }
        catch(e: JSONException)
        {
            e.printStackTrace()
        }

        return listaFilmes
    }

    private fun parseFilme(filmeJson: JSONObject): Filme
    {
        return with(filmeJson)
        {
            Filme(
                getBoolean("adult"),
                getString("backdrop_path"),
                parseGenreIds(getJSONArray("genre_ids")),
                getInt("id"),
                getString("original_language"),
                getString("original_title"),
                getString("overview"),
                getDouble("popularity"),
                getString("poster_path"),
                getString("release_date"),
                getString("title"),
                getBoolean("video"),
                getDouble("vote_average"),
                getInt("vote_count")
            )
        }
    }

    private fun parseGenreIds(genreIdsArray: JSONArray): List<Int>
    {
        return List(genreIdsArray.length())
        {
            genreIdsArray.getInt(it);
        }
    }


    suspend fun getJson(): List<Filme>
    {
        val tempResposta = this.fazerRequisicao();
        val tempJson = this.parseJson(tempResposta);
        return tempJson;
    }
}

