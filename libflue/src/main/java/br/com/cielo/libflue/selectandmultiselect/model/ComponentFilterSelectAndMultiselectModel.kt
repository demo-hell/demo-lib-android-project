package br.com.cielo.libflue.selectandmultiselect.model

import android.widget.Checkable

class ComponentFilterSelectAndMultiselectModel(val label: String, private var isChecked: Boolean = false)
    : Checkable{
    override fun setChecked(checked: Boolean) {
        isChecked = checked
    }

    override fun isChecked() = isChecked

    override fun toggle() {
        isChecked.not()
    }
}