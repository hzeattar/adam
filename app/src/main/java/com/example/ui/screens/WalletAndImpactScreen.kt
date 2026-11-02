package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.EwasteRepository
import com.example.model.EwastePickupOrder
import com.example.ui.theme.*

@Composable
fun WalletAndImpactScreen(
    repository: EwasteRepository,
    modifier: Modifier = Modifier
) {
    val stats by repository.impactStats.collectAsState()
    val orders by repository.pickupOrders.collectAsState()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Wallet Balance & Payout Status
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = TechCardDark),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CircuitCyan.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "محفظة ريفايف والأرباح الكاش",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = CircuitCyan
                        )
                        Surface(
                            color = CyberEmeraldPrimary.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "InstaPay & Vodafone Cash",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyberEmeraldPrimary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "إجمالي المبالغ المستلمة من الأجهزة:",
                        fontSize = 11.sp,
                        color = TextMedium
                    )
                    Text(
                        text = "${stats.totalCashEarnedEgp.toInt()} جنيه مصري",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = CyberEmeraldPrimary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            color = Color(0xFF09121B),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Filled.Stars, contentDescription = null, tint = ReclaimedGold, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("رصيد النقاط: ${stats.ecoPointsBalance} نقطة", fontSize = 11.sp, color = ReclaimedGold, fontWeight = FontWeight.Bold)
                            }
                        }

                        Surface(
                            color = Color(0xFF09121B),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "كوبون خصم 20% متاح",
                                fontSize = 11.sp,
                                color = CircuitCyan,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        // 2. Heavy Metal & Environmental Recovery Ledger
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF080F16)),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, TechCardBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "أثرك البيئي واستخلاص المعادن من الأجهزة:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextHigh
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ImpactBadge("ذهب 24K مستخلص", "${stats.goldRecoveredMg.toInt()} مجم", ReclaimedGold, Icons.Filled.Diamond)
                        ImpactBadge("نحاس معاد استخدامه", "${stats.copperRecoveredG.toInt()} جم", ReclaimedCopper, Icons.Filled.PrecisionManufacturing)
                        ImpactBadge("نفايات إلكترونية محولة", "${stats.totalEwasteRecycledKg} كجم", CyberEmeraldPrimary, Icons.Filled.DeleteForever)
                        ImpactBadge("انبعاثات CO₂ متفاداة", "${stats.co2PreventedKg.toInt()} كجم", CircuitCyan, Icons.Filled.Park)
                    }
                }
            }
        }

        // 3. Active Pickup Orders Status
        item {
            Text(
                text = "طلبات الاستلام الجارية ومتابعة الكاش (${orders.size})",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextHigh
            )
        }

        items(orders, key = { it.id }) { order ->
            PickupOrderCard(order = order)
        }
    }
}

@Composable
private fun ImpactBadge(
    title: String,
    value: String,
    color: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp, color = color)
        Text(title, fontSize = 9.sp, color = TextMedium)
    }
}

@Composable
fun PickupOrderCard(order: EwastePickupOrder) {
    Card(
        colors = CardDefaults.cardColors(containerColor = TechCardDark),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, TechCardBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("طلب رقم: ${order.id}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = CircuitCyan)

                Surface(
                    color = when (order.status) {
                        "PAID_OUT" -> CyberEmeraldPrimary.copy(alpha = 0.2f)
                        "COURIER_ASSIGNED" -> CircuitCyan.copy(alpha = 0.2f)
                        else -> ReclaimedGold.copy(alpha = 0.2f)
                    },
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = when (order.status) {
                            "PAID_OUT" -> "تم التحويل بنجاح ✅"
                            "COURIER_ASSIGNED" -> "المندوب في الطريق 🛵"
                            "TESTED_AT_LAB" -> "جاري الفحص بالمعمل 🔬"
                            else -> "طلب جديد ⏳"
                        },
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (order.status) {
                            "PAID_OUT" -> CyberEmeraldPrimary
                            "COURIER_ASSIGNED" -> CircuitCyan
                            else -> ReclaimedGold
                        },
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("${order.brand} - ${order.modelName}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextHigh)
            Text("📍 ${order.address} (${order.city})", fontSize = 11.sp, color = TextMedium)

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "طريقة الدفع: ${order.payoutMethod.titleAr} (${order.payoutAccount})",
                    fontSize = 10.sp,
                    color = TextMedium
                )
                Text(
                    text = "${order.estimatedCashEgp.toInt()} ج.م",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = CyberEmeraldPrimary
                )
            }
        }
    }
}
