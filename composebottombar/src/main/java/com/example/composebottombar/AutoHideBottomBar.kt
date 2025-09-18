package com.example.composebottombar

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AutoHideBottomNavigationBar(
    selectedItemIndex: Int,
    items: List<FarmerBottomBarItem>,
    isDarkTheme: Boolean,
    isExpanded: Boolean,
    onItemSelected: (Int) -> Unit,
    onExpandRequested: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isAutoExpanded by remember { mutableStateOf(true) }
    var shouldResetTimer by remember { mutableStateOf(false) }

    LaunchedEffect(selectedItemIndex, shouldResetTimer) {
        isAutoExpanded = true
        kotlinx.coroutines.delay(4000)
        isAutoExpanded = false
        if (shouldResetTimer) {
            shouldResetTimer = false
        }
    }

    val borderColor = if (isDarkTheme) {
        Color(0xFFFF6E40).copy(alpha = 0.4f)
    } else {
        Color(0xFFFF6E40).copy(alpha = 0.6f)
    }

    val targetBarWidth by animateDpAsState(
        targetValue = if (isAutoExpanded) {
            (items.size * 65 + 120 - 65 + 32).dp
        } else {
            120.dp
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ), label = "bar_width_anim"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .width(targetBarWidth)
                .height(65.dp)
                .border(
                    width = 0.3.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(32.dp)
                )
                .clickable(
                    onClick = {
                        if (!isAutoExpanded) {
                            isAutoExpanded = true
                            shouldResetTimer = true
                            onExpandRequested()
                        }
                    },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF4B7146)),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            AnimatedContent(
                targetState = isAutoExpanded,
                transitionSpec = { fadeIn() with fadeOut() },
                label = "bottom_bar_content"
            ) { expanded ->
                if (expanded) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items.forEachIndexed { index, item ->
                            AppleStyleBottomNavItem(
                                item = item,
                                isSelected = selectedItemIndex == index,
                                isDarkTheme = isDarkTheme,
                                onClick = {
                                    onItemSelected(index)
                                    shouldResetTimer = true
                                }
                            )
                        }
                    }
                } else {
                    CompactSelectedItem(
                        item = items[selectedItemIndex],
                        isDarkTheme = isDarkTheme
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppleStyleBottomNavItem(
    item: FarmerBottomBarItem,
    isSelected: Boolean,
    isDarkTheme: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectionBackgroundColor by animateColorAsState(
        targetValue = if (isSelected) {
            if (isDarkTheme) Color(0xFF0A84FF) else Color(0xFF007AFF)
        } else Color.Transparent,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "background_color_anim"
    )

    val targetWidth by animateDpAsState(
        targetValue = if (isSelected) 120.dp else 65.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "width_anim"
    )

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.02f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scale_anim"
    )

    Box(
        modifier = modifier
            .width(targetWidth)
            .height(50.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(RoundedCornerShape(25.dp))
            .background(selectionBackgroundColor)
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = isSelected,
            transitionSpec = {
                if (targetState) {
                    slideInHorizontally(
                        initialOffsetX = { -it / 2 },
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    ) + fadeIn(
                        animationSpec = tween(200, delayMillis = 50)
                    ) with slideOutHorizontally(
                        targetOffsetX = { it / 2 },
                        animationSpec = tween(150)
                    ) + fadeOut(animationSpec = tween(150))
                } else {
                    slideInHorizontally(
                        initialOffsetX = { it / 2 },
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    ) + fadeIn(
                        animationSpec = tween(200, delayMillis = 50)
                    ) with slideOutHorizontally(
                        targetOffsetX = { -it / 2 },
                        animationSpec = tween(150)
                    ) + fadeOut(animationSpec = tween(150))
                }
            },
            label = "item_content"
        ) { selected ->
            if (selected) {
                // Selected state - horizontal layout with icon and text
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Icon(
                        imageVector = item.selectedIcon,
                        contentDescription = item.name,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = item.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        maxLines = 1
                    )
                }
            } else {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = item.unselectedIcon,
                        contentDescription = item.name,
                        tint = if (isDarkTheme) Color(0xFFFFFFFF) else Color(0xFF000000),
                        modifier = Modifier.size(26.dp)
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = item.name,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isDarkTheme) Color(0xFFFFFFFF) else Color(0xFF000000),
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
fun CompactSelectedItem(
    item: FarmerBottomBarItem,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "compact_scale"
    )

    val selectionBackgroundColor by animateColorAsState(
        targetValue = if (isDarkTheme) Color(0xFF0A84FF) else Color(0xFF007AFF),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "background_color_anim"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .width(120.dp)
            .height(50.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(RoundedCornerShape(35.dp))
            .background(selectionBackgroundColor)

    ) {
        Icon(
            imageVector = item.selectedIcon,
            contentDescription = item.name,
            tint = Color.White,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = item.name,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            maxLines = 1
        )
    }
}