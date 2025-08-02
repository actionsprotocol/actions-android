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
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.actionsfun.common.ui.components.button.BaseThreeDimensionalButton
import app.actionsfun.common.ui.components.button.PrimaryButton
import app.actionsfun.common.ui.components.button.ThreeDimensionalButton
import app.actionsfun.common.ui.modifier.hapticFeedback
import app.actionsfun.common.ui.style.AppTheme
import app.actionsfun.common.ui.style.Body14Medium
import app.actionsfun.common.ui.style.Body14Regular
import app.actionsfun.common.ui.style.Body16Medium
import app.actionsfun.common.ui.style.Heading1
import app.actionsfun.feature.market.ui.DepositUI
import app.actionsfun.feature.market.ui.QuickAmountUI
import timber.log.Timber

@Composable
internal fun Deposit(
    state: DepositUI,
    modifier: Modifier = Modifier,
    quickAmountClick: (Float) -> Unit = { Unit },
    optionClick: (Boolean) -> Unit = { Unit },
    valueChange: (Float) -> Unit = { Unit },
    actionButtonClick: () -> Unit = { Unit },
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .padding(bottom = 16.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier
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
                .alpha(
                    if (state.enabled) 1f else .4f
                )
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Text(
                text = state.title,
                style = Body16Medium,
                color = AppTheme.Colors.Text.Primary,
            )

            OptionPicker(
                state = state,
                optionSelected = optionClick,
            )

            AmountInput(
                title = state.label,
                amount = state.amount,
                infoMessage = state.infoMessage,
                label = "Balance: ${state.balance} SOL",
                enabled = state.enabled && !state.loading,
                onAmountChange = valueChange,
            )

            QuickAmounts(
                options = state.quickAmounts,
                enabled = state.enabled && !state.loading,
                onClick = quickAmountClick,
            )
        }

        PrimaryButton(
            text = state.button,
            loading = state.loading,
            enabled = state.buttonEnabled,
            onClick = actionButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun OptionPicker(
    state: DepositUI,
    modifier: Modifier = Modifier,
    optionSelected: (Boolean) -> Unit = { Unit },
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OptionButton(
            modifier = Modifier
                .weight(1f)
                .hapticFeedback(HapticFeedbackType.ContextClick),
            text = "YES",
            label = state.yesPercentage,
            isActive = state.selectedOption,
            option = true,
            onClick = {
                if (!state.loading && state.enabled) {
                    optionSelected(true)
                }
            },
        )
        OptionButton(
            modifier = Modifier
                .weight(1f)
                .hapticFeedback(HapticFeedbackType.ContextClick),
            text = "NO",
            label = state.noPercentage,
            isActive = !state.selectedOption,
            option = false,
            onClick = {
                if (!state.loading && state.enabled) {
                    optionSelected(false)
                }
            },
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
    infoMessage: String,
    enabled: Boolean,
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
                    onAmountChange(
                        newText.replace("..", ".")
                            .toFloatOrNull() ?: 0f
                    )
                },
                textStyle = Heading1.copy(
                    color = if (isHint) Color(0xFFDBDEE0) else Color(0xFF000000)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                enabled = enabled,
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

        if (infoMessage.isNotEmpty()) {
            Text(
                text = infoMessage,
                style = Body14Regular,
                color = AppTheme.Colors.Text.Secondary,
            )
        }
    }
}

@Composable
private fun QuickAmounts(
    options: List<QuickAmountUI>,
    enabled: Boolean,
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
                    .hapticFeedback(HapticFeedbackType.ContextClick)
                    .clickable(enabled = enabled) { onClick(amount.value) },
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
            state = DepositUI(
                selectedOption = true,
                amount = 0f,
                title = "Will pump.fun have higher trading volume than bonk.fun in exactly 24 hours?",
                label = "Enter amount",
                balance = 11.23f,
                quickAmounts = listOf(
                    QuickAmountUI(value = 0.1f, label = "0.1"),
                    QuickAmountUI(value = 0.5f, label = "0.5"),
                    QuickAmountUI(value = 1f, label = "1"),
                    QuickAmountUI(value = 23f, label = "Max"),
                ),
                yesPercentage = "12%",
                noPercentage = "88%",
                button = "Deposit",
                enabled = true,
                loading = false,
                buttonEnabled = true,
                infoMessage = ""
            ),
            modifier = Modifier
                .padding(all = 16.dp)
        )
    }
}
