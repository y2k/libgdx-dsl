package im.y2k.libgdxdsl

import com.badlogic.gdx.scenes.scene2d.ui.Widget

/**
 * Created by y2k on 02/12/2016.
 **/
class Space {

    var width: Number
        get() = throw UnsupportedOperationException()
        set(value) {
            widget.prefSize = Size(value.toFloat(), widget.prefHeight)
        }

    var height: Number
        get() = throw UnsupportedOperationException()
        set(value) {
            widget.prefSize = Size(widget.prefWidth, value.toFloat())
        }

    val widget = SpaceWidget()

    class SpaceWidget : Widget() {
        override fun getPrefWidth(): Float = prefSize.width
        override fun getPrefHeight(): Float = prefSize.height
        internal var prefSize = Size()
    }
}