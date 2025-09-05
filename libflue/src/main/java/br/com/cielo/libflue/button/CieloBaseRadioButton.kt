package br.com.cielo.libflue.button

import android.content.Context
import android.util.AttributeSet
import android.widget.Checkable
import androidx.constraintlayout.widget.ConstraintLayout

abstract class CieloBaseRadioButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), Checkable {

    protected var isCheckedValue: Boolean = false
    protected var text: String = ""

    abstract fun setTextValue(text: String)

    fun getTextValue() = this.text

    override fun isChecked() = this.isCheckedValue

    override fun toggle() {
        this.isChecked = !isCheckedValue
    }

}