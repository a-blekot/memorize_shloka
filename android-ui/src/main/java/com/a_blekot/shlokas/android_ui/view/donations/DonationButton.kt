package com.a_blekot.shlokas.android_ui.view.donations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.VolunteerActivism
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import com.a_blekot.shlokas.android_ui.custom.StandartRow
import com.a_blekot.shlokas.android_ui.theme.Dimens
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingS
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingXS
import com.a_blekot.shlokas.common.data.Donation

@Composable
fun DonationButton(donation: Donation, purchase: () -> Unit = {}) {
    StandartRow(
        horizontalArrangement = Arrangement.spacedBy(paddingS),
        modifier = Modifier
            .clip(shape = RoundedCornerShape(Dimens.radiusS))
            .background(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
            )
            .padding(horizontal = paddingS)
            .clickable { purchase.invoke() },
        padding = paddingS
    ) {
        Text(
            donation.title,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1
        )

        Spacer(modifier = Modifier.weight(1.0f))

        Text(
            donation.formattedPrice,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1
        )

        Spacer(modifier = Modifier.width(paddingXS))

        Icon(
            Icons.Rounded.VolunteerActivism,
            "donate",
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(Dimens.iconSizeL)
        )
    }
}