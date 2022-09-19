package com.a_blekot.memorize_shloka

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MusicOff
import androidx.compose.material.icons.rounded.VolunteerActivism
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android_ui.custom.StandartColumn
import com.a_blekot.shlokas.android_ui.custom.StandartRow
import com.a_blekot.shlokas.android_ui.theme.Dimens
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingS
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingXS
import com.a_blekot.shlokas.android_ui.view.list.ListView
import com.a_blekot.shlokas.android_ui.view.player.PlayerView
import com.a_blekot.shlokas.android_ui.view.settings.SettingsView
import com.a_blekot.shlokas.common.root.RootComponent
import com.a_blekot.shlokas.common.root.RootComponent.Child.*
import com.a_blekot.shlokas.common.root.RootComponent.Child.List
import com.a_blekot.shlokas.common.utils.billing.BillingHelper
import com.a_blekot.shlokas.common.utils.billing.DonationProduct
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun MainContent(component: RootComponent, billingHelper: BillingHelper? = null) {

    StandartColumn {
//        billingHelper.availableDonations
//            .filter { it.price != null }
//            .forEach {
//                SubscriptionButton(it, billingHelper)
//            }

        Children(stack = component.childStack, animation = stackAnimation(fade() + scale())) {
            when (val child = it.instance) {
                is List -> ListView(child.component)
                is Player -> PlayerView(child.component)
                is Settings -> SettingsView(child.component)
                else -> throw IllegalArgumentException("No View for child: ${child.javaClass.simpleName}")
            }
        }
    }
}

@Composable
fun SubscriptionButton(donation: DonationProduct, billingHelper: BillingHelper) {
    StandartRow(
        horizontalArrangement = Arrangement.spacedBy(paddingS),
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                shape = RoundedCornerShape(Dimens.radiusS)
            )
            .padding(vertical = paddingXS)
            .clickable { billingHelper.purchase(donation) }
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
            donation.price ?: "??? $",
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1
        )

        Icon(
            Icons.Rounded.VolunteerActivism,
            "donate",
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(Dimens.iconSizeL)
        )
    }
}