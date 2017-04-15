package im.y2k.libgdxdsl

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable

/**
 * Created by y2k on 06/11/2016.
 **/
class WebImageWidget(
    private val load: (String, Vector2, (Drawable) -> Unit) -> Unit) : Image() {

    var requestSize: Vector2? = null
    var src by listenable<ImageWrapper> { invalidateHierarchy() }

    private var loadedSrc: ImageWrapper? = null
    private var _width = 0f
    private var _height = 0f

    override fun layout() {
        super.layout()
        _width = requestSize?.width ?: parent.width
        val limitedAspect = Math.max(3 / 4f, (src?.aspect ?: 1000f))
        _height = requestSize?.height ?: parent.width / limitedAspect
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        reloadImageIfChanged()
    }

    private fun reloadImageIfChanged() {
        if (loadedSrc == src) return
        loadedSrc = src

        load(makeUrl(), Vector2(prefWidth, prefHeight)) {
            drawable = it
        }
    }

    private fun makeUrl(): String {
        TODO()
    }

    override fun getPrefWidth(): Float = _width
    override fun getPrefHeight(): Float = _height
}