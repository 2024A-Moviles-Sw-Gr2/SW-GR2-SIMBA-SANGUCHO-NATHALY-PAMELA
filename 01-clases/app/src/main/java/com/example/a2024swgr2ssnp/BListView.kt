package com.example.a2024swgr2ssnp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class BListView : AppCompatActivity() {
    val arreglo = BBaseDatosMemoria.arregloBEntrenador
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)
        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = ArrayAdapter(
            this, //Contexto
                    android.R.layout.simple_list_item_1, //Layout xml a usar
                    arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonAñadirListView = findViewById<Button>(
            R.id.btn_anadir_list_view
        )
        botonAñadirListView.setOnClickListener {
            añadirEntrenador(adaptador)
        }
        registerForContextMenu(listView)
    }

    var posicionItemSelecionado = -1

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenuInfo
    ){
        super.onCreateContextMenu(menu,v,menuInfo)
        //Llenamos opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        //Obtener id
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSelecionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                mostrarSnackbar("Editar $posicionItemSelecionado")
                return true
            }
            R.id.mi_eliminar -> {
                mostrarSnackbar("Eliminar $posicionItemSelecionado")
                abrirDialogo()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun añadirEntrenador(
        adaptador: ArrayAdapter<BEntrenador>
    ){
        arreglo.add(
            BEntrenador(4,"Wendy", "d@d.com"))
        adaptador.notifyDataSetChanged()
    }

    fun mostrarSnackbar(texto:String){
        val snack = Snackbar.make(
            //findViewById(R.id.cl_ciclo_vida), // Se hace referencia al ciclo de vida, pero la verdad es que no estamos ahi
            findViewById(R.id.cl_blist_view),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()

    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ese Eliminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{
                dialog, which ->
                mostrarSnackbar("Acepto $which")
            }
        )
        builder.setNegativeButton("Cancelar", null)
        val opciones = resources.getStringArray(
            R.array.string_array_opciones
        )
        val selecionPrevia = booleanArrayOf(
            true, // Lunes,
            false, // Martes
            false, // Miercoles
        )
        builder.setMultiChoiceItems(
            opciones,
            selecionPrevia,
            {
                dialog, which , isChecked ->
                mostrarSnackbar("Item : $which")
            }
        )
        val dialogo = builder.create()
        dialogo.show()
    }


}