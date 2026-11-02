package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.EwasteRepository
import com.example.model.DeviceCategory
import com.example.model.DeviceCondition
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EwasteRecycleScreen(
    repository: EwasteRepository,
    onRequestPickup: (
        category: DeviceCategory,
        brand: String,
        modelName: String,
        condition: DeviceCondition,
        estimatedCash: Double
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf(DeviceCategory.SMARTPHONES) }
    var selectedBrand by remember { mutableStateOf("Apple") }
    var modelText by remember { mutableStateOf("iPhone 12 Pro") }
    var selectedCondition by remember { mutableStateOf(DeviceCondition.LIKE_NEW_WORKING) }

    val valuation = remember(selectedCategory, selectedBrand, modelText, selectedCondition) {
        repository.calculateValuation(selectedCategory, selectedBrand, modelText, selectedCondition)
    }

    val popularBrands = remember(selectedCategory) {
        when (selectedCategory) {
            DeviceCategory.SMARTPHONES -> listOf("Apple", "Samsung", "Xiaomi", "Oppo", "Realme", "Google Pixel")
            DeviceCategory.LAPTOPS_PCS -> listOf("Apple (MacBook)", "Dell", "Lenovo (ThinkPad)", "HP", "Asus", "Acer")
            DeviceCategory.TABLETS -> listOf("Apple (iPad)", "Samsung Galaxy Tab", "Huawei", "Lenovo Tab")
            DeviceCategory.PCBS_COMPONENTS -> listOf("Nvidia GPU", "Intel CPU", "Motherboards", "RAM Modules", "Server PCBs")
            DeviceCategory.BATTERIES_POWER -> listOf("Anker", "Joyroom", "Laptop Batteries", "UPS Lead-Acid", "Li-ion Packs")
            DeviceCategory.CABLES_CHARGERS -> listOf("Apple Original", "Type-C High Wattage", "Mixed E-Scrap Cables", "Charger Bricks")
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // 1. High-Tech E-Waste Recovery Hero Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.revive_ewaste_banner),
                    contentDescription = "ReVive High-Tech Electronics Recycling",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xFF070D12).copy(alpha = 0.95f))
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Surface(
                        color = CyberEmeraldPrimary,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "إعادة تدوير الإلكترونيات في مصر • E-WASTE RECOVERY",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "بيع أو دور أجهزتك القديمة كاش",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    )
                    Text(
                        text = "استلم فلوسك فوراً على إنستاباي أو فودافون كاش مع استخراج المعادن الثمينة بأمان",
                        fontSize = 11.sp,
                        color = TextMedium
                    )
                }
            }
        }

        // 2. Select Device Category
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "1. اختر نوع الجهاز الإلكتروني:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = CircuitCyan
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(DeviceCategory.values()) { cat ->
                        val isSelected = cat == selectedCategory
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                selectedCategory = cat
                                selectedBrand = popularBrands.first()
                            },
                            label = { Text(cat.titleAr, fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = CyberEmeraldPrimary,
                                selectedLabelColor = Color.Black,
                                containerColor = TechCardDark,
                                labelColor = TextHigh
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("cat_chip_${cat.name}")
                        )
                    }
                }
            }
        }

        // 3. Brand & Model Selector
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = TechCardDark),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, TechCardBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "2. الماركة والموديل:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = CircuitCyan
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(popularBrands) { brand ->
                            val isSel = brand == selectedBrand
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSel) CircuitCyan.copy(alpha = 0.2f) else Color(0xFF0F1A26),
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isSel) CircuitCyan else TechCardBorder
                                ),
                                modifier = Modifier.clickable { selectedBrand = brand }
                            ) {
                                Text(
                                    text = brand,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSel) CircuitCyan else TextMedium,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = modelText,
                        onValueChange = { modelText = it },
                        label = { Text("اكتب الموديل أو المواصفات التقنية (اختياري)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyberEmeraldPrimary,
                            unfocusedBorderColor = TechCardBorder,
                            focusedTextColor = TextHigh,
                            unfocusedTextColor = TextHigh
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }
        }

        // 4. Device Condition Evaluator
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "3. الحالة الفنية للجهاز:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = CircuitCyan
                )

                Spacer(modifier = Modifier.height(8.dp))

                DeviceCondition.values().forEach { cond ->
                    val isSelected = cond == selectedCondition
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) Color(cond.tagColorHex).copy(alpha = 0.12f) else TechCardDark,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) Color(cond.tagColorHex) else TechCardBorder
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { selectedCondition = cond }
                            .testTag("condition_select_${cond.name}")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = cond.titleAr,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = TextHigh
                                )
                                Text(
                                    text = cond.titleEn,
                                    fontSize = 10.sp,
                                    color = TextMedium
                                )
                            }

                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedCondition = cond },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(cond.tagColorHex),
                                    unselectedColor = TextMuted
                                )
                            )
                        }
                    }
                }
            }
        }

        // 5. Valuation & Metals Reclaimed Card (EGP & Gold/Copper)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0C1724)),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, CyberEmeraldPrimary)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.MonetizationOn,
                                contentDescription = null,
                                tint = ReclaimedGold,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "التقييم الفوري المعتمد",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = ReclaimedGold
                            )
                        }

                        Surface(
                            color = CyberEmeraldPrimary.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "تسعير سوق الإلكترونيات بمصر",
                                fontSize = 9.sp,
                                color = CyberEmeraldPrimary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Cash EGP Display
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "قيمة الكاش الفوري:",
                                fontSize = 11.sp,
                                color = TextMedium
                            )
                            Text(
                                text = "${valuation.estimatedCashEgp.toInt()} جنيه مصري",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = CyberEmeraldPrimary
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "أو رصيد محفظة (+20% بونص):",
                                fontSize = 10.sp,
                                color = CircuitCyan
                            )
                            Text(
                                text = "${valuation.walletBonusEgp.toInt()} ج.م",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = CircuitCyan
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Precious Metal Recovery Breakdown
                    Surface(
                        color = Color(0xFF080F18),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            MetalStatItem("ذهب مستخلص", "${valuation.goldRecoveredMg.toInt()} مجم", ReclaimedGold)
                            MetalStatItem("نحاس نقي", "${valuation.copperRecoveredG.toInt()} جم", ReclaimedCopper)
                            MetalStatItem("كربون متفادى", "${valuation.co2DivertedKg.toInt()} كجم", CyberEmeraldPrimary)
                            MetalStatItem("نقاط إيكو", "+${valuation.ecoPoints}", CircuitCyan)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            onRequestPickup(
                                selectedCategory,
                                selectedBrand,
                                modelText,
                                selectedCondition,
                                valuation.estimatedCashEgp
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CyberEmeraldPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("request_pickup_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ElectricMoped,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "اطلب مندوب لاستلام الجهاز والدفع فوراً",
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MetalStatItem(title: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = color)
        Text(title, fontSize = 9.sp, color = TextMedium)
    }
}
