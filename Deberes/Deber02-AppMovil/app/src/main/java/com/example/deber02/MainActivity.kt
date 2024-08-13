package com.example.deber02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    lateinit var listView: ListView
    var pasteleriaSeleccionada: Pasteleria? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BaseDeDatos.tablaPastelerias = SqliteHelper(this)
        BaseDeDatos.tablaPasteles = SqliteHelper(this)

        actualizarListado()

        val botonCrearPasteleria = findViewById<Button>(R.id.btn_crear_pasteleria)
        botonCrearPasteleria.setOnClickListener {
            irActividad(PasteleriaCRUD::class.java)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val id = pasteleriaSeleccionada?.id ?: return super.onContextItemSelected(item)
        return when (item.itemId) {
            R.id.mi_editar -> {
                abrirActividadConParametros(PasteleriaCRUD::class.java, id)
                true
            }
            R.id.mi_eliminar -> {
                BaseDeDatos.tablaPastelerias!!.eliminarPasteleria(id)
                actualizarListado()
                true
            }
            R.id.mi_listaPasteles-> {
                abrirActividadConParametros(PastelActivity::class.java, id)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_pasteleria, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        pasteleriaSeleccionada = listView.adapter.getItem(info.position) as Pasteleria
    }

    fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    fun abrirActividadConParametros(clase: Class<*>, id: Int) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("id", id)
        startActivity(intentExplicito)
    }

    fun actualizarListado() {
        val arregloPasteleria = BaseDeDatos.tablaPastelerias!!.obtenerPastelerias()
        listView = findViewById(R.id.lv_list_pastelerias)
        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, arregloPasteleria)
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }

    override fun onResume() {
        super.onResume()
        actualizarListado()
    }
}
