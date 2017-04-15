package im.y2k.libgdxdsl

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by y2k on 15/04/2017.
 **/

inline val Vector2.width: Float get() = x
inline val Vector2.height: Float get() = y

data class ImageWrapper(
    val width: Float,
    val height: Float,
    val aspect: Float)

internal fun colorRgba(value: Long) = Color(value.toInt())

internal fun createFillTexture(width: Int, height: Int, color: Color): Texture {
    val pixmap = Pixmap(width, height, Pixmap.Format.RGBA8888)
    pixmap.setColor(color)
    pixmap.fillRectangle(0, 0, width, height)
    val texture = Texture(pixmap)
    pixmap.dispose()
    return texture
}

internal inline fun <T> listenable(initialValue: T, crossinline onChange: (newValue: T) -> Unit):
    ReadWriteProperty<Any?, T> = object : ObservableProperty<T>(initialValue) {
    override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {
        if (newValue != oldValue)
            onChange(newValue)
    }
}

internal inline fun <T> listenable(crossinline onChange: (newValue: T?) -> Unit):
    ReadWriteProperty<Any?, T?> = object : ObservableProperty<T?>(null) {
    override fun afterChange(property: KProperty<*>, oldValue: T?, newValue: T?) {
        if (newValue != oldValue)
            onChange(newValue)
    }
}

internal data class Size(val width: Float = 0f, val height: Float = 0f) {
    val isZero: Boolean
        get() = width == 0f && height == 0f
}