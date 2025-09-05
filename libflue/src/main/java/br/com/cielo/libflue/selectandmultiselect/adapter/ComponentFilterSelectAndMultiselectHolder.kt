package br.com.cielo.libflue.selectandmultiselect.adapter

import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.cielo.libflue.R
import br.com.cielo.libflue.selectandmultiselect.model.ComponentFilterSelectAndMultiselectModel
import kotlinx.android.synthetic.main.item_component_select_multiselect.view.*

class ComponentFilterSelectAndMultiselectHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(
        list: MutableList<ComponentFilterSelectAndMultiselectModel>, position: Int,
        recyclerView: RecyclerView, isMultiselect: Boolean,
        listener: ComponentFilterSelectAndMultiselectListener,
        selectItens: MutableList<ComponentFilterSelectAndMultiselectModel>,
        orientation: Int
    ) {

        val item = list[position]
        itemView.textViewItem.text = item.label

        if (orientation == LinearLayoutManager.HORIZONTAL) {
            itemView.layoutParams.width = FrameLayout.LayoutParams.WRAP_CONTENT
        }

        if (item.isChecked) {
            setSelectState(itemView.textViewItem, selectItens, item)
            listener.getSelectedItens(selectItens)
        }

        if (isMultiselect) {
            itemView.setOnClickListener {
                item.isChecked = item.isChecked.not()
                if (item.isChecked) {
                    setSelectState(itemView.textViewItem, selectItens, item)
                } else {
                    setUnselectState(itemView.textViewItem, selectItens, item)
                }
                listener.getSelectedItens(selectItens)
            }
        } else {
            itemView.setOnClickListener { view ->
                list.forEachIndexed { index, it ->
                    if (index == position && it.isChecked.not()) {
                        it.isChecked = true
                        setSelectState(itemView.textViewItem, selectItens, it)
                    } else if (index == position && it.isChecked) {
                        it.isChecked = false
                        setUnselectState(
                            getHolderItem(recyclerView, index).itemView.textViewItem,
                            selectItens,
                            it
                        )
                    } else if (index != position) {
                        it.isChecked = false
                        setUnselectState(
                            getHolderItem(recyclerView, index).itemView.textViewItem,
                            selectItens,
                            it
                        )
                    }
                }
                listener.getSelectedItens(selectItens)
            }
        }
    }

    private fun setSelectState(
        view: AppCompatTextView,
        selectItens: MutableList<ComponentFilterSelectAndMultiselectModel>,
        itemAdd: ComponentFilterSelectAndMultiselectModel
    ) {
        view.setTextColor(
            ContextCompat.getColor(itemView.context, R.color.display_0)
        )
        view.setBackgroundResource(
            R.drawable.cielo_blue_button_view_state_selected_corner200
        )
        selectItens.add(itemAdd)
    }

    private fun setUnselectState(
        view: AppCompatTextView,
        selectItens: MutableList<ComponentFilterSelectAndMultiselectModel>,
        removeItem: ComponentFilterSelectAndMultiselectModel
    ) {
        view.setTextColor(
            ContextCompat.getColor(itemView.context, R.color.color_5A646E)
        )
        view.setBackgroundResource(
            R.drawable.cielo_blue_button_stroke_view_unselected_state
        )
        selectItens.remove(removeItem)
    }

    private fun getHolderItem(recyclerView: RecyclerView, index: Int) =
        recyclerView.getChildViewHolder(
            recyclerView.getChildAt(index)
        ) as ComponentFilterSelectAndMultiselectHolder
}