package com.a_blekot.shlokas.android_ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.a_blekot.shlokas.android_ui.theme.Dimens.borderS
import com.a_blekot.shlokas.android_ui.theme.Dimens.buttonHeight
import com.a_blekot.shlokas.android_ui.theme.Dimens.radiusM
import dev.icerock.moko.resources.StringResource

@Composable
fun DialogButton(
    modifier: Modifier,
    isSelected: Boolean,
    title: StringResource,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(buttonHeight)
            .clip(shape = RoundedCornerShape(radiusM))
            .background(
                color = if (isSelected) colorScheme.secondaryContainer else Color.Transparent,
            )
            .border(
                width = borderS,
                color = colorScheme.primary,
                shape = RoundedCornerShape(radiusM)
            )
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = resolveString(title),
            maxLines = 1,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = colorScheme.onPrimaryContainer
        )
    }
}