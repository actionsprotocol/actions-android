package app.actionsfun.common.ui.components.market

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.actionsfun.common.ui.components.threed.Gyroscopic3DLayout
import app.actionsfun.common.ui.components.threed.ThreeDimensionalLayout
import app.actionsfun.common.ui.rememberAppShimmer
import app.actionsfun.common.ui.style.AppTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun MarketShimmer(
    modifier: Modifier = Modifier,
) {
    ThreeDimensionalLayout(
        modifier = modifier,
        color = AppTheme.Colors.Background.Surface,
        shape = RoundedCornerShape(32.dp),
        shadowColor = Color(0xFFE9E9ED),
        shadowAlignment = Alignment.BottomStart,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    width = 2.dp,
                    color = Color(0xFFE9E9ED),
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(all = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            // Header shimmer
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE9E9ED))
                        .shimmer(rememberAppShimmer())
                )

                // Username and time
                Column(modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(16.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFE9E9ED))
                            .shimmer(rememberAppShimmer())
                    )
                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .width(80.dp)
                            .height(12.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFE9E9ED))
                            .shimmer(rememberAppShimmer())
                    )
                }

                // Status
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(32.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFFE9E9ED))
                        .shimmer(rememberAppShimmer())
                )
            }

            // Market Description shimmer
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                // Title
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFE9E9ED))
                        .shimmer(rememberAppShimmer())
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(24.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFE9E9ED))
                        .shimmer(rememberAppShimmer())
                )

                // Description
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth()
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFE9E9ED))
                        .shimmer(rememberAppShimmer())
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFE9E9ED))
                        .shimmer(rememberAppShimmer())
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(14.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFE9E9ED))
                        .shimmer(rememberAppShimmer())
                )
            }

            // About Market shimmer
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                // Section title
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(24.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFE9E9ED))
                        .shimmer(rememberAppShimmer())
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Market image
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFE9E9ED))
                            .shimmer(rememberAppShimmer())
                    )

                    // Divider
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(16.dp)
                            .background(Color(0xFFE9E9ED))
                    )

                    // Chance section
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .width(50.dp)
                                    .height(12.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFFE9E9ED))
                                    .shimmer(rememberAppShimmer())
                            )
                            Box(
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .width(40.dp)
                                    .height(24.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFFE9E9ED))
                                    .shimmer(rememberAppShimmer())
                            )
                        }

                        // Progress indicator
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE9E9ED))
                                .shimmer(rememberAppShimmer())
                        )
                    }

                    // Divider
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(16.dp)
                            .background(Color(0xFFE9E9ED))
                    )

                    // Volume section
                    Column {
                        Box(
                            modifier = Modifier
                                .width(50.dp)
                                .height(12.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFE9E9ED))
                                .shimmer(rememberAppShimmer())
                        )
                        Row(
                            modifier = Modifier.padding(top = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE9E9ED))
                                    .shimmer(rememberAppShimmer())
                            )
                            Box(
                                modifier = Modifier
                                    .width(40.dp)
                                    .height(24.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFFE9E9ED))
                                    .shimmer(rememberAppShimmer())
                            )
                        }
                    }
                }
            }

            // Replies shimmer
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                // Replies header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(16.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFE9E9ED))
                            .shimmer(rememberAppShimmer())
                    )
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(14.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFE9E9ED))
                            .shimmer(rememberAppShimmer())
                    )
                }

                // Reply items
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    repeat(3) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE9E9ED))
                                    .shimmer(rememberAppShimmer())
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(14.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFFE9E9ED))
                                    .shimmer(rememberAppShimmer())
                            )
                        }
                    }
                }
            }
        }
    }
}