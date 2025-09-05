package br.com.cielo.libflue.inputtext

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import br.com.cielo.libflue.R
import kotlinx.android.synthetic.main.layout_cielo_text_input_view.view.*


open class CieloTextInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var hint: String = ""
    private var text: String = ""
    private var maxLines: Int = -1
    private var imeOptions: Int = -1
    private var maxlength: Int = -1
    private var endIconDrawable: Drawable? = null

    private var inputType: Int = -1

    private var errorMessage: String? = null
    private var onTextChangeListener: TextChangeListener? = null

    private var onEndIconClickListener: View.OnClickListener? = null

    private var onTextViewFocusChangeListener: OnFocusChangeListener? = null

    var disableCopyToClipboard = false
        set(value) {
            field = value
            if (field) disableCopyToClipboard()
        }

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        LayoutInflater
            .from(context)
            .inflate(R.layout.layout_cielo_text_input_view, this, true)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CieloTextInputView,
            0,
            0
        ).apply {
            try {
                getString(R.styleable.CieloTextInputView_hint)?.let {
                    this@CieloTextInputView.hint = it
                }
                getString(R.styleable.CieloTextInputView_android_text)?.let {
                    this@CieloTextInputView.text = it
                }
                getInt(R.styleable.CieloTextInputView_android_maxLines, -1).let {
                    this@CieloTextInputView.maxLines = it
                }
                getInt(R.styleable.CieloTextInputView_android_inputType, -1).let {
                    this@CieloTextInputView.inputType = it
                }
                getInt(R.styleable.CieloTextInputView_android_imeOptions, -1).let {
                    this@CieloTextInputView.imeOptions = it
                }
                getInt(R.styleable.CieloTextInputView_android_maxLength, -1).let {
                    this@CieloTextInputView.maxlength = it
                }
                getBoolean(R.styleable.CieloTextInputView_disableCopyToClipboard, false).let {
                    this@CieloTextInputView.disableCopyToClipboard = it
                }
            } finally {
                recycle()
            }
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        configureListeners()

        this.hintTextView.text = this.hint
        this.textView.hint = this.hint

        if (this.maxLines != -1) {
            this.textView.maxLines = this.maxLines
        }

        if (this.inputType != -1) {
            this.textView.inputType = this.inputType
        }

        if (this.imeOptions != -1) {
            this.textView.imeOptions = this.imeOptions
        }

        if (this.endIconDrawable == null) {
            this.endIconImage?.visibility = View.GONE
        } else {
            this.endIconImage.setImageDrawable(this.endIconDrawable)
        }

        this.setMaxLength(this.maxlength)
        this.setText(this.text)

        changeBorderColor(this.textView.hasFocus())
    }

    private fun configureListeners() {
        this.textView?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                this@CieloTextInputView.onTextChangeListener?.afterTextChanged(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                this@CieloTextInputView.onTextChangeListener?.beforeTextChanged(
                    s,
                    start,
                    count,
                    after
                )
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    this@CieloTextInputView.text = ""
                } else {
                    this@CieloTextInputView.text = s.toString()
                }
                this@CieloTextInputView.onTextChangeListener?.onTextChanged(s, start, before, count)
            }
        })

        this.textView?.setOnFocusChangeListener { v, hasFocus ->
            changeViewStateByFocus(hasFocus)
            this.onTextViewFocusChangeListener?.onFocusChange(v, hasFocus)
        }

        this.endIconImage?.setOnClickListener {
            this.onEndIconClickListener?.onClick(it)
        }

        this.boxLayout?.setOnClickListener {
            this.textView?.requestFocus()
        }
    }

    private fun changeViewStateByFocus(hasFocus: Boolean) {
        if (this.textView.text.isNullOrEmpty()) {
            if (hasFocus) {
                moveHintTextToTop()
            } else {
                moveHintTextToOriginalPosition()
            }
        }
        changeBorderColor(hasFocus)
        changeHintColor(hasFocus)
    }

    private fun moveHintTextToTop() {
        if (this.hint.isNotEmpty()) {
            this.hintTextView.visibility = View.VISIBLE
            this.textView.hint = ""
            this.hintTextView.setTextColor(ContextCompat.getColor(this.context, R.color.cielo_400))
        }
    }

    private fun moveHintTextToOriginalPosition() {
        this.hintTextView.visibility = View.GONE
        this.textView.hint = this.hint
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        this.boxLayout?.isEnabled = enabled
        this.textView?.isEnabled = enabled
        changeBorderColor(this.textView.hasFocus())
    }

    fun setOnTextChangeListener(listener: TextChangeListener?) {
        this.onTextChangeListener = listener
    }

    fun setOnTextViewFocusChanged(listener: View.OnFocusChangeListener) {
        this.onTextViewFocusChangeListener = listener
    }

    fun setError(message: String?) {
        if (this.isEnabled) {
            this.errorMessage = message
            if (this.errorMessage.isNullOrEmpty()) {
                this.boxLayout?.background = ContextCompat.getDrawable(
                    this.context,
                    R.drawable.cielo_text_input_view_selector
                )
                this.messageTextView?.visibility = View.GONE
                this.hintTextView?.setTextColor(
                    ContextCompat.getColor(
                        this.context,
                        if (this.hintTextView.hasFocus()) R.color.cielo_400 else R.color.nublado_400
                    )
                )
            } else {
                this.messageTextView?.text = message
                this.messageTextView?.visibility = View.VISIBLE
                this.hintTextView.setTextColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.perigo_400
                    )
                )
                this.messageTextView?.setTextColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.perigo_400
                    )
                )
                this.boxLayout?.background = ContextCompat.getDrawable(
                    this.context,
                    R.drawable.outline_rounded_shape_color_perigo_400
                )
            }
        }
    }

    fun setHint(text: String) {
        this.hint = text
        this.hintTextView?.text = text
        if (this.textView?.text.isNullOrEmpty()) {
            this.textView?.hint = text
        }
        changeHintPosition(this.text)
        changeHintColor(this.textView.hasFocus())
    }

    fun getText() = this.text

    fun setText(text: String) {
        this.text = text
        this.textView.setText(text)
        changeHintPosition(text)
        changeHintColor(this.textView.hasFocus())
    }

    fun setSelection(length: Int) {
        this.textView?.setSelection(length)
    }

    fun setFilters(filters: Array<InputFilter>) {
        this.textView?.filters = filters
    }

    fun setOnEditorActionListener(listener: TextView.OnEditorActionListener?): kotlin.Unit {
        this.textView?.setOnEditorActionListener(listener)
    }

    fun setOnEndIconClickListener(listener: OnClickListener) {
        this.onEndIconClickListener = listener
    }

    fun setEndIconDrawable(icon: Drawable?) {
        this.endIconDrawable = icon
        if (this.endIconDrawable == null) {
            this.endIconImage?.visibility = View.GONE
        } else {
            this.endIconImage?.visibility = View.VISIBLE
            this.endIconImage?.setImageDrawable(this.endIconDrawable)
        }
    }

    fun setEndIconDrawable(@DrawableRes idRes: Int) {
        AppCompatResources.getDrawable(this.context, idRes)?.let { itDrawable ->
            setEndIconDrawable(itDrawable)
        }
    }

    fun setEndIconDrawable(@DrawableRes idDrawableRes: Int, @ColorRes idColorRes: Int) {
        AppCompatResources.getDrawable(this.context, idDrawableRes)?.let { itDrawable ->
            DrawableCompat.setTint(itDrawable, ContextCompat.getColor(this.context, idColorRes))
            setEndIconDrawable(itDrawable)
        }
    }

    open fun setInputType(type: Int) {
        this.inputType = type
        this.textView?.inputType = this.inputType
    }

    fun disableCopyToClipboard() {
        textView.setCustomSelectionActionModeCallback(object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?) = false
            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?) = false
            override fun onDestroyActionMode(mode: ActionMode?) {}
        })
    }

    fun setMaxLength(length: Int) {
        this.maxlength = length
        if (maxlength > 0) {
            this.setFilters(arrayOf(InputFilter.LengthFilter(maxlength)))
        } else {
            this.setFilters(arrayOf())
        }
    }

    private fun changeHintPosition(text: String) {
        if (text.trim().isEmpty()) {
            moveHintTextToOriginalPosition()
        } else {
            moveHintTextToTop()
        }
    }

    private fun changeHintColor(hasFocus: Boolean) {
        if (this.errorMessage.isNullOrEmpty()) {
            if (hasFocus) {
                //this.boxLayout?.background = ContextCompat.getDrawable(this.context, R.drawable.outline_rounded_shape_color_cielo_400)
                this.hintTextView.setTextColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.cielo_400
                    )
                )
            } else {
                //this.boxLayout?.background = ContextCompat.getDrawable(this.context, R.drawable.outline_rounded_shape_color_nublado_200)
                this.hintTextView.setTextColor(
                    ContextCompat.getColor(
                        this.context,
                        R.color.nublado_400
                    )
                )
            }
        } else {
            //this.boxLayout?.background = ContextCompat.getDrawable(this.context, R.drawable.outline_rounded_shape_color_nublado_200)
            this.hintTextView.setTextColor(ContextCompat.getColor(this.context, R.color.perigo_400))
        }
    }

    open fun changeBorderColor(hasFocus: Boolean) {
        if (this.isEnabled) {
            if (hasFocus) {
                changeBorder(R.drawable.outline_rounded_shape_color_cielo_400)
            } else if (this.errorMessage.isNullOrEmpty()) {
                changeBorder(R.drawable.cielo_text_input_view_selector)
            } else {
                changeBorder(R.drawable.outline_rounded_shape_color_perigo_400)
            }
        } else {
            setError(null)
            changeBorder(R.drawable.filled_rounded_shape_color_nublado_100)
        }
    }

    private fun changeBorder(@DrawableRes drawableId: Int) {
        this.boxLayout?.background = ContextCompat.getDrawable(this.context, drawableId)
    }

    protected fun goToEndText() {
        setSelection(this.text.length)
    }

    interface TextChangeListener {
        fun afterTextChanged(s: Editable?) {}
        fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
}