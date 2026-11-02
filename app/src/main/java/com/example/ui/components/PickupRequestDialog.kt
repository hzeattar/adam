package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.DeviceCategory
import com.example.model.DeviceCondition
import com.example.model.PayoutMethod
import com.example.ui.theme.*

@Composable
fun PickupRequestDialog(
    category: DeviceCategory,
    brand: String,
    modelName: String,
    condition: DeviceCondition,
    estimatedCash: Double,
    onDismiss: () -> Unit,
    onSubmit: (
        name: String,
        phone: String,
        city: String,
        address: String,
        payoutMethod: PayoutMethod,
        payoutAccount: String
    ) -> Unit
) {
    var name by remember { mutableStateOf("محمد علي") }
    var phone by remember { mutableStateOf("01012345678") }
    var city by remember { mutableStateOf("القاهرة - المعادي") }
    var address by remember { mutableStateOf("شارع النصر - عمارة 28 - شقة 5") }
    var selectedPayout by remember { mutableStateOf(PayoutMethod.INSTAPAY) }
    var payoutAccount by remember { mutableStateOf("mohamed@instapay") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = TechCardDark,
            border = androidx.compose.foundation.BorderStroke(1.dp, CircuitCyan.copy(alpha = 0.5f)),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "طلب استلام إلكترونيات من المنزل",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = CyberEmeraldPrimary
                        )
                        Text(
                            text = "ReVive Eco-Courier Doorstep Pickup",
                            fontSize = 10.sp,
                            color = TextMedium
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Filled.Close, contentDescription = "Close", tint = TextMedium)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Item Summary Badge
                Surface(
                    color = Color(0xFF09121B),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("$brand - $modelName", fontWeight = FontWeight.Bold, color = TextHigh, fontSize = 12.sp)
                            Text(condition.titleAr, fontSize = 10.sp, color = Color(condition.tagColorHex))
                        }
                        Text(
                            "${estimatedCash.toInt()} ج.م",
                            fontWeight = FontWeight.ExtraBold,
                            color = CyberEmeraldPrimary,
                            fontSize = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Contact & Address
                Text("بيانات الاستلام في مصر:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = CircuitCyan)
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("الاسم بالكامل") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextHigh,
                        unfocusedTextColor = TextHigh
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("رقم الهاتف (مصر)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextHigh,
                        unfocusedTextColor = TextHigh
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("المدينة / المحافظة") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextHigh,
                        unfocusedTextColor = TextHigh
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("العنوان بالتفصيل ورقم الشقة") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextHigh,
                        unfocusedTextColor = TextHigh
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Payout Method Selector
                Text("طريقة استلام أموالك:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = CircuitCyan)
                Spacer(modifier = Modifier.height(6.dp))

                PayoutMethod.values().forEach { method ->
                    val isSel = method == selectedPayout
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSel) CircuitCyan.copy(alpha = 0.15f) else Color(0xFF0F1A26),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSel) CircuitCyan else TechCardBorder
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                            .clickable {
                                selectedPayout = method
                                payoutAccount = when (method) {
                                    PayoutMethod.INSTAPAY -> "user@instapay"
                                    PayoutMethod.VODAFONE_CASH -> phone
                                    PayoutMethod.REVIVE_STORE_CREDIT -> "المحفظة الإلكترونية (+20% بونص)"
                                    PayoutMethod.CASH_ON_PICKUP -> "استلام كاش يد بيد"
                                }
                            }
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(method.titleAr, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextHigh)
                            Text(method.hint, fontSize = 10.sp, color = TextMedium)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = payoutAccount,
                    onValueChange = { payoutAccount = it },
                    label = { Text("عنوان التحويل / رقم المحفظة") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextHigh,
                        unfocusedTextColor = TextHigh
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onSubmit(name, phone, city, address, selectedPayout, payoutAccount)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("confirm_pickup_order_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = CyberEmeraldPrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "تأكيد حجز المندوب واستلام الكاش",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
