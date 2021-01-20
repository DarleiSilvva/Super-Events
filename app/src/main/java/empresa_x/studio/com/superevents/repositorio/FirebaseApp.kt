package empresa_x.studio.com.superevents.repositorio

import android.app.Application
import com.google.firebase.database.FirebaseDatabase


class FirebaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}