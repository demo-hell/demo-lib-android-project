package br.com.cielo.testelibflue

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.cielo.libflue.selectandmultiselect.ComponentFilterSelectAndMultiselectView
import br.com.cielo.libflue.selectandmultiselect.model.ComponentFilterSelectAndMultiselectModel
import kotlinx.android.synthetic.main.activity_test_component_filter_select_and_multiselect.*

class TestComponentFilterSelectAndMultiselectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_component_filter_select_and_multiselect)

        val list = arrayOf(
            ComponentFilterSelectAndMultiselectModel("Efetivada"),
            ComponentFilterSelectAndMultiselectModel("Enviada para registro"),
            ComponentFilterSelectAndMultiselectModel("Rejeitada"),
            ComponentFilterSelectAndMultiselectModel("Cancelamento solicitado"),
            ComponentFilterSelectAndMultiselectModel("Cancelamento efetivado"),
            ComponentFilterSelectAndMultiselectModel("Erro registro"),
            ComponentFilterSelectAndMultiselectModel("Cancelamento não efetivado"),
            ComponentFilterSelectAndMultiselectModel("Pendente"),
            ComponentFilterSelectAndMultiselectModel("Em processamento"),
            ComponentFilterSelectAndMultiselectModel("Pagamento efetivado")
        )

        list.forEach {
           // if (it.label == "Pagamento efetivado") it.isChecked = true
        }
        componentSelect.setContent(list.toMutableList())

        checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
           // componentSelect.isMultiselect = isChecked
        }
        button.setOnClickListener {
            componentSelect.getSelectedItems().forEach { Log.i("ComponentAndMultiselect", it.label) }
        }

    }
}