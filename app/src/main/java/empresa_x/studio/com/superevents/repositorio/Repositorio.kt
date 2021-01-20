package empresa_x.studio.com.superevents.repositorio

import empresa_x.studio.com.superevents.eventos_model.EventosModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Repositorio {
    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null
    var repositorioInstance: Repositorio? = null
    var arrayEventos = ArrayList<EventosModel>()
    var arrayMeusIngressosModel = ArrayList<EventosModel>()

    fun repositorio(): Repositorio? {
        if (repositorioInstance == null) {
            repositorioInstance = Repositorio()
        }
        return repositorioInstance
    }

    fun getEventoData(): ArrayList<EventosModel> {
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase!!.reference
        databaseReference!!.child("Eventos").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                arrayEventos.clear()
                var eventosModel: EventosModel
                for (data in dataSnapshot.children) {
                    eventosModel = EventosModel()
                    val descricao = data.child("descricao").value as String?
                    val titulo_do_evento = data.child("titulo_do_evento").value as String?
                    val dataHora = data.child("data").value as String?
                    val compra = data.child("compra").value as Boolean
                    val urlImg = data.child("urlImg").value as String?

                    eventosModel.id = data.child("id").value.toString()
                    eventosModel.valor = data.child("valor").value.toString().toInt()
                    eventosModel.descricao = descricao
                    eventosModel.titulo_do_evento = titulo_do_evento
                    eventosModel.data = dataHora
                    eventosModel.urlImg = urlImg
                    eventosModel.compra = compra
                    arrayEventos.add(eventosModel)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException()
            }
        })
        return arrayEventos
    }

    fun getMeusIngressosData(): ArrayList<EventosModel> {
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase!!.reference
        val uid = FirebaseAuth.getInstance().uid
        databaseReference!!.child("MeusIngressos$uid").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                arrayMeusIngressosModel.clear()
                var meusIngressosModel: EventosModel
                for (data in dataSnapshot.children) {
                    meusIngressosModel = EventosModel()
                    val descricao = data.child("descricao").value as String?
                    val titulo_do_evento = data.child("titulo_do_evento").value as String?
                    val dataHora = data.child("data").value as String?
                    val compra = data.child("compra").value as Boolean
                    val urlImg = data.child("urlImg").value as String?

                    meusIngressosModel.id = data.child("id").value.toString()
                    meusIngressosModel.valor = data.child("valor").value.toString().toInt()
                    meusIngressosModel.descricao = descricao
                    meusIngressosModel.titulo_do_evento = titulo_do_evento
                    meusIngressosModel.data = dataHora
                    meusIngressosModel.urlImg = urlImg
                    meusIngressosModel.compra = compra
                    arrayMeusIngressosModel.add(meusIngressosModel)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException()
            }
        })
        return arrayMeusIngressosModel
    }
}