package im.y2k.libgdxdsl

import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import com.badlogic.gdx.scenes.scene2d.utils.Layout

/**
 * Created by y2k on 06/12/2016.
 **/
class WrapPanel : WidgetGroup() {

    private var _height = 0f

    override fun layout() {
        var x = 0f
        var y = 0f
        var maxChildHeight = 0f

        for (child in children) {
            if (child !is Layout) throw Exception()

            child.validate()

            if (x + child.prefWidth <= width) {
                child.setBounds(x, y, child.prefWidth, child.prefHeight)
                x += child.prefWidth
                maxChildHeight = Math.max(maxChildHeight, child.prefHeight)
            } else {
                y += maxChildHeight
                child.setBounds(0f, y, child.prefWidth, child.prefHeight)
                x = child.prefWidth
                maxChildHeight = child.prefHeight
            }
        }

        _height = y + maxChildHeight
    }

    override fun getPrefWidth(): Float = width
    override fun getPrefHeight(): Float = _height
}