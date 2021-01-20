package empresa_x.studio.com.superevents.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import empresa_x.studio.com.superevents.eventos_model.EventosModel
import empresa_x.studio.com.superevents.repositorio.Repositorio
import java.util.ArrayList

class MainViewModel: ViewModel () {

    val repositorio = Repositorio ()
    fun getLiveDataEventosDatabase (): MutableLiveData<ArrayList<EventosModel>> {
        val mutableData = MutableLiveData<ArrayList<EventosModel>>()
        mutableData.value = repositorio.getEventoData()
        return mutableData
    }
    fun getLiveDataMeusIngressosDatabase (): MutableLiveData<ArrayList<EventosModel>> {
        val mutableData = MutableLiveData<ArrayList<EventosModel>>()
        mutableData.value =  repositorio.getMeusIngressosData()
        return mutableData
    }
}