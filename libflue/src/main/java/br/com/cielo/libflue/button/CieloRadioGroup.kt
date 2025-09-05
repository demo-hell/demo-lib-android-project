package br.com.cielo.libflue.button

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children


class CieloRadioGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var listener: RadioButtonListener? = null
    private var buttons = ArrayList<Checkable>()

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        addChildView(child, index, params)
        super.addView(child, index, params)
    }

    private fun addChildView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        child?.let {itChild ->
            if (itChild is Checkable) {
                configureChild(itChild, index, params)
            }
            else {
                if (itChild is ViewGroup) {
                    for(i in 0 until itChild.childCount) {
                        val view = itChild.getChildAt(i)
                        addChildView(view, index, params)
                    }
                }
            }
        }
    }

    private fun configureChild(child: View, index: Int, params: ViewGroup.LayoutParams?) {
        if (child is Checkable) {
            buttons.add(child)
            child.setOnClickListener {
                setAllButtonsToUnselectedState()
                child.isChecked = true
                this.listener?.onItemSelected(child as CieloBaseRadioButton)
            }
        }
    }

    private fun setAllButtonsToUnselectedState() {
//        val container: ConstraintLayout = this
//        for (i in 0 until container.childCount) {
//            val child = container.getChildAt(i)
//            if (child is CieloBaseRadioButton) {
//                child.isChecked = false
//            }
//        }
        buttons.forEach {
            it.isChecked = false
        }
    }

    fun getSelectedItemIndex(): Int? {
        return this.children.map { it as CieloBaseRadioButton }.indexOfFirst { it.isChecked }
    }

    fun getItemSelectedValue() : String? {
        val container: ConstraintLayout = this
        for (i in 0 until container.childCount) {
            val child = container.getChildAt(i)
            if (child is CieloBaseRadioButton) {
                if (child.isChecked) {
                    return child.getTextValue()
                }
            }
        }
        return null
    }

    fun setRadioButtonListener(listener: RadioButtonListener) {
        this.listener = listener
    }

    interface RadioButtonListener {
        fun onItemSelected(button: CieloBaseRadioButton)
    }
}