package com.example.grizel.recyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var personas: ArrayList<Persona>? = null
    var adapter:PersonasAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*personas.add(Persona("Grizel","http://los40es00.epimg.net/los40/imagenes/2017/09/04/actualidad/1504523276_027610_1504523352_noticia_normal.jpg","Femenino"))
        personas.add(Persona("Grizel","http://los40es00.epimg.net/los40/imagenes/2017/09/04/actualidad/1504523276_027610_1504523352_noticia_normal.jpg","Femenino"))
        personas.add(Persona("Grizel","http://los40es00.epimg.net/los40/imagenes/2017/09/04/actualidad/1504523276_027610_1504523352_noticia_normal.jpg","Femenino"))
        personas.add(Persona("Grizel","http://los40es00.epimg.net/los40/imagenes/2017/09/04/actualidad/1504523276_027610_1504523352_noticia_normal.jpg","Femenino"))*/

        recyclerPersonas.layoutManager = GridLayoutManager(applicationContext, 1)
        recyclerPersonas.setHasFixedSize(true)
        personas = ArrayList()
        adapter = PersonasAdapter(personas!!, this)
        recyclerPersonas.adapter = adapter

        val cache = DiskBasedCache(cacheDir,1024*1024)
        val network = BasicNetwork(HurlStack())

        val requestQueue = RequestQueue(cache,network).apply{
            start()
        }
        val url = "https://randomuser.me/api/?results=10"

        val jsonObjectPersonas = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {
                response ->
                //Log.d("respuesta", response.toString())
                val resultadoJSON = response.getJSONArray("results")

                for (indice in 0..resultadoJSON.length()-1){
                    val personaJSON = resultadoJSON.getJSONObject(indice)
                    val genero = personaJSON.getString("gender")
                    val nombreJSON = personaJSON.getJSONObject("name")
                    val nombrePersona = "${nombreJSON.getString("title")} "+
                    "${nombreJSON.getString("first")} ${nombreJSON.getString("last")}"
                    val fotoJSON = personaJSON.getJSONObject("picture")
                    val foto = fotoJSON.getString("large")
                    val locationJSON = personaJSON.getJSONObject("location")
                    //val coordJSON = locationJSON.getJSONObject("coordinates")
                    //val latitud = coordJSON.getString("latitude").toDouble()
                    //val longitud = coordJSON.getString("latitude").toDouble()

                    personas!!.add(Persona(nombrePersona, foto,genero))

                }
                adapter!!.notifyDataSetChanged()

            }, Response.ErrorListener {
                error ->
                Log.wtf("error volley: ", error.localizedMessage)
            })

        requestQueue.add(jsonObjectPersonas)
    }

}
