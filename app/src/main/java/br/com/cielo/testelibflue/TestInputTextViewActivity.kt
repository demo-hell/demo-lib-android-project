package br.com.cielo.testelibflue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_test_input_text_view.*

class TestInputTextViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_input_text_view)
        this.textInput03.setOnEndIconClickListener(View.OnClickListener {
            Toast.makeText(this, "End Icon Image Clicked", Toast.LENGTH_SHORT).show()
        })
        this.textInput02?.setHint("Usuário")
        this.textInput02?.setText("123455")
        this.textInput02?.isEnabled = false
        textInput04?.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD)
        this.textInput03?.setEndIconDrawable(R.drawable.ic_info_white)
    }

}
