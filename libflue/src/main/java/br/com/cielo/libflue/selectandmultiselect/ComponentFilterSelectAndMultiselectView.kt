package br.com.cielo.libflue.selectandmultiselect

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import br.com.cielo.libflue.R
import br.com.cielo.libflue.selectandmultiselect.adapter.ComponentFilterSelectAndMultiselectAdapter
import br.com.cielo.libflue.selectandmultiselect.adapter.ComponentFilterSelectAndMultiselectListener
import br.com.cielo.libflue.selectandmultiselect.model.ComponentFilterSelectAndMultiselectModel
import kotlinx.android.synthetic.main.layout_component_filter_select_multiselect_view.view.*

const val SPAN_COUNT = 2
const val ORIENTATION = LinearLayoutManager.VERTICAL

class ComponentFilterSelectAndMultiselectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private var spanCount: Int = SPAN_COUNT,
    private var orientation: Int = ORIENTATION,
    private var isMultiselect: Boolean = false
) : FrameLayout(context, attrs, defStyleAttr) {

    private var list = mutableListOf<ComponentFilterSelectAndMultiselectModel>()
    private var selectedItens: MutableList<ComponentFilterSelectAndMultiselectModel> = ArrayList()

    init {
        inflate(context, R.layout.layout_component_filter_select_multiselect_view, this)
        val attribute = context.obtainStyledAttributes(
            attrs,
            R.styleable.ComponentFilterSelectAndMultiselectView
        )

        isMultiselect = attribute.getBoolean(
            R.styleable.ComponentFilterSelectAndMultiselectView_isMultiselect,
            false
        )
        spanCount = attribute.getInt(
            R.styleable.ComponentFilterSelectAndMultiselectView_spanCount,
            SPAN_COUNT
        )
        orientation = attribute.getInt(
            R.styleable.ComponentFilterSelectAndMultiselectView_orientation,
            ORIENTATION
        )
        attribute.recycle()
    }

    fun setContent(list: MutableList<ComponentFilterSelectAndMultiselectModel>) {
        this.list = list
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val sglm = StaggeredGridLayoutManager(spanCount, orientation)
        recyclerView.layoutManager = sglm
        recyclerView.adapter =
            ComponentFilterSelectAndMultiselectAdapter(this.list, recyclerView, isMultiselect,
                orientation, object : ComponentFilterSelectAndMultiselectListener {
                    override fun getSelectedItens(selectedItens: MutableList<ComponentFilterSelectAndMultiselectModel>) {
                        this@ComponentFilterSelectAndMultiselectView.selectedItens = selectedItens
                    }
                })
    }

    fun getSelectedItems() = selectedItens
}