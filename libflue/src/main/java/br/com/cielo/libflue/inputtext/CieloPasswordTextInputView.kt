package br.com.cielo.libflue.inputtext

import android.content.Context
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import br.com.cielo.libflue.R
import kotlinx.android.synthetic.main.layout_cielo_text_input_view.view.*

class CieloPasswordTextInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CieloTextInputView(context, attrs, defStyleAttr) {

    private var toogleVisible: Boolean = false

    override fun onFinishInflate() {
        super.onFinishInflate()
        val isChecked = this.textView.inputType or InputType.TYPE_CLASS_NUMBER
        configureInputType()
        changePasswordImage()
        configureListeners()
    }

    private fun configureInputType() {
        if ((this.textView.inputType or InputType.TYPE_CLASS_NUMBER) != InputType.TYPE_CLASS_NUMBER) {
            this.textView
                .inputType = this.textView.inputType or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        } else {
            this.textView.keyListener = DigitsKeyListener.getInstance("0123456789")
        }

        if (this.toogleVisible) {
            this.textView?.transformationMethod = null
        } else {
            this.textView?.transformationMethod = PasswordTransformationMethod()
        }
        this.goToEndText()
    }

    private fun configureListeners() {
        this.setOnEndIconClickListener(OnClickListener {
            this.toogleVisible = !this.toogleVisible
            configureInputType()
            changePasswordImage()
        })
    }

    private fun changePasswordImage() {
        setEndIconDrawable(
            AppCompatResources.getDrawable(
                this.context,
                if (this.toogleVisible) R.drawable.ic_password_show else R.drawable.ic_password_hide
            )
        )
    }

    override fun setInputType(type: Int) {
        val isChecked = type or InputType.TYPE_CLASS_NUMBER
        super.setInputType(type)
        configureInputType()
    }

    fun hasTransformationMethod() = this.textView.transformationMethod != null
}