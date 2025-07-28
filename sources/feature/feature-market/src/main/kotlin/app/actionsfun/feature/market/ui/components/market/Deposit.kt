package app.actionsfun.feature.market.ui.components.market

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.actionsfun.common.ui.components.button.BaseThreeDimensionalButton
import app.actionsfun.common.ui.style.AppTheme
import app.actionsfun.common.ui.style.Body14Medium
import app.actionsfun.common.ui.style.Body14Regular
import app.actionsfun.common.ui.style.Body16Medium
import app.actionsfun.common.ui.style.Heading1
import app.actionsfun.feature.market.ui.QuickAmountUI

@Composable
internal fun Deposit(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(32.dp),
            )
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color(0xFFE9E9ED),
                shape = RoundedCornerShape(32.dp),
            )
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            text = "Will pump.fun have higher trading volume than bonk.fun in exactly 24 hours?",
            style = Body16Medium,
            color = AppTheme.Colors.Text.Primary,
        )

        OptionPicker()

        AmountInput(
            title = "Enter amount",
            amount = 0f,
            label = "Balance: 23.52 SOL",
            error = null,
        )

        QuickAmounts(
            options = listOf(
                QuickAmountUI(0.1f, "+0.1"),
                QuickAmountUI(0.5f, "+0.5"),
                QuickAmountUI(1f, "+1"),
                QuickAmountUI(24f, "Max"),
            )
        )
    }
}

@Composable
private fun OptionPicker(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OptionButton(
            modifier = Modifier
                .weight(1f),
            text = "YES",
            label = "12%",
            isActive = true,
            option = true,
            onClick = {},
        )
        OptionButton(
            modifier = Modifier
                .weight(1f),
            text = "NO",
            label = "88%",
            isActive = false,
            option = false,
            onClick = {},
        )
    }
}

@Composable
private fun OptionButton(
    text: String,
    label: String,
    isActive: Boolean,
    option: Boolean, // yes or no
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { Unit },
) {
    val borderColor = when {
        !isActive -> Color(0xFFE9E9ED)
        option -> Color(0xFF21D979)
        else -> Color(0xFFF55B7F)
    }
    val contentColor = when {
        !isActive -> Color(0xFF9FA5AC)
        option -> Color(0xFF21D979)
        else -> Color(0xFFF55B7F)
    }

    BaseThreeDimensionalButton(
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(32.dp)
            )
            .height(44.dp),
        color = Color(0xFFFFFFFF),
        onClick = onClick,
        shadowSize = if (isActive) 4.dp else 0.dp,
        shadowColor = borderColor,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = text,
                style = Body16Medium,
                color = contentColor,
            )
            Text(
                text = label,
                style = Body16Medium,
                color = contentColor,
            )
        }
    }
}

@Composable
private fun AmountInput(
    title: String,
    amount: Float,
    label: String,
    error: String?,
    modifier: Modifier = Modifier,
    onAmountChange: (Float) -> Unit = { },
) {
    val text = if (amount == 0f) "" else amount.toString()
    val isHint = text.isEmpty()

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            style = Body14Medium,
            color = AppTheme.Colors.Text.Primary,
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 70.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            AnimatedContent(
                targetState = isHint,
                transitionSpec = { fadeIn() togetherWith fadeOut() }
            ) { isHint ->
                if (isHint) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        tint = Color(0xFFDBDEE0),
                        painter = painterResource(app.actionsfun.common.ui.R.drawable.ic_solana),
                        contentDescription = null,
                    )
                } else {
                    Image(
                        modifier = Modifier.size(40.dp),
                        painter = painterResource(app.actionsfun.common.ui.R.drawable.ic_solana),
                        contentDescription = null,
                    )
                }
            }

            BasicTextField(
                modifier = Modifier,
                value = text,
                onValueChange = { newText ->
                    onAmountChange(newText.toFloatOrNull() ?: 0f)
                },
                textStyle = Heading1.copy(
                    color = if (isHint) Color(0xFFDBDEE0) else Color(0xFF000000)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (isHint) {
                        Text(
                            text = "0.00",
                            style = Heading1,
                            color = Color(0xFFDBDEE0)
                        )
                    }
                    innerTextField()
                }
            )
        }

        Text(
            text = label,
            style = Body14Regular,
            color = AppTheme.Colors.Text.Secondary,
        )
    }
}

@Composable
private fun QuickAmounts(
    options: List<QuickAmountUI>,
    modifier: Modifier = Modifier,
    onClick: (Float) -> Unit = { Unit },
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        options.forEach { amount ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE9E9ED),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .height(36.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .clickable { onClick(amount.value) },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = amount.label,
                    style = Body16Medium,
                    color = AppTheme.Colors.Text.Primary,
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Deposit(
            modifier = Modifier
                .padding(all = 16.dp)
        )
    }
}
