package empresa_x.studio.com.superevents

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.content.SharedPreferences
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import empresa_x.studio.com.superevents.autenticacao.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import androidx.recyclerview.widget.LinearLayoutManager
import android.graphics.drawable.ColorDrawable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import empresa_x.studio.com.superevents.viewmodels.MainViewModel
import com.google.firebase.database.*
import empresa_x.studio.com.superevents.eventos_adapter.EventosAdapter
import java.util.*


class MainActivity : AppCompatActivity() {

    var dialog: Dialog? = null

    private lateinit var eventosAdapter: EventosAdapter
    private lateinit var meusIngressosAdapter: EventosAdapter
    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java)}

    var recyclerView: RecyclerView? = null
    var identificarTipo = false
    var constraintLayout1: ConstraintLayout? = null
    var imageView1: ImageView? = null
    var textView1: TextView? = null
    var constraintLayout2: ConstraintLayout? = null
    var imageView2: ImageView? = null
    var textView2: TextView? = null
    var uid: String? = null
    var parafixarCadastro = 0
    var settings: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = "   Super Events "
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        toolbar.setLogo(R.drawable.ic_logo_redondo)
        toolbar.setBackgroundResource(R.color.vermelho2)
        setSupportActionBar(toolbar)

        val fixarCadastro = intent.getIntExtra("FIXAR_CADASTRO", 0)
        settings = getSharedPreferences("fixar", MODE_PRIVATE)
        parafixarCadastro = settings!!.getInt("fixarCadastro", 0)
        parafixarCadastro += fixarCadastro
        val editor = settings!!.edit()
        editor.putInt("fixarCadastro", parafixarCadastro)
        editor.commit()
        if (parafixarCadastro == 0) {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            finish()
            startActivity(intent)
        }
        dialog = Dialog(this)
        recyclerView = findViewById<View>(R.id.recycleView) as RecyclerView
        uid = FirebaseAuth.getInstance().uid
        constraintLayout1 = findViewById<View>(R.id.constraintLayout_Eventos) as ConstraintLayout
        imageView1 = findViewById<View>(R.id.imageView_Eventos) as ImageView
        textView1 = findViewById<View>(R.id.textview_Eventos) as TextView
        constraintLayout2 = findViewById<View>(R.id.constraintLayout_MeusIngressos) as ConstraintLayout
        imageView2 = findViewById<View>(R.id.imageView_MeusIngressos) as ImageView
        textView2 = findViewById<View>(R.id.textview_MeusIngressos) as TextView
        textView1!!.setTextColor(resources.getColor(R.color.white))
        constraintLayout1!!.setBackgroundResource(R.drawable.fundo_button_ui_dark)
        imageView1!!.setImageResource(R.drawable.ic_calendario_dark)
        identificarTipo = true
        adaptadorEventoClasse()

        constraintLayout1!!.setOnClickListener(View.OnClickListener {
            adaptadorEventoClasse()
            textView1!!.setTextColor(resources.getColor(R.color.white))
            constraintLayout1!!.setBackgroundResource(R.drawable.fundo_button_ui_dark)
            imageView1!!.setImageResource(R.drawable.ic_calendario_dark)
            identificarTipo = true
            textView2!!.setTextColor(resources.getColor(R.color.vermelho3))
            constraintLayout2!!.setBackgroundResource(R.drawable.fundo_button_ui_light)
            imageView2!!.setImageResource(R.drawable.ic_ticket_light)
        })
        constraintLayout2!!.setOnClickListener(View.OnClickListener {
            adaptadorMeusIngressosClasse()
            textView2!!.setTextColor(resources.getColor(R.color.white))
            constraintLayout2!!.setBackgroundResource(R.drawable.fundo_button_ui_dark)
            imageView2!!.setImageResource(R.drawable.ic_ticket_dark)
            identificarTipo = false
            textView1!!.setTextColor(resources.getColor(R.color.vermelho3))
            constraintLayout1!!.setBackgroundResource(R.drawable.fundo_button_ui_light)
            imageView1!!.setImageResource(R.drawable.ic_calendario_light)
        })
    }

    fun adaptadorEventoClasse() {
        recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity)
        eventosAdapter = EventosAdapter(viewModel.getLiveDataEventosDatabase().value!!, this@MainActivity)
        recyclerView!!.adapter = eventosAdapter
        observeDataDatabase ()
    }

    fun adaptadorMeusIngressosClasse() {
        recyclerView!!.layoutManager = LinearLayoutManager(this@MainActivity)
        meusIngressosAdapter = EventosAdapter(viewModel.getLiveDataMeusIngressosDatabase().value!!, this@MainActivity)
        recyclerView!!.adapter = meusIngressosAdapter
        observeDataReceita ()
    }

    fun observeDataDatabase (){
        viewModel.getLiveDataEventosDatabase().observe(this, Observer {
            eventosAdapter.notifyDataSetChanged()
        })
    }
    fun observeDataReceita (){
        viewModel.getLiveDataMeusIngressosDatabase().observe(this, Observer {
            meusIngressosAdapter.notifyDataSetChanged()
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.logout) {
            logout()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    fun logout() {
        val txtclose: TextView
        val logout_text: TextView
        dialog!!.setContentView(R.layout.log_out_dados_popup)
        txtclose = dialog!!.findViewById<View>(R.id.txtclose) as TextView
        logout_text = dialog!!.findViewById<View>(R.id.logout_text) as TextView
        logout_text.setOnClickListener {
            parafixarCadastro = settings!!.getInt("fixarCadastro", 0)
            parafixarCadastro = 0
            val editor = settings!!.edit()
            editor.putInt("fixarCadastro", parafixarCadastro)
            editor.commit()
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            finish()
            startActivity(intent)
        }
        txtclose.setOnClickListener { dialog!!.dismiss() }
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.show()
    }
}