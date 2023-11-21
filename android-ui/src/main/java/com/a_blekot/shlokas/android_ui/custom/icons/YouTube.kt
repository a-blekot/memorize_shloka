package com.a_blekot.shlokas.android_ui.custom.icons

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android_ui.theme.AppTheme

val Icons.Rounded.BrandYoutube: ImageVector
    get() {
        if (_brandYoutube != null) {
            return _brandYoutube!!
        }
        _brandYoutube = materialIcon(name = "Rounded.Share") {
//        Builder(name = "BrandYoutube", defaultWidth = 24.0.dp, defaultHeight =
//        24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(7.0f, 5.0f)
                lineTo(17.0f, 5.0f)
                arcTo(4.0f, 4.0f, 0.0f, false, true, 21.0f, 9.0f)
                lineTo(21.0f, 15.0f)
                arcTo(4.0f, 4.0f, 0.0f, false, true, 17.0f, 19.0f)
                lineTo(7.0f, 19.0f)
                arcTo(4.0f, 4.0f, 0.0f, false, true, 3.0f, 15.0f)
                lineTo(3.0f, 9.0f)
                arcTo(4.0f, 4.0f, 0.0f, false, true, 7.0f, 5.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(10.0f, 9.0f)
                lineToRelative(5.0f, 3.0f)
                lineToRelative(-5.0f, 3.0f)
                close()
            }
        }
        return _brandYoutube!!
    }

private var _brandYoutube: ImageVector? = null

@Preview
@Composable
private fun BrandYoutubePreview() {
    AppTheme {
        IconButton(
            onClick = {},
            modifier = Modifier.size(128.dp),
        ) {
            Icon(
                Icons.Rounded.BrandYoutube,
                "YouTube",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}