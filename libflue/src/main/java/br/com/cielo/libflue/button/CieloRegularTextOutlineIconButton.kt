package br.com.cielo.libflue.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import br.com.cielo.libflue.R
import kotlinx.android.synthetic.main.layout_cielo_regular_text_outline_icon.view.*

class CieloRegularTextOutlineIconButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var text: String = ""

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CieloRegularTextOutlineIconButton,
            0,
            0
        ).apply {
            try {
                getString(R.styleable.CieloRegularTextOutlineIconButton_android_text)?.let {
                    this@CieloRegularTextOutlineIconButton.text = it
                }
                getBoolean(R.styleable.CieloRegularTextOutlineIconButton_android_enabled, true).let {
                    isEnabled = it
                }
            } finally {
                recycle()
            }
        }

        isClickable = true
        setBackgroundResource(R.drawable.cielo_blue_regular_text_outline_icon_selector)

        LayoutInflater
            .from(context)
            .inflate(R.layout.layout_cielo_regular_text_outline_icon, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        this.textView?.text = this.text
        this.iconView?.isEnabled = this.isEnabled
    }

    fun setText(text: String? = null, @ColorRes color: Int? = null) {
        text?.let {
            this.text = it
            this.textView?.text = it
        }
        color?.let {
            this.textView?.setTextColor(ContextCompat.getColor(this.context, it))
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        var color = ContextCompat.getColor(this.context, R.color.cielo_light_button_text_view_selector)
        if (!enabled) {
            color = ContextCompat.getColor(this.context, R.color.display_200)
        }
        this.iconView?.setColorFilter(color)
    }

    fun setIconImage(@DrawableRes idRes: Int? = null, @ColorRes colorTint: Int? = null) {
        idRes?.let {
            this.iconView?.setImageResource(it)
        }
        colorTint?.let {
            this.iconView?.setColorFilter(ContextCompat.getColor(this.context, it))
        }
    }

}