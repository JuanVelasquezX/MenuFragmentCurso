package cr.ac.menufragmentcurso

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import com.squareup.picasso.Picasso
import cr.ac.menufragmentcurso.entity.Empleado
import cr.ac.menufragmentcurso.repository.EmpleadoRepository
import java.io.ByteArrayOutputStream

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val PICK_IMAGE = 64


/**
 * A simple [Fragment] subclass.
 * Use the [AddEmpleadoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddEmpleadoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    lateinit var img_avatar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val vista = inflater.inflate(R.layout.fragment_add_empleado, container, false)
        img_avatar = vista.findViewById(R.id.avatar_add)

        vista.findViewById<Button>(R.id.btnAgregar).setOnClickListener { Agregar() }

        img_avatar.setOnClickListener {
            var gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, PICK_IMAGE)
        }

        return vista
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            var imageUri = data?.data

            Picasso.get()
                .load(imageUri)
                .resize(240, 240)
                .centerCrop()
                .into(img_avatar)

        }
    }

    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun decodeImage(b64: String): Bitmap {
        val imageBytes = Base64.decode(b64, 0)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    fun Agregar() {

        val add_id = view?.findViewById<TextView>(R.id.id_add)
        val add_nombre = view?.findViewById<TextView>(R.id.nombre_add)
        val add_puesto = view?.findViewById<TextView>(R.id.puesto_add)
        val add_departamento = view?.findViewById<TextView>(R.id.departamento_add)
        val add_avatar = view?.findViewById<ImageView>(R.id.avatar_add)
        val x = add_avatar?.drawable?.let { encodeImage(it.toBitmap()) }



        //  Se crea la instancia del Empleado con cada uno de los parametros
        var empleado: Empleado = Empleado(
            0,
            add_id?.text.toString(),
            add_nombre?.text.toString(),
            add_puesto?.text.toString(),
            add_departamento
                ?.text.toString(),
            x
        )
        //  Guarda al empleado
        empleado?.let { EmpleadoRepository.instance.save(it) }

        Salir()


    }

    fun Salir() {
        val fragmento: Fragment = CamaraFragment.newInstance("Camara")
        fragmentManager
            ?.beginTransaction()
            ?.replace(R.id.home_content, fragmento)
            ?.commit()
        activity?.setTitle("Camara")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddEmpleadoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            AddEmpleadoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                }
            }
    }
}