package br.com.cielo.libflue.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import br.com.cielo.libflue.R
import kotlinx.android.synthetic.main.layout_cielo_regular_blue_button.view.*

class CieloRegularWhiteButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var text: String = ""

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CieloRegularSecondaryButton,
            0,
            0).apply {
            try {
                getString(R.styleable.CieloRegularSecondaryButton_android_text)?.let {
                    this@CieloRegularWhiteButton.text = it
                }
                getBoolean(R.styleable.CieloRegularSecondaryButton_android_enabled, true).let {
                    isEnabled = it
                }
            } finally {
                recycle()
            }
        }

        isClickable = true
        setBackgroundResource(R.drawable.cielo_white_button_view_selector)

        LayoutInflater
            .from(context)
            .inflate(R.layout.layout_cielo_regular_blue_button, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        this.textView?.text = this.text
        this.textView?.setTextColor(ContextCompat.getColor(this.context, when(this.isEnabled) {
            true -> R.color.brand_400
            false -> android.R.color.white
        }))
    }
}
