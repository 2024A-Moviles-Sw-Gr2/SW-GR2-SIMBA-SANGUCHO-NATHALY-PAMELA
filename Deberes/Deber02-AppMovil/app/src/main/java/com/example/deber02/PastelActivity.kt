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
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class PastelActivity : AppCompatActivity() {
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pastel)

        val botonCrearPastel = findViewById<Button>(R.id.btn_crear_list_pasteles)
        botonCrearPastel.setOnClickListener {
            obtenerDatos(
                PastelCRUD::class.java,
                0
            )
        }
        guardarDatos()
    }




    override fun onContextItemSelected(item: MenuItem): Boolean {
        val id = pastelSeleccionado?.idPastel ?: return super.onContextItemSelected(item)

        return when(item.itemId){
            R.id.mi_editar_pastel ->{
                obtenerDatos(
                    PastelCRUD::class.java,
                    id
                )
                return true
            }
            R.id.mi_eliminar_pastel ->{
                BaseDeDatos.tablaPasteles!!.eliminarPastel(
                    id
                )
                guardarDatos()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    var pastelSeleccionado: Pastel? = null
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_pastel, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        pastelSeleccionado = listView.adapter.getItem(info.position) as Pastel

    }

    fun obtenerDatos(clase: Class<*>, id: Int) {
        val intentExplicito = Intent(this, clase)
        intentExplicito.putExtra("idPastel", id)
        intentExplicito.putExtra("id", intent.getIntExtra("id", 0))
        startActivity(intentExplicito)
    }

    fun guardarDatos(){
        val idPasteleria = intent.getIntExtra("id", 0)
        val pasteles = BaseDeDatos.tablaPasteles!!.obtenerPastelesDePastelerias(idPasteleria)
        val pasteleriaEncontrado = BaseDeDatos.tablaPastelerias!!.obtenerIDPasteleria(idPasteleria)
        val nombrePasteleria = findViewById<TextView>(R.id.tv_pastelerias)
        if (pasteleriaEncontrado != null) {
            nombrePasteleria.text = pasteleriaEncontrado.nombrePasteleria
        } else {
            // Maneja el caso cuando pasteleriaEncontrado es null
            nombrePasteleria.text = "Pastelería no encontrada"
        }
        listView = findViewById<ListView>(R.id.lv_list_pasteles)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            pasteles
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }

    override fun onResume() {
        super.onResume()
        guardarDatos()
    }
    fun mostrarSnackBar(texto: String) {
        Snackbar.make(
            findViewById(R.id.layout_pasteles),
            texto,
            Snackbar.LENGTH_LONG
        )
            .setAction("Action", null).show()
    }
}