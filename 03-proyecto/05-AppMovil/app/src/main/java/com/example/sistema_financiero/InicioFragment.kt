package com.example.sistema_financiero

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistema_financiero.Adapter.ResumenAdapter
import com.example.sistema_financiero.R
import com.example.sistema_financiero.SqliteHelper
import com.example.sistema_financiero.Cuenta

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class InicioFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ResumenAdapter
    private lateinit var sqliteHelper: SqliteHelper
    private var cuentas: MutableList<Cuenta> = mutableListOf()

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
        // Inflar el layout para este fragmento
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.resumen_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Inicializar SqliteHelper y cargar cuentas desde la base de datos
        sqliteHelper = SqliteHelper(context)
        cuentas = sqliteHelper.obtenerCuentas()

        // Configurar el adaptador
        adapter = ResumenAdapter(cuentas)
        recyclerView.adapter = adapter

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InicioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        refreshCuentas()
    }

    private fun refreshCuentas() {
        cuentas = sqliteHelper.obtenerCuentas()
        adapter.updateCuentas(cuentas)
    }
}
