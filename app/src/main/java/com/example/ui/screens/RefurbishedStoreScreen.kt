package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.EwasteRepository
import com.example.model.RefurbishedProduct
import com.example.ui.theme.*

@Composable
fun RefurbishedStoreScreen(
    repository: EwasteRepository,
    modifier: Modifier = Modifier
) {
    val items = repository.refurbishedCatalog
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Banner
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = TechCardDark),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, TechCardBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "متجر الإلكترونيات المجددة المعتمدة",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = CyberEmeraldPrimary
                        )
                        Surface(
                            color = CyberEmeraldPrimary.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "ضمان 12 شهر معتمد",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyberEmeraldPrimary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "أجهزة تم تجديدها وفحصها بمعامل ريفايف في مصر بأعلى معايير الجودة وبطاريات أصلية توفر لك حتى 50% من سعر الجديد.",
                        fontSize = 11.sp,
                        color = TextMedium
                    )
                }
            }
        }

        // Product Cards
        items(items, key = { it.id }) { product ->
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
                        Surface(
                            color = CyberEmeraldPrimary.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = product.conditionGrade,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyberEmeraldPrimary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }

                        Surface(
                            color = BatteryGreen.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "🔋 صحة البطارية: ${product.batteryHealthPct}%",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = BatteryGreen,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = product.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = TextHigh
                    )

                    Text(
                        text = product.specs,
                        fontSize = 11.sp,
                        color = TextMedium
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = Color(0xFF09121B),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "🌱 يمنع ${product.carbonSavedKg.toInt()} كجم CO₂ • ${product.eWasteDivertedGrams.toInt()} جم نفايات",
                                fontSize = 9.sp,
                                color = CircuitCyan,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "${product.refurbPriceEgp.toInt()} ج.م",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = CyberEmeraldPrimary
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "${product.originalPriceEgp.toInt()} ج.م",
                                    fontSize = 11.sp,
                                    color = TextMuted,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }
                            Text("شامل الشحن والضمان المعتمد", fontSize = 9.sp, color = TextMedium)
                        }

                        Button(
                            onClick = {
                                Toast.makeText(context, "تمت إضافة ${product.name} إلى سلة المشتريات!", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CyberEmeraldPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("buy_refurb_${product.id}")
                        ) {
                            Text("شراء الآن", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}
