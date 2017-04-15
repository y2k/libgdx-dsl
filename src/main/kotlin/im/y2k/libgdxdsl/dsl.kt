package im.y2k.libgdxdsl

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable

fun GroupEx.wrapPanel(init: GroupEx.() -> Unit) {
    val panel = WrapPanel()
    val groupEx = GroupEx(panel)
    add(panel, groupEx)
    groupEx.init()
}

fun GroupEx.edit(init: EditEx.() -> Unit) {
    val wrapper = EditEx()
    add(wrapper.widget, null)
    wrapper.init()
}

class EditEx {

    var textChanged: (String) -> Unit
        get() = throw UnsupportedOperationException()
        set(value) {
            widget.setTextFieldListener { _, _ ->
                value("" + widget.text)
            }
        }

    var hint: String
        get() = throw UnsupportedOperationException()
        set(value) {
            widget.messageText = value
        }

    val widget = TextField(null,
        TextField.TextFieldStyle().apply {
            fontColor = Color.BLACK
            font = BitmapFont(Gdx.files.internal("font.fnt"))
        })
}

fun GroupEx.stackV(init: GroupEx.() -> Unit) {
    val stack = FillLinearLayout()
    val groupEx = GroupEx(stack)
    add(stack, groupEx)
    groupEx.init()
}

fun GroupEx.scrollV(init: GroupEx.() -> Unit): GroupEx {
    val stack = LinearLayout()

    val scrollPanel = ScrollPane(stack)
    scrollPanel.setScrollingDisabled(true, false)

    val groupEx = GroupEx(stack)
    add(scrollPanel, groupEx)

    groupEx.init()
    return groupEx
}

fun GroupEx.stackH(init: GroupEx.() -> Unit): GroupEx {
    val stack = HorizontalFillLinearLayout()
    val groupEx = GroupEx(stack)
    add(stack, groupEx)
    groupEx.init()
    return groupEx
}

fun GroupEx.space(init: Space.() -> Unit) {
    val wrapper = Space()
    add(wrapper.widget, null)
    wrapper.init()
}

fun GroupEx.image(init: ImageEx.() -> Unit) {
    val w = ImageEx()
    add(w.widget, null)
    w.init()
}

class ImageEx {

    var size: Vector2
        get() = throw UnsupportedOperationException()
        set(value) {
            widget.requestSize = value
        }

    var src: ImageWrapper?
        get() = throw UnsupportedOperationException()
        set(value) {
            widget.src = value
        }

    val widget = WebImageWidget { _, _, _ -> Unit }
}

fun GroupEx.label(font: BitmapFont, init: LabelEx.() -> Unit) {
    val view = LabelEx(font)
    add(view.border, null)
    view.init()
}

fun GroupEx.button(font: BitmapFont, init: ButtonEx.() -> Unit) {
    val wrapper = ButtonEx(font)
    add(wrapper.widget, null)
    wrapper.init()
}

class ButtonEx(font: BitmapFont) {

    var text: String
        get() = throw UnsupportedOperationException()
        set(value) {
            widget.setText(value)
        }

    fun click(listener: () -> Unit) {
        widget.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                listener()
            }
        })
    }

    val widget = TextButton(null, TextButton.TextButtonStyle(
        NinePatchDrawable(NinePatch(Texture("apptheme_btn_default_normal_holo_light.9.png"), 12, 12, 16, 14)),
        NinePatchDrawable(NinePatch(Texture("apptheme_btn_default_pressed_holo_light.9.png"), 12, 12, 16, 14)),
        null,
        font
    ))
}

class LayoutPair(
    val width: LayoutParam = LayoutParam.wrap,
    val height: LayoutParam = LayoutParam.wrap)

enum class LayoutParam { fill, wrap }
