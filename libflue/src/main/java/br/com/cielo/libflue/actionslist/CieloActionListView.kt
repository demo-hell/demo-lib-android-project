package br.com.cielo.libflue.actionslist

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import br.com.cielo.libflue.R
import kotlinx.android.synthetic.main.layout_base_action_list_view.view.*

open class CieloActionListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var text: String = ""
    private var tvTextColorBackup: Int = 0
    private var dividerViewColorBackup: Int = 0;
    private var isShowDivider: Boolean = true

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CieloActionListView,
            0,
            0
        ).apply {
            try {
                getString(R.styleable.CieloActionListView_android_text)?.let {
                    this@CieloActionListView.text = it
                }
                getBoolean(R.styleable.CieloActionListView_android_enabled, true).let {
                    isEnabled = it
                }
                getBoolean(R.styleable.CieloActionListView_showDivider, true).let {
                    isShowDivider = it
                }
            } finally {
                recycle()
            }
        }

        isClickable = true

        LayoutInflater
            .from(context)
            .inflate(R.layout.layout_base_action_list_view, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        this.tvText?.text = this.text
        backupColors()
        configureViews()
        configureListeners()
    }

    private fun backupColors() {
        this.tvText?.currentTextColor?.let { itColor ->
            this.tvTextColorBackup = itColor
        }
        this.dividerView?.background?.let { itBackground ->
            if (itBackground is ColorDrawable) {
                this.dividerViewColorBackup = itBackground.color
            }
        }
    }

    private fun configureViews() {
        if (!this.isShowDivider) {
            this.dividerView?.visibility = View.GONE
        }
    }

    private fun configureListeners() {
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            this.tvText?.setTextColor(when(it.action) {
                MotionEvent.ACTION_DOWN -> ContextCompat.getColor(this.context, R.color.brand_400)
                else -> if (this.isEnabled) this.tvTextColorBackup else android.R.color.white
            })
            this.dividerView?.setBackgroundColor(when(it.action) {
                MotionEvent.ACTION_DOWN -> ContextCompat.getColor(this.context, R.color.brand_400)
                else -> if (this.isEnabled) this.dividerViewColorBackup else android.R.color.white
            })
        }
        return super.onTouchEvent(event)
    }

    fun showDivider(isShow: Boolean) {
        this.isShowDivider = isShow
        this.dividerView?.visibility = if (isShow) View.VISIBLE else View.GONE
    }

}