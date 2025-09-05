package br.com.cielo.testelibflue

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureListener()
        editTextPassword.disableCopyToClipboard = false
    }

    private fun configureListener() {
        this.actionsListButton?.setOnClickListener {
            startActivity(ActionsListViewsActivity::class.java)
        }
        this.horizontalButton?.setOnClickListener {
            startActivity(HorizontalActivity::class.java)
        }

        this.buttonComponentSelect.setOnClickListener {
            startActivity(TestComponentFilterSelectAndMultiselectActivity::class.java)
        }
    }

}

fun AppCompatActivity.startActivity(cls: Class<*>) {
    this.startActivity(Intent(this, cls))
}