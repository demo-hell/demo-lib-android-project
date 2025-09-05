package br.com.cielo.libflue.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import br.com.cielo.libflue.R
import kotlinx.android.synthetic.main.layout_cielo_large_blue_button.view.*
import kotlinx.android.synthetic.main.layout_cielo_small_blue_button.view.*
import kotlinx.android.synthetic.main.layout_cielo_small_blue_button.view.textView

class CieloLargeBlueButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var text: String = ""

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CieloLargeBlueButton,
            0,
            0).apply {
            try {
                getString(R.styleable.CieloLargeBlueButton_android_text)?.let {
                    this@CieloLargeBlueButton.text = it
                }
                getBoolean(R.styleable.CieloLargeBlueButton_android_enabled, true).let {
                    isEnabled = it
                }
            } finally {
                recycle()
            }
        }

        isClickable = true
        setBackgroundResource(R.drawable.cielo_blue_button_view_selector)

        LayoutInflater
            .from(context)
            .inflate(R.layout.layout_cielo_large_blue_button, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        this.textView?.text = this.text
    }

    fun setText(text: String) {
        this.text = text
        this.textView?.text = this.text
    }

}