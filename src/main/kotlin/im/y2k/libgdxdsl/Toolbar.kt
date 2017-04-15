package im.y2k.libgdxdsl

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont

/**
 * Created by y2k on 29/11/2016.
 **/
fun GroupEx.toolbar(font: BitmapFont, title: String) {
    label(font) {
        maxLines = 1
        background = colorRgba(0xff8a19ff)
        color = Color.WHITE
        padding = 20
        text = title
    }
}