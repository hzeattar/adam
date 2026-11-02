package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.google.firebase.auth.FirebaseUser

@Composable
fun ReViveHeader(
    currentUser: FirebaseUser?,
    walletBalanceEgp: Double,
    onOpenWallet: () -> Unit,
    onAuthClick: () -> Unit
) {
    Surface(
        color = TechCardDark,
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, TechCardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Brand Logo & Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(CyberEmeraldPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Memory,
                        contentDescription = "ReVive E-Waste",
                        tint = Color.Black,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "ReVive",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                letterSpacing = 0.5.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = CircuitCyan.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "E-WASTE EGYPT",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = CircuitCyan,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = if (currentUser != null) "مرحباً: ${currentUser.displayName ?: "مستخدم ريفايف"}" else "وضع الزائر • سجل الدخول للمزامنة",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (currentUser != null) CyberEmeraldPrimary else TextMedium,
                        maxLines = 1
                    )
                }
            }

            // Actions: Wallet + Auth
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    onClick = onOpenWallet,
                    color = Color(0xFF09141F),
                    shape = RoundedCornerShape(20.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CyberEmeraldPrimary.copy(alpha = 0.5f)),
                    modifier = Modifier.testTag("wallet_balance_header_btn")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountBalanceWallet,
                            contentDescription = null,
                            tint = CyberEmeraldPrimary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${walletBalanceEgp.toInt()} ج.م",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = CyberEmeraldPrimary
                        )
                    }
                }

                IconButton(
                    onClick = onAuthClick,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF0F1C2B))
                        .testTag("header_auth_btn")
                ) {
                    Icon(
                        imageVector = if (currentUser != null) Icons.Filled.Logout else Icons.Filled.AccountCircle,
                        contentDescription = if (currentUser != null) "تسجيل الخروج" else "تسجيل الدخول",
                        tint = if (currentUser != null) ReclaimedCopper else CircuitCyan,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
