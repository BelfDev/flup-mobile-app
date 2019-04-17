package com.br.flup.app.authentication.ui

import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewOutlineProvider
import androidx.constraintlayout.widget.ConstraintLayout
import com.br.flup.app.R


class AuthFormView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {

    companion object {
        private const val DEFAULT_CORNER_RADIUS = 8
    }

    private var maskBitmap: Bitmap? = null
    private var paint: Paint? = null
    private var maskPaint: Paint? = null
    private var cornerRadius: Float = 0.toFloat()

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.auth_form_view, this, true)
        applyCustomAttributes(attrs)
        applyCornerRadius()
    }

    private fun applyCustomAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it,
                R.styleable.auth_form_view_attributes, 0, 0
            )

            cornerRadius =
                typedArray.getInteger(R.styleable.auth_form_view_attributes_corner_radius, DEFAULT_CORNER_RADIUS)
                    .toFloat()

            typedArray.recycle()
        }
    }

    private fun applyCornerRadius() {
        val metrics = context.resources.displayMetrics
        cornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cornerRadius, metrics)

        paint = Paint(Paint.ANTI_ALIAS_FLAG)

        maskPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        maskPaint?.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

        setWillNotDraw(false)

    }

    override fun draw(canvas: Canvas) {
        val offscreenBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val offscreenCanvas = Canvas(offscreenBitmap)

        super.draw(offscreenCanvas)

        if (maskBitmap == null) {
            maskBitmap = createMask(canvas.width, canvas.height)
        }

        offscreenCanvas.drawBitmap(maskBitmap, 0f, 0f, maskPaint)
        canvas.drawBitmap(offscreenBitmap, 0f, 0f, paint)
    }

    private fun createMask(width: Int, height: Int): Bitmap {
        val mask = Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)
        val canvas = Canvas(mask)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.WHITE

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        canvas.drawRoundRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), cornerRadius, cornerRadius, paint)

        return mask
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            outlineProvider = CustomOutline(w, h)
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private inner class CustomOutline internal constructor(internal var width: Int, internal var height: Int) :
        ViewOutlineProvider() {

        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(0, 0, width, height, cornerRadius)
        }
    }

}
