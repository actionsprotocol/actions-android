package app.actionsfun.feature.market.ui.components.market

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.actionsfun.common.ui.R
import app.actionsfun.common.ui.components.button.PrimaryButton
import app.actionsfun.common.ui.style.*
import app.actionsfun.common.util.timeRelativeString
import app.actionsfun.feature.market.ui.RepliesUI

@Composable
internal fun RepliesCard(
    state: RepliesUI,
    modifier: Modifier = Modifier,
    onSendReply: (String) -> Unit = {},
    onConnectWallet: () -> Unit = {},
) {
    var replyText by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White, RoundedCornerShape(32.dp))
                .border(2.dp, Color(0xFFEC58A9), RoundedCornerShape(32.dp))
                .padding(24.dp)
        ) {
            if (state.replies.isEmpty()) {
                // Empty state
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No replies yet",
                        style = Heading4,
                        color = AppTheme.Colors.Text.Primary,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Be the first to share your thoughts",
                        style = Body14Regular,
                        color = AppTheme.Colors.Text.Secondary,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.replies) { reply ->
                        ReplyItem(reply = reply)
                    }
                }
            }
        }

        // Show connect wallet button or input field based on wallet connection state
        if (!state.isWalletConnected) {
            PrimaryButton(
                text = "Connect wallet to reply",
                onClick = onConnectWallet,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(
                            AppTheme.Colors.Border.Primary,
                            RoundedCornerShape(24.dp)
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = replyText,
                        onValueChange = { replyText = it },
                        modifier = Modifier.weight(1f),
                        textStyle = Body14Regular.applyColor(AppTheme.Colors.Text.Primary),
                        cursorBrush = SolidColor(AppTheme.Colors.Text.Primary),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                if (replyText.text.isNotBlank()) {
                                    onSendReply(replyText.text)
                                    replyText = TextFieldValue("")
                                }
                            }
                        ),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (replyText.text.isEmpty()) {
                                    Text(
                                        text = "Write your reply",
                                        style = Body14Regular,
                                        color = AppTheme.Colors.Text.Secondary
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )

                    IconButton(
                        onClick = {
                            if (replyText.text.isNotBlank()) {
                                onSendReply(replyText.text)
                                replyText = TextFieldValue("")
                            }
                        },
                        modifier = Modifier.size(32.dp),
                        enabled = replyText.text.isNotBlank()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chevron_right),
                            contentDescription = "Send",
                            tint = if (replyText.text.isNotBlank()) 
                                Color(0xFFEC58A9) 
                            else 
                                AppTheme.Colors.Text.Secondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReplyItem(
    reply: CommentUI,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(reply.image),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = reply.author,
                    style = Body14SemiBold,
                    color = AppTheme.Colors.Text.Primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false)
                )
                
                Text(
                    text = reply.createdAt.timeRelativeString(),
                    style = Body12Regular,
                    color = AppTheme.Colors.Text.Secondary
                )
            }

            Text(
                text = reply.text,
                style = Body14Regular,
                color = AppTheme.Colors.Text.Primary
            )
        }
    }
}