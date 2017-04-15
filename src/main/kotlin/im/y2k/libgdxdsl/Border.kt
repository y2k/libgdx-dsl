package im.y2k.libgdxdsl

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import com.badlogic.gdx.scenes.scene2d.utils.Layout

/**
 * Created by y2k on 27/11/2016.
 **/
class Border : WidgetGroup() {

    var padding by listenable(0f, { invalidateHierarchy() })

    private var _prefHeight = 0f
    private val texture = createFillTexture(4, 4, Color.WHITE)

    override fun layout() {
        val child = children.firstOrNull() ?: return
        if (child !is Layout) throw IllegalArgumentException()

        child.invalidate()
        child.validate()
        child.setPosition(padding, padding)
        child.setSize(width - 2 * padding, child.prefHeight)

        _prefHeight = child.prefHeight + 2 * padding
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.color = color
        batch.draw(texture, x, y, width, height)
        super.draw(batch, parentAlpha)
    }

    override fun getPrefWidth(): Float = width
    override fun getPrefHeight(): Float = _prefHeight
}