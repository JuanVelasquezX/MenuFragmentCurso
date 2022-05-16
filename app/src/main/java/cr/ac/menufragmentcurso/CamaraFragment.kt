package cr.ac.menufragmentcurso

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import cr.ac.menufragmentcurso.adapter.EmpleadoAdapter
import cr.ac.menufragmentcurso.entity.Empleado
import cr.ac.menufragmentcurso.repository.EmpleadoRepository

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CamarakFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CamaraFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    //private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            //param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

       val vista = inflater.inflate(R.layout.fragment_camara, container, false)
        val empleadoList= vista.findViewById<ListView>(R.id.empleados_list)
        val empleadoRepository = EmpleadoRepository()
        val empleadoAdapter = context?.let { EmpleadoAdapter(it, EmpleadoRepository.instance.datos()) }
       //al seleccionar en la lista
        empleadoList.onItemClickListener=
            AdapterView.OnItemClickListener{ parent,view, position, id->
                val empleado = parent.getItemAtPosition(position)
                val fragmento: Fragment = EditEmpleadoFragment.newInstance(empleado as Empleado)
                fragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.home_content, fragmento)
                    ?.commit()
                activity?.setTitle("Empleado")
            }
        empleadoList.setAdapter(empleadoAdapter)

        //al hacer click en el fab plus
        vista.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            vista.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{ Agregar() }
        }

        return vista
    }
    fun Agregar(){
        val fragmento: Fragment = AddEmpleadoFragment.newInstance("Agregar")
        fragmentManager
            ?.beginTransaction()
            ?.replace(R.id.home_content, fragmento)
            ?.commit()
        activity?.setTitle("Agregar")
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         /* @param param2 Parameter 2.*/
         * @return A new instance of fragment CamarakFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            CamaraFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                  //  putString(ARG_PARAM2, param2)
                }
            }
    }
}