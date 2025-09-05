package br.com.cielo.libflue.button

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.updateLayoutParams
import br.com.cielo.libflue.R
import kotlinx.android.synthetic.main.layout_cielo_regular_text_outline_icon.view.*
import kotlinx.android.synthetic.main.layout_cielo_regular_text_outline_icon.view.iconView
import kotlinx.android.synthetic.main.layout_cielo_regular_text_outline_icon.view.textView
import kotlinx.android.synthetic.main.layout_cielo_regular_text_outline_select_radio_button.view.*

class CieloRegularTextOutlineSelectRadioButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CieloBaseRadioButton(context, attrs, defStyleAttr) {

    private var customLayout: View? = null

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CieloBaseRadioButton,
            0,
            0
        ).apply {
            try {
                getString(R.styleable.CieloBaseRadioButton_android_text)?.let {
                    this@CieloRegularTextOutlineSelectRadioButton.text = it
                }
                getBoolean(R.styleable.CieloBaseRadioButton_android_enabled, true).let {
                    isEnabled = it
                }
            } finally {
                recycle()
            }
        }

        isClickable = true
        setBackgroundResource(R.drawable.text_outline_rounded_shape_color_white)

        LayoutInflater
            .from(context)
            .inflate(R.layout.layout_cielo_regular_text_outline_select_radio_button, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        this.textView?.text = this.text
    }

    override fun setChecked(checked: Boolean) {
        this.isCheckedValue = checked
        if (checked) {
            setBackgroundResource(R.drawable.shape_white_outline_blue)
            this.textView?.setTextColor(ContextCompat.getColor(this.context, R.color.cielo_400))
            this.iconView?.setImageResource(R.drawable.ic_radio_button_selected)
        }
        else {
            setBackgroundResource(R.drawable.text_outline_rounded_shape_color_white)
            this.textView?.setTextColor(ContextCompat.getColor(this.context, R.color.display_400))
            this.iconView?.setImageResource(R.drawable.ic_radio_button_unselect)
        }
    }

    override fun setTextValue(text: String) {
        this.text = text
        this.textView?.text = text
    }

    fun setLayout(layout: ViewGroup) {
        this.customLayout = layout
        this.bodyLayoutView?.removeAllViews()
        this.bodyLayoutView?.addView(layout)
        this.bodyLayoutView?.requestLayout()
    }

    fun setLayout(@LayoutRes layoutResId: Int) {
        this.bodyLayoutView?.removeAllViews()
        LayoutInflater
            .from(context)
            .inflate(layoutResId, this.bodyLayoutView, false)?.let {
                this.customLayout = it
                this.bodyLayoutView?.addView(it)
            }
    }

    fun getLayout() = this.customLayout

    fun doCentralizeSelectImage() {
        (this.iconView?.layoutParams as LinearLayout.LayoutParams).apply {
            this.topMargin = 0
            this.gravity = Gravity.CENTER_VERTICAL
        }
        this.iconView?.invalidate()
    }

}