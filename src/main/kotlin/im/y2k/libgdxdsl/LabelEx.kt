package im.y2k.libgdxdsl

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label

class LabelEx(private val font: BitmapFont) {

    var maxLines: Int
        get() = throw UnsupportedOperationException()
        set(value) {
            if (value != 1) throw UnsupportedOperationException()
            label.setWrap(false)
            label.setEllipsis(true)
        }

    var color: Color
        get() = throw UnsupportedOperationException()
        set(value) {
            style.fontColor = value
        }

    var background: Color
        get() = throw UnsupportedOperationException()
        set(value) {
            border.color = value
        }

    var padding: Number
        get() = throw UnsupportedOperationException()
        set(value) {
            border.padding = value.toFloat()
        }

    fun setSize(width: Int, height: Int) {
        label.setSize(width.toFloat(), height.toFloat())
    }

    var text: String
        get() = TODO("not implemented")
        set(value) {
            label.setText(value)
        }

    internal val border: Border
    private val label: Label
    private val style: Label.LabelStyle

    init {
        style = Label.LabelStyle(font, Color.BLACK)
        label = Label(null, style)
        label.setWrap(true)

        border = Border()
        border.addActor(label)
    }
}
