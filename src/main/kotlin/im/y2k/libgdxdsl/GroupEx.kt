package im.y2k.libgdxdsl

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.utils.Layout

class GroupEx(private val container: Group) {

    var parent: GroupEx? = null

    fun setView(init: GroupEx.() -> Unit) {
        container.clearChildren()
        this.init()
        (container.children[0] as Layout).setFillParent(true)
    }

    val width: Float get() = container.width

    fun add(view: Actor, groupEx: GroupEx?) {
        container.addActor(view)
        groupEx?.parent = this
    }

    fun layout(height: LayoutParam = LayoutParam.wrap,
               width: LayoutParam = LayoutParam.wrap) {
        when (container) {
            is FillLinearLayout ->
                container.rules.put(container.children.size - 1, LayoutPair(width, height))
            is HorizontalFillLinearLayout ->
                container.rules.put(container.children.size - 1, LayoutPair(width, height))
        }
    }

    fun clearChildren() = container.clearChildren()
}