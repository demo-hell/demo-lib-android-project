package br.com.cielo.libflue.selectandmultiselect.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.cielo.libflue.R
import br.com.cielo.libflue.selectandmultiselect.model.ComponentFilterSelectAndMultiselectModel

class ComponentFilterSelectAndMultiselectAdapter(
    private val list: MutableList<ComponentFilterSelectAndMultiselectModel>,
    private val recyclerView: RecyclerView,
    private val isMultiselect: Boolean,
    private val orientation: Int,
    private val listener: ComponentFilterSelectAndMultiselectListener
) : RecyclerView.Adapter<ComponentFilterSelectAndMultiselectHolder>() {

    private val selectItens: MutableList<ComponentFilterSelectAndMultiselectModel> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ComponentFilterSelectAndMultiselectHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_component_select_multiselect, parent, false)
    )


    override fun onBindViewHolder(
        holder: ComponentFilterSelectAndMultiselectHolder,
        position: Int
    ) {
        holder.bind(list, position, recyclerView, isMultiselect, listener, selectItens, orientation)
    }

    override fun getItemCount() = list.size
}

interface ComponentFilterSelectAndMultiselectListener {
    fun getSelectedItens(selectedItens: MutableList<ComponentFilterSelectAndMultiselectModel>)
}