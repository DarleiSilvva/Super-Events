package empresa_x.studio.com.superevents.autenticacao

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import empresa_x.studio.com.superevents.MainActivity
import empresa_x.studio.com.superevents.R


class CadastroActivity : AppCompatActivity() {
    private var mEditEmail: EditText? = null
    private var mEditPassword: EditText? = null
    private var edit_username: EditText? = null
    private var mBtnEnter: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        mEditEmail = findViewById(R.id.edit_email)
        mEditPassword = findViewById(R.id.edit_password)
        mBtnEnter = findViewById(R.id.button_entrar)
        edit_username = findViewById(R.id.edit_username)
        mBtnEnter!!.setOnClickListener(View.OnClickListener { createUser() })
    }

    private fun createUser() {
        val nome = edit_username!!.text.toString()
        val email = mEditEmail!!.text.toString()
        val senha = mEditPassword!!.text.toString()
        if (nome == null || nome.isEmpty() || email == null || email.isEmpty() || senha == null || senha.isEmpty()) {
            Toast.makeText(this, "Nome, senha e email devem ser preenchidos", Toast.LENGTH_SHORT)
                .show()
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    saveUserInFirebase()
                    val intent = Intent(this@CadastroActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.putExtra("FIXAR_CADASTRO", 1)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@CadastroActivity,
                        "Coloque um endereço válido!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            .addOnFailureListener { }
    }

    private fun saveUserInFirebase() {
        val uid = FirebaseAuth.getInstance().uid
        val nomeDoUsuario = edit_username!!.text.toString()
        val users = Usuario(uid, nomeDoUsuario)
        FirebaseFirestore.getInstance().collection("users")
            .document(uid!!)
            .set(users)
            .addOnSuccessListener {
                val intent = Intent(this@CadastroActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra("FIXAR_CADASTRO", 1)
                startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(
                    this@CadastroActivity,
                    "Cadastre outro email ou vá para a tela de login ver se esse cadastrou!",
                    Toast.LENGTH_LONG
                ).show()
            }
    }
}