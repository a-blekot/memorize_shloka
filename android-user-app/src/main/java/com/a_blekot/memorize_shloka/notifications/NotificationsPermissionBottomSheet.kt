package com.a_blekot.memorize_shloka.notifications

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a_blekot.memorize_shloka.R
import com.a_blekot.shlokas.android_ui.Spacer
import com.a_blekot.shlokas.android_ui.custom.ButtonPrimary
import com.a_blekot.shlokas.android_ui.custom.ButtonSecondary
import com.a_blekot.shlokas.android_ui.painter
import com.a_blekot.shlokas.android_ui.resource
import com.a_blekot.shlokas.android_ui.theme.AppTheme

@Composable
fun NotificationsPermissionBottomSheet(
    onTurnOnClick: () -> Unit,
    onSkipClick: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.background)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(24.dp)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(104.dp)
                .background(
                    color = colorScheme.primaryContainer,
                    shape = RoundedCornerShape(32.dp),
                )
        ) {
            Image(
                painter = R.drawable.nrs_notifications_ic_bell.painter(),
                contentDescription = null,
                modifier = Modifier
                    .size(84.dp)
            )
        }
        Spacer(16.dp)
        Text(
            text = R.string.notifications_permission_dialog_title.resource(),
            style = typography.titleMedium,
            color = colorScheme.primary
        )
        Spacer(8.dp)
        Text(
            text = R.string.notifications_permission_dialog_subtitle.resource(),
            style = typography.bodyMedium,
            color = colorScheme.secondary,
        )
        Spacer(16.dp)

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            ButtonSecondary(
                text = R.string.notifications_permission_dialog_skip.resource(),
                onClick = onSkipClick,
            )
            Spacer(16.dp)
            ButtonPrimary(
                text = R.string.notifications_permission_dialog_action.resource(),
                modifier = Modifier
                    .weight(1f),
                onClick = onTurnOnClick,
            )
        }
        Spacer(8.dp)
    }
}


@Preview
@Composable
private fun Preview() {
    AppTheme {
        NotificationsPermissionBottomSheet(
            {}, {}
        )
    }
}
