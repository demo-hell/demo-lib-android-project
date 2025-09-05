package br.com.cielo.libflue.alert

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import br.com.cielo.libflue.R
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.FragmentManager


class CieloAlertDialogFragment private constructor(builder: Builder) : DialogFragment() {

    var title: String? = null
    var message: String? = null
    var closeTextButton: String? = null
    var onCloseButtonClickListener: View.OnClickListener? = null

    init {
        this.title = builder.title
        this.message = builder.message
        this.closeTextButton = builder.closeTextButton
        this.onCloseButtonClickListener = builder.onCloseButtonClickListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var view: View = requireActivity().layoutInflater.inflate(R.layout.layout_cielo_alert_dialog_fragment, null)
        configureViews(view)
        var alert = AlertDialog.Builder(requireActivity())
        alert.setView(view)
        val dialog = alert.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    private fun configureViews(view: View) {
        view.findViewById<AppCompatTextView>(R.id.titleTextView)?.let {
            it.text = this.title
        }

        view.findViewById<AppCompatTextView>(R.id.messageTextView)?.let {
            it.text = this.message
        }

        view.findViewById<Button>(R.id.closeButtonView)?.let { itButton ->
            this.closeTextButton?.let { itText ->
                itButton.text = itText
            }
            itButton.setOnClickListener {
                this.onCloseButtonClickListener?.onClick(it)
                this.dismissAllowingStateLoss()
            }
        }
    }

    fun showAllowingStateLoss(fragmentManager: FragmentManager, tag: String) {
        val transaction = fragmentManager.beginTransaction()
        transaction.add(this, tag)
        transaction.commitAllowingStateLoss()
    }

    class Builder {
        var title: String? = null
            private set

        var message: String? = null
            private set

        var closeTextButton: String? = null
            private set

        var onCloseButtonClickListener: View.OnClickListener? = null
            private set

        var onPositiveButtonClickListener: View.OnClickListener? = null
            private set

        fun title(text: String) = apply { this.title = text }
        fun message(text: String) = apply { this.message = text }
        fun closeTextButton(text: String) = apply { this.closeTextButton = text }
        fun onCloseButtonClickListener(listener: View.OnClickListener) = apply { this.onCloseButtonClickListener = listener }
        fun build() = CieloAlertDialogFragment(this)
    }
}