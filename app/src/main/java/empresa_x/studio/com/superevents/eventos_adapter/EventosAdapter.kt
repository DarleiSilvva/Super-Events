package empresa_x.studio.com.superevents.eventos_adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import empresa_x.studio.com.superevents.R
import empresa_x.studio.com.superevents.eventos_model.EventosModel
import java.util.*


class EventosAdapter(
        private val eventosModel: ArrayList<EventosModel>,
        private val context: Context
) : RecyclerView.Adapter<EventosAdapter.ViewHolder>() {

    var textviewValor_text: TextView? = null
    var textviewDescricao_text: TextView? = null
    var textviewEvento_text: TextView? = null
    var textviewData_text: TextView? = null
    var imageViewBoolean: ImageView? = null
    var imageViewEvento: ImageView? = null
    var compra_aBoolean = false
    var valor: String? = null
    var descricao: String? = null
    var titulo_do_evento: String? = null
    var data: String? = null
    var id: String? = null
    var urlImgS: String? = null
    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.modelo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.valor_text.text = java.lang.String.valueOf(eventosModel[position].valor) + " R$"
        holder.descricao_text.text = eventosModel[position].descricao
        holder.evento_text.text = eventosModel[position].titulo_do_evento
        holder.data_text.text = eventosModel[position].data

        Glide.clear(holder.foto_do_evento)
        Glide.with(context).load(eventosModel[position].urlImg).into(holder.foto_do_evento)

        if (eventosModel[position].compra!!) {
            holder.compra_boolean.setImageResource(R.drawable.ic_check_correto)
        } else {
            holder.compra_boolean.setImageResource(R.drawable.ic_check_errado)
        }
        holder.linearLayout_background.setOnClickListener {
            id = eventosModel[position].id
            valor = java.lang.String.valueOf(eventosModel[position].valor)
            descricao = eventosModel[position].descricao
            titulo_do_evento = eventosModel[position].titulo_do_evento
            data = eventosModel[position].data
            compra_aBoolean = eventosModel[position].compra!!
            urlImgS = eventosModel[position].urlImg
            visualizarEvento()
        }
    }

    override fun getItemCount(): Int {
        return eventosModel.size
    }

    fun visualizarEvento () {
        val dialog = Dialog(context)
        val sair: TextView
        val comprar: TextView
        dialog.setContentView(R.layout.dados_popup)
        textviewValor_text = dialog.findViewById<View>(R.id.valor_text) as TextView
        comprar = dialog.findViewById<View>(R.id.comprar_nome) as TextView
        textviewEvento_text = dialog.findViewById<View>(R.id.evento_text) as TextView
        textviewDescricao_text = dialog.findViewById<View>(R.id.descricao_text) as TextView
        textviewData_text = dialog.findViewById<View>(R.id.data_text) as TextView
        imageViewBoolean = dialog.findViewById<View>(R.id.pago_boolean) as ImageView
        imageViewEvento = dialog.findViewById<View>(R.id.imageEvento) as ImageView
        sair = dialog.findViewById<View>(R.id.sair_nome) as TextView
        textviewValor_text!!.text = valor + "R$"
        textviewDescricao_text!!.text = descricao
        textviewEvento_text!!.text = titulo_do_evento
        textviewData_text!!.text = data
        Glide.with(context).load(urlImgS).into(imageViewEvento)
        if (compra_aBoolean) {
            imageViewBoolean!!.setImageResource(R.drawable.ic_check_correto)
        } else {
            imageViewBoolean!!.setImageResource(R.drawable.ic_check_errado)
        }
        sair.setOnClickListener {
            dialog.dismiss()
        }
        comprar.setOnClickListener {
            firebaseDatabase = FirebaseDatabase.getInstance()
            databaseReference = firebaseDatabase!!.reference

            compra_aBoolean = true
            val uid = FirebaseAuth.getInstance().uid

            imageViewBoolean!!.setImageResource(R.drawable.ic_check_correto)
            val eventosDatabase = EventosModel()
            eventosDatabase.id = id
            eventosDatabase.valor = valor!!.toInt()
            eventosDatabase.descricao = descricao
            eventosDatabase.titulo_do_evento = titulo_do_evento
            eventosDatabase.data = data
            eventosDatabase.compra = compra_aBoolean
            eventosDatabase.urlImg = urlImgS
            databaseReference!!.child("Eventos").child(eventosDatabase.id.toString())
                    .setValue(eventosDatabase)
            databaseReference!!.child("MeusIngressos$uid").child(eventosDatabase.id.toString())
                    .setValue(eventosDatabase)
            Toast.makeText(context, "Comprado!", Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var valor_text: TextView
        var evento_text: TextView
        var descricao_text: TextView
        var data_text: TextView
        var compra_boolean: ImageView
        var foto_do_evento: ImageView
        var linearLayout_background: LinearLayout

        init {
            valor_text = itemView.findViewById(R.id.valor_text)
            evento_text = itemView.findViewById(R.id.evento_text)
            descricao_text = itemView.findViewById(R.id.descricao_text)
            data_text = itemView.findViewById(R.id.data_text)
            compra_boolean = itemView.findViewById(R.id.pago_boolean)
            linearLayout_background = itemView.findViewById(R.id.fundo)
            foto_do_evento = itemView.findViewById(R.id.imageEvento)
        }
    }
}
