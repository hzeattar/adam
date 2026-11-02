package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.EwasteRepository
import com.example.model.DropOffKiosk
import com.example.ui.theme.*

@Composable
fun DropOffMapScreen(
    repository: EwasteRepository,
    modifier: Modifier = Modifier
) {
    val kiosks = repository.dropOffKiosks
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header Banner
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Place,
                                contentDescription = null,
                                tint = CircuitCyan,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "نقاط وصناديق التجميع الذكية",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = CircuitCyan
                            )
                        }

                        Surface(
                            color = CyberEmeraldPrimary.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "4 فروع معتمدة بمصر",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyberEmeraldPrimary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "تقدر تودع بطارياتك أو أجهزتك القديمة بنفسك في أي صندوق ذكي وتحصل على بونص فوري في محفظتك.",
                        fontSize = 11.sp,
                        color = TextMedium
                    )
                }
            }
        }

        // Kiosks List
        items(kiosks, key = { it.id }) { kiosk ->
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
                        Text(kiosk.nameAr, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextHigh)
                        Surface(
                            color = ReclaimedGold.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = kiosk.bonusMultiplier,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = ReclaimedGold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(kiosk.nameEn, fontSize = 10.sp, color = TextMuted)
                    Spacer(modifier = Modifier.height(6.dp))

                    Text("📍 ${kiosk.address}", fontSize = 11.sp, color = TextMedium)
                    Text("🏢 ${kiosk.area} - ${kiosk.city}", fontSize = 11.sp, color = CircuitCyan)

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            if (kiosk.acceptsBatteries) {
                                Surface(
                                    color = BatteryGreen.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "🔋 يستقبل بطاريات",
                                        fontSize = 9.sp,
                                        color = BatteryGreen,
                                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            if (kiosk.acceptsLargeElectronics) {
                                Surface(
                                    color = CircuitCyan.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "💻 يستقبل لابتوب وشاشات",
                                        fontSize = 9.sp,
                                        color = CircuitCyan,
                                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        OutlinedButton(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Address", "${kiosk.nameAr} - ${kiosk.address}")
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "تم نسخ العنوان للملاحة!", Toast.LENGTH_SHORT).show()
                            },
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(Icons.Filled.ContentCopy, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("نسخ العنوان", fontSize = 10.sp)
                        }
                    }
                }
            }
        }
    }
}
