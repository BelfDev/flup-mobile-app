package com.br.flup.app.authentication.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.br.flup.app.R
import kotlinx.android.synthetic.main.auth_form_view.view.*

class AuthFormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.auth_form_view, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it,
                R.styleable.auth_form_view_attributes, 0, 0
            )


            val title = typedArray.getString(R.styleable.auth_form_view_attributes_custom_title)

            testText.text = title

            typedArray.recycle()
        }

    }
}
