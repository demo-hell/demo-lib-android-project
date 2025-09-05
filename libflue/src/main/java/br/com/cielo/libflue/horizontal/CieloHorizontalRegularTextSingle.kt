package br.com.cielo.libflue.horizontal

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import br.com.cielo.libflue.R
import kotlinx.android.synthetic.main.layout_base_horizontal_regular_text_single_view.view.*

open class CieloHorizontalRegularTextSingle @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var text: String = ""
    private var tvTextColorBackup: Int = 0
    private var dividerViewColorBackup: Int = 0;

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CieloHorizontalRegularTextSingle,
            0,
            0
        ).apply {
            try {
                getString(R.styleable.CieloHorizontalRegularTextSingle_android_text)?.let {
                    this@CieloHorizontalRegularTextSingle.text = it
                }
                getBoolean(R.styleable.CieloHorizontalRegularTextSingle_android_enabled, true).let {
                    isEnabled = it
                }
            } finally {
                recycle()
            }
        }

        isClickable = true

        LayoutInflater
            .from(context)
            .inflate(R.layout.layout_base_horizontal_regular_text_single_view, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        backupColors()
        configureViews()
        configureListeners()
    }

    private fun backupColors() {
        this.tvText?.currentTextColor?.let { itColor ->
            this.tvTextColorBackup = itColor
        }
    }

    private fun configureViews() {
        this.tvText?.text = this.text
    }

    private fun configureListeners() {
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when(it.action) {
                MotionEvent.ACTION_DOWN -> {
                    this.tvText?.setTextColor(ContextCompat.getColor(this.context, R.color.brand_400))
                    this.llContent?.setBackgroundResource(R.drawable.shape_white_outline_blue)
                }
                else -> {
                    this.tvText?.setTextColor(if (this.isEnabled) this.tvTextColorBackup else ContextCompat.getColor(this.context, android.R.color.white))
                    this.llContent?.setBackgroundResource(R.drawable.shape_white_outline_display_200)
                }
            }
        }
        return super.onTouchEvent(event)
    }

}