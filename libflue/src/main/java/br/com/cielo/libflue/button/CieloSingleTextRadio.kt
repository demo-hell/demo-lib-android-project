package br.com.cielo.libflue.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import br.com.cielo.libflue.R
import kotlinx.android.synthetic.main.layout_cielo_regular_text_outline_icon.view.textView
import kotlinx.android.synthetic.main.layout_cielo_single_text_radio.view.*

class CieloSingleTextRadio @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CieloBaseRadioButton(context, attrs, defStyleAttr) {

    var showLineSeparator = true
        set(value) {
            field = value
            if (field) viewLineSeparator.visibility = View.VISIBLE
            else viewLineSeparator.visibility = View.INVISIBLE
            invalidate()
            requestLayout()
        }

    init {
        LayoutInflater
            .from(context)
            .inflate(R.layout.layout_cielo_single_text_radio, this, true)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CieloBaseRadioButton,
            0,
            0
        ).apply {
            try {
                getString(R.styleable.CieloBaseRadioButton_android_text)?.let {
                    text = it
                }
                getBoolean(R.styleable.CieloBaseRadioButton_android_enabled, true).let {
                    isEnabled = it
                }
                showLineSeparator =
                    getBoolean(R.styleable.CieloBaseRadioButton_showLineSeparator, true)
            } finally {
                recycle()
            }
        }
        isClickable = true
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        this.textView?.text = this.text
    }

    override fun setChecked(checked: Boolean) {
        this.isCheckedValue = checked
        if (checked) {
            this.textView?.setTextColor(ContextCompat.getColor(this.context, R.color.cielo_400))
            this.imageViewIcon?.setImageResource(R.drawable.ic_radio_button_selected)
        } else {
            this.textView?.setTextColor(ContextCompat.getColor(this.context, R.color.display_400))
            this.imageViewIcon?.setImageResource(R.drawable.ic_radio_button_unselect_background_white)
        }
    }

    override fun setTextValue(text: String) {
        this.text = text
        this.textView?.text = text
    }
}