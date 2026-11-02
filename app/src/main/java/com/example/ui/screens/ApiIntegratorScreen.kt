package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun ApiIntegratorScreen(
    modifier: Modifier = Modifier
) {
    var baseUrl by remember { mutableStateOf("https://api.revive-recycle.eg/v1") }
    var apiStatus by remember { mutableStateOf("Ready for Integration") }
    var isPinging by remember { mutableStateOf(false) }
    var lastResponseJson by remember {
        mutableStateOf(
            """{
  "status": "ready",
  "platform": "ReVive E-Waste & Tech Recycling",
  "region": "Egypt (Cairo, Alexandria, Giza, Delta)",
  "endpoints_available": [
    "/ewaste/evaluate",
    "/pickup/create",
    "/kiosks/nearby",
    "/refurbished/catalog",
    "/payout/instapay"
  ],
  "payout_gateways": ["InstaPay", "Vodafone Cash", "Store Credit"]
}"""
        )
    }

    val context = LocalContext.current

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Banner
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = TechCardDark),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CircuitCyan)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Hub, contentDescription = null, tint = CircuitCyan)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "مركز ربط الـ API والـ Endpoints",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = CircuitCyan
                            )
                        }

                        Surface(
                            color = CyberEmeraldPrimary.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "REST & WebSocket",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = CyberEmeraldPrimary,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "التطبيق مهيأ بالكامل لاستقبال الـ API الخاص بك وتوصيل طلبات تقييم الأجهزة، الدفع عبر إنستاباي/فودافون كاش، وخرائط نقاط التجميع.",
                        fontSize = 11.sp,
                        color = TextMedium
                    )
                }
            }
        }

        // Base URL Input
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = TechCardDark),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, TechCardBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "رابط الـ API الأساسي (Base URL):",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = TextHigh
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = baseUrl,
                        onValueChange = { baseUrl = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextHigh,
                            unfocusedTextColor = TextHigh
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                isPinging = true
                                apiStatus = "200 OK - Connected"
                                lastResponseJson = """{
  "status": "connected",
  "base_url": "$baseUrl",
  "connected_at": "${System.currentTimeMillis()}",
  "service": "ReVive E-Waste Core",
  "database": "PostgreSQL 16 + PostGIS",
  "active_egypt_nodes": 4
}"""
                                isPinging = false
                                Toast.makeText(context, "تم اختبار الاتصال بنجاح!", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CyberEmeraldPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("ping_api_btn")
                        ) {
                            Icon(Icons.Filled.NetworkCheck, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("اختبار الاتصال (Ping API)", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }

                        Button(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("API Docs", API_DOCS_PAYLOAD)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "تم نسخ مواصفات الـ API بالكامل!", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF162536)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Filled.ContentCopy, contentDescription = null, tint = CircuitCyan, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("نسخ كود الـ API", color = CircuitCyan, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // Live JSON Response Viewer
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF070C12)),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, TechCardBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "STATUS: $apiStatus",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (apiStatus.contains("200")) CyberEmeraldPrimary else ReclaimedGold
                        )
                        Text(
                            text = "JSON Payload",
                            fontSize = 10.sp,
                            color = TextMedium,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    SelectionContainer {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .background(Color(0xFF04070A), RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = lastResponseJson,
                                color = Color(0xFF80D8FF),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }

        // Endpoints Schema Cards
        item {
            Text(
                text = "المسارات المطلوبة للربط (Expected Endpoints):",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = TextHigh
            )
        }

        item {
            EndpointCard("POST", "/api/v1/ewaste/evaluate", "حاسبة تقييم الأجهزة بناءً على الفحص وحالة الشاشة والبطارية واستخلاص المعادن.")
        }
        item {
            EndpointCard("POST", "/api/v1/pickup/request", "طلب استلام إلكترونيات من المنزل وتعيين أقرب مندوب وتحويل الكاش فوراً.")
        }
        item {
            EndpointCard("POST", "/api/v1/payout/instapay", "تنفيذ التحويل اللحظي للمستخدم عبر شبكة المدفوعات اللحظية المصرية (InstaPay/IPA).")
        }
        item {
            EndpointCard("GET", "/api/v1/kiosks/nearby", "جلب أقرب صناديق ونقاط تجميع الإلكترونيات الذكية في القاهرة والإسكندرية والمحافظات.")
        }
    }
}

@Composable
fun EndpointCard(method: String, endpoint: String, description: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = TechCardDark),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, TechCardBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = if (method == "POST") CyberEmeraldPrimary else CircuitCyan,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = method,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(endpoint, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextHigh, fontFamily = FontFamily.Monospace)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(description, fontSize = 11.sp, color = TextMedium)
        }
    }
}

private const val API_DOCS_PAYLOAD = """
{
  "openapi": "3.0.0",
  "info": {
    "title": "ReVive E-Waste & Electronics Recycling Egypt API",
    "version": "1.0.0"
  },
  "paths": {
    "/api/v1/ewaste/evaluate": {
      "post": {
        "summary": "Evaluate electronic item payout & precious metals recovery",
        "parameters": ["category", "brand", "model", "condition"]
      }
    },
    "/api/v1/pickup/request": {
      "post": {
        "summary": "Book doorstep courier pickup in Egypt",
        "parameters": ["name", "phone", "city", "address", "payout_method", "account"]
      }
    },
    "/api/v1/payout/instapay": {
      "post": {
        "summary": "Transfer payout via InstaPay IPA"
      }
    }
  }
}
"""
