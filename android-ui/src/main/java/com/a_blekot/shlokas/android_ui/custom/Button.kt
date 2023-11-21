package com.a_blekot.shlokas.android_ui.custom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android_ui.Spacer
import com.a_blekot.shlokas.android_ui.theme.AppTheme

@Composable
fun ButtonPrimary(
    text: String,
    enabled: Boolean = true,
    verticalPadding: Dp = 10.dp,
    horizontalPadding: Dp = 8.dp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    OutlinedButton(
        enabled = enabled,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = colorScheme.primary,
            containerColor = colorScheme.primary,
        ),
        contentPadding = PaddingValues(
            horizontal = horizontalPadding,
            vertical = verticalPadding
        ),
        onClick = onClick,
        modifier = modifier,
    )
    {
        Text(
            text = text,
            color = colorScheme.onPrimary,
            style = typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun ButtonSecondary(
    text: String,
    enabled: Boolean = true,
    verticalPadding: Dp = 10.dp,
    horizontalPadding: Dp = 8.dp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    OutlinedButton(
        enabled = enabled,
        border = BorderStroke(1.dp, color = Color.LightGray),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color.Transparent,
//            backgroundColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(
            horizontal = horizontalPadding,
            vertical = verticalPadding
        ),
        onClick = onClick,
        modifier = modifier,
    )
    {
        Text(
            text = text,
            color = colorScheme.primary,
            style = typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00FF00)
@Composable
fun ButtonsPreview() {
    AppTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            ButtonPrimary(
                text = "Primary",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(16.dp)
            ButtonSecondary(
                text = "Secondary",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}