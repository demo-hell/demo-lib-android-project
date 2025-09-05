package br.com.cielo.libflue.actionslist

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import br.com.cielo.libflue.R
import kotlinx.android.synthetic.main.layout_base_action_list_view.view.*

class CieloTextArrowActionListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CieloActionListView(context, attrs, defStyleAttr) {

    private var arrowImageView: ImageView? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        injectArrow()
    }

    private fun injectArrow() {
        val size = this.context.resources.getDimension(R.dimen.dimen_24dp).toInt()
        this.arrowImageView = ImageView(this.context)
        this.arrowImageView?.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_chevron_right))
        this.arrowImageView?.layoutParams = LinearLayout.LayoutParams(size, size)
        this.llSufix?.addView(arrowImageView)
        this.llSufix?.requestLayout()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            val color = ContextCompat.getColor(this.context, when(it.action) {
                MotionEvent.ACTION_DOWN -> R.color.brand_400
                else -> if (this.isEnabled) R.color.display_400 else android.R.color.white
            })
            this.arrowImageView?.setColorFilter(color)
        }
        return super.onTouchEvent(event)
    }
}