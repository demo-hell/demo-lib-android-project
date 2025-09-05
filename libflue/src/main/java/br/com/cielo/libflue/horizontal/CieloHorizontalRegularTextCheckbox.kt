package br.com.cielo.libflue.horizontal

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.*
import androidx.core.content.ContextCompat
import br.com.cielo.libflue.R
import kotlinx.android.synthetic.main.layout_base_horizontal_regular_text_single_view.view.*

class CieloHorizontalRegularTextCheckbox @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), Checkable {

    private var text: String = ""
    private var checked: Boolean = false
    private var ivCheckbox: ImageView? = null
    private var onCheckedChangeListener: OnCheckedChangeListener? = null

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CieloHorizontalRegularTextCheckbox,
            0,
            0
        ).apply {
            try {
                getString(R.styleable.CieloHorizontalRegularTextCheckbox_android_text)?.let {
                    this@CieloHorizontalRegularTextCheckbox.text = it
                }
                getBoolean(R.styleable.CieloHorizontalRegularTextCheckbox_android_enabled, true).let {
                    isEnabled = it
                }
                getBoolean(R.styleable.CieloHorizontalRegularTextCheckbox_android_checked, false).let {
                    checked = it
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
        configureViews()
    }

    override fun setChecked(checked: Boolean) {
        this.checked = checked
        configureViews()
    }

    override fun isChecked(): Boolean {
        return this.checked;
    }

    override fun toggle() {
        this.checked = !this.checked
        configureViews()
        this.onCheckedChangeListener?.onCheckedChanged(this, this.checked)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            if (it.action == MotionEvent.ACTION_DOWN) {
                toggle()
            }
        }
        return super.onTouchEvent(event)
    }

    private fun verifyImageView() {
        if (this.ivCheckbox == null) {
            this.ivCheckbox = ImageView(this.context)
            injectImage()
        }
    }

    private fun injectImage() {
        val size = this.context.resources.getDimension(R.dimen.dimen_24dp).toInt()
        this.ivCheckbox?.setImageDrawable(
            ContextCompat.getDrawable(
                this.context,
                R.drawable.ic_checkbutton
            )
        )
        this.ivCheckbox?.layoutParams = LinearLayout.LayoutParams(size, size)
        this.llSufix?.addView(ivCheckbox)
        this.llSufix?.requestLayout()
    }

    private fun configureViews() {
        verifyImageView()
        this.tvText?.text = this.text
        when(this.checked) {
            true -> {
                this.tvText?.setTextColor(ContextCompat.getColor(this.context, R.color.brand_400))
                this.llContent?.setBackgroundResource(R.drawable.shape_white_outline_blue)
                this.ivCheckbox?.setImageDrawable(
                    ContextCompat.getDrawable(
                        this.context,
                        R.drawable.ic_checkbutton_selected
                    )
                )
            }
            else -> {
                this.tvText?.setTextColor(ContextCompat.getColor(this.context, R.color.display_400))
                this.llContent?.setBackgroundResource(R.drawable.shape_white_outline_display_200)
                this.ivCheckbox?.setImageDrawable(
                    ContextCompat.getDrawable(
                        this.context,
                        R.drawable.ic_checkbutton
                    )
                )
            }
        }
    }

    /**
     * Register a callback to be invoked when the checked state of this button
     * changes.
     *
     * @param listener the callback to call on checked state change
     */
    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener?) {
        this.onCheckedChangeListener = listener
    }

    interface OnCheckedChangeListener {
        /**
         * Called when the checked state of a compound button has changed.
         *
         * @param buttonView The compound button view whose state has changed.
         * @param isChecked  The new checked state of buttonView.
         */
        fun onCheckedChanged(view: CieloHorizontalRegularTextCheckbox?, isChecked: Boolean)
    }
}