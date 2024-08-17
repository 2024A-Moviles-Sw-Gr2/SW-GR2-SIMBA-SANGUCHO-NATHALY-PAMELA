package com.example.sistema_financiero

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistema_financiero.Adapter.CuentaAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CuentasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CuentasFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private val CREATE_ACCOUNT_REQUEST = 1
    private val EDIT_ACCOUNT_REQUEST = 2
    private lateinit var adapter: CuentaAdapter
    private var cuentas: ArrayList<Cuenta> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cuentas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addAccountFab: FloatingActionButton = view.findViewById(R.id.addAccountFab)
        addAccountFab.setOnClickListener {
            val intent = Intent(activity, CuentaCRUD::class.java)
            startActivityForResult(intent, CREATE_ACCOUNT_REQUEST)
        }
        initRecyclerView(view)
    }

    // Inicializa el RecyclerView
    fun initRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.cuentas_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        BaseDeDatos.tablaCuentas = SqliteHelper(requireContext())
        cuentas = BaseDeDatos.tablaCuentas!!.obtenerCuentas()
        adapter = CuentaAdapter(cuentas)
        recyclerView.adapter = adapter

        // Configura el adaptador para manejar la edición de cuentas
        adapter.setOnItemEditListener { cuenta ->
            val intent = Intent(activity, CuentaCRUD::class.java).apply {
                putExtra("cuentaSelecionada", cuenta)
            }
            startActivityForResult(intent, EDIT_ACCOUNT_REQUEST)
        }
        adapter.setOnItemDeleteListener { cuenta ->
            val exito = BaseDeDatos.tablaCuentas!!.eliminarCuenta(cuenta.id)
            if (exito) {
                adapter.eliminarCuenta(cuenta)
            } else {
                // Mostrar un mensaje de error o manejar la falla de eliminación
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CREATE_ACCOUNT_REQUEST -> {
                    val cuentaCreada = data?.getParcelableExtra<Cuenta>("cuentaCreada")
                    cuentaCreada?.let {
                        cuentas.add(it)
                        adapter.notifyDataSetChanged()
                    }
                }
                EDIT_ACCOUNT_REQUEST -> {
                    val cuentaActualizada = data?.getParcelableExtra<Cuenta>("cuentaActualizada")
                    cuentaActualizada?.let {
                        adapter.actualizarCuenta(it)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CuentasFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

