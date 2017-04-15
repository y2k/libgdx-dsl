package im.y2k.libgdxdsl

import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import com.badlogic.gdx.scenes.scene2d.utils.Layout
import com.badlogic.gdx.utils.IntMap

/**
 * Created by y2k on 10/11/2016.
 **/
class LinearLayout : WidgetGroup() {

    private var _prefHeight = 0f

    override fun layout() {
        _prefHeight = 0f
        for (child in children) {
            if (child !is Layout) throw IllegalArgumentException()

            child.width = this.width
            child.height = this.height

            child.invalidate()
            child.validate()

            child.setPosition(0f, height - _prefHeight - child.prefHeight)
            child.setSize(width, child.prefHeight)
            _prefHeight += child.prefHeight
        }
    }

    override fun getPrefWidth(): Float = width
    override fun getPrefHeight(): Float = _prefHeight
}

class FillLinearLayout : WidgetGroup() {

    private val DEFAULT_LAYOUT_PARAMS = LayoutPair(width = LayoutParam.fill)

    val rules = IntMap<LayoutPair>()

    private var wrapHeight = 0f

    override fun layout() {
        wrapHeight = 0f
        var fillCount = 0

        for (index in 0..children.size - 1) {
            val child = children[index]
            if (child !is Layout) throw IllegalArgumentException()

            child.width = this.width
            child.height = this.height

            child.invalidate()
            child.validate()

            val r = getLayout(index).height
            if (r == LayoutParam.wrap) wrapHeight += child.prefHeight
            else fillCount++
        }

        var offset = 0f
        for (index in 0..children.size - 1) {
            val child = children[index]
            if (child !is Layout) throw IllegalArgumentException()

            val w = when (getLayout(index).width) {
                LayoutParam.wrap -> child.prefWidth
                LayoutParam.fill -> width
            }
            val h = when (getLayout(index).height) {
                LayoutParam.wrap -> child.prefHeight
                LayoutParam.fill -> (height - wrapHeight) / fillCount
            }

            child.setPosition(0f, height - offset - h)
            child.setSize(w, h)
            offset += h
        }
    }

    private fun getLayout(index: Int) = rules.get(index, DEFAULT_LAYOUT_PARAMS)

    override fun getPrefWidth(): Float = width
    override fun getPrefHeight(): Float = if (hasFillChild()) height else wrapHeight

    private fun hasFillChild() = rules.any { it.value.height == LayoutParam.fill }
}

class HorizontalFillLinearLayout : WidgetGroup() {

    private val DEFAULT_LAYOUT_PARAMS = LayoutPair()

    val rules = IntMap<LayoutPair>()

    private var maxChildHeight = 0f

    override fun layout() {
        maxChildHeight = 0f
        var wrapWidth = 0f
        var fillCount = 0

        for (index in 0..children.size - 1) {
            val child = children[index]
            if (child !is Layout) throw IllegalArgumentException()

            child.width = this.width
            child.height = this.height

            child.invalidate()
            child.validate()

            maxChildHeight = Math.max(maxChildHeight, child.prefHeight)

            val r = getLayoutParams(index)
            if (r.width == LayoutParam.wrap) wrapWidth += child.prefWidth
            else fillCount++
        }

        var offset = 0f
        for (index in 0..children.size - 1) {
            val child = children[index]
            if (child !is Layout) throw IllegalArgumentException()

            val w = when (getLayoutParams(index).width) {
                LayoutParam.wrap -> child.prefWidth
                LayoutParam.fill -> (width - wrapWidth) / fillCount
            }

            child.setPosition(offset, 0f)
            child.setSize(w, child.prefHeight)
            offset += w
        }
    }

    private fun getLayoutParams(index: Int) = rules.get(index, DEFAULT_LAYOUT_PARAMS)

    override fun getPrefWidth(): Float = width
    override fun getPrefHeight(): Float = maxChildHeight
}