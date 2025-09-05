package br.com.cielo.libflue.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import br.com.cielo.libflue.R
import kotlinx.android.synthetic.main.layout_cielo_small_blue_button.view.*

class CieloSmallLightButton@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var text: String = ""

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CieloSmallBlueButton,
            0,
            0).apply {
            try {
                getString(R.styleable.CieloSmallBlueButton_android_text)?.let {
                    this@CieloSmallLightButton.text = it
                }
                getBoolean(R.styleable.CieloSmallBlueButton_android_enabled, true).let {
                    isEnabled = it
                }
            } finally {
                recycle()
            }
        }

        isClickable = true
        setBackgroundResource(R.drawable.cielo_light_button_view_selector)

        LayoutInflater
            .from(context)
            .inflate(R.layout.layout_cielo_small_blue_button, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        this.textView?.text = this.text
        configureTextColor()
    }

    fun setText(text: String) {
        this.text = text
        this.textView?.text = this.text
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        configureTextColor()
    }

    private fun configureTextColor() {
        if (this.isEnabled) {
            this.textView?.setTextColor(ContextCompat.getColorStateList(this.context, R.color.cielo_light_button_text_view_selector))
        }
        else {
            this.textView?.setTextColor(ContextCompat.getColor(this.context, android.R.color.white))
        }
    }

}