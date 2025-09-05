package br.com.cielo.libflue.alert

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import br.com.cielo.libflue.R

class CieloAskQuestionDialogFragment private constructor(builder: Builder) : DialogFragment()  {

    var title: String? = null
    var message: String? = null
    var cancelTextButton: String? = null
    var positiveTextButton: String? = null
    var onCancelButtonClickListener: View.OnClickListener? = null
    var onPositiveButtonClickListener: View.OnClickListener? = null

    init {
        this.title = builder.title
        this.message = builder.message
        this.cancelTextButton = builder.cancelTextButton
        this.positiveTextButton = builder.positiveTextButton
        this.onCancelButtonClickListener = builder.onCancelButtonClickListener
        this.onPositiveButtonClickListener = builder.onPositiveButtonClickListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var view: View = requireActivity().layoutInflater.inflate(R.layout.layout_cielo_ask_question_dialog_fragment, null)
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

        view.findViewById<Button>(R.id.cancelButtonView)?.let { itButton ->
            this.cancelTextButton?.let { itText ->
                itButton.text = itText
            }

            itButton.setOnClickListener {
                this.onCancelButtonClickListener?.onClick(it)
                this.dismissAllowingStateLoss()
            }
        }

        view.findViewById<Button>(R.id.positiveButtonView)?.let { itButton ->
            this.positiveTextButton?.let { itText ->
                itButton.text = itText
            }

            itButton.setOnClickListener {
                this.onPositiveButtonClickListener?.onClick(it)
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

        var cancelTextButton: String? = null
            private set

        var positiveTextButton: String? = null
            private set

        var onCancelButtonClickListener: View.OnClickListener? = null
            private set

        var onPositiveButtonClickListener: View.OnClickListener? = null
            private set

        fun title(text: String) = apply { this.title = text }
        fun message(text: String) = apply { this.message = text }
        fun cancelTextButton(text: String) = apply { this.cancelTextButton = text }
        fun positiveTextButton(text: String) = apply { this.positiveTextButton = text }
        fun onCancelButtonClickListener(listener: View.OnClickListener) = apply { this.onCancelButtonClickListener = listener }
        fun onPositiveButtonClickListener(listener: View.OnClickListener) = apply { this.onPositiveButtonClickListener = listener }
        fun build() = CieloAskQuestionDialogFragment(this)
    }

}