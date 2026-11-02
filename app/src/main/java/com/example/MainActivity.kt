package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth.AuthManager
import com.example.data.EwasteRepository
import com.example.model.DeviceCategory
import com.example.model.DeviceCondition
import com.example.ui.components.AuthScreen
import com.example.ui.components.PickupRequestDialog
import com.example.ui.components.ReViveHeader
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

enum class EwasteTab {
    RECYCLE_CALCULATOR,
    DROP_OFF_KIOSKS,
    REFURB_STORE,
    WALLET_IMPACT,
    API_INTEGRATOR
}

class MainActivity : ComponentActivity() {

    private val repository = EwasteRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ReViveTheme {
                ReViveEwasteApp(repository = repository)
            }
        }
    }
}

@Composable
fun ReViveEwasteApp(repository: EwasteRepository) {
    val context = LocalContext.current
    var currentUser by remember { mutableStateOf<FirebaseUser?>(AuthManager.currentUser) }
    var guestMode by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            currentUser = auth.currentUser
        }
        AuthManager.auth.addAuthStateListener(listener)
        onDispose {
            AuthManager.auth.removeAuthStateListener(listener)
        }
    }

    if (currentUser == null && !guestMode) {
        AuthScreen(
            onAuthSuccess = { user ->
                currentUser = user
            },
            onContinueAsGuest = {
                guestMode = true
            }
        )
    } else {
        var currentTab by remember { mutableStateOf(EwasteTab.RECYCLE_CALCULATOR) }
        var showPickupDialog by remember { mutableStateOf(false) }

        var pendingCategory by remember { mutableStateOf(DeviceCategory.SMARTPHONES) }
        var pendingBrand by remember { mutableStateOf("Apple") }
        var pendingModel by remember { mutableStateOf("iPhone 12") }
        var pendingCondition by remember { mutableStateOf(DeviceCondition.LIKE_NEW_WORKING) }
        var pendingCash by remember { mutableStateOf(5400.0) }

        val impactStats by repository.impactStats.collectAsState()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                ReViveHeader(
                    currentUser = currentUser,
                    walletBalanceEgp = impactStats.totalCashEarnedEgp,
                    onOpenWallet = { currentTab = EwasteTab.WALLET_IMPACT },
                    onAuthClick = {
                        if (currentUser != null) {
                            AuthManager.signOut(context) {
                                currentUser = null
                                guestMode = false
                            }
                        } else {
                            guestMode = false
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .testTag("ewaste_bottom_nav"),
                    containerColor = TechCardDark,
                    tonalElevation = 8.dp
                ) {
                    NavigationBarItem(
                        selected = currentTab == EwasteTab.RECYCLE_CALCULATOR,
                        onClick = { currentTab = EwasteTab.RECYCLE_CALCULATOR },
                        icon = {
                            Icon(
                                imageVector = if (currentTab == EwasteTab.RECYCLE_CALCULATOR) Icons.Filled.Autorenew else Icons.Outlined.Autorenew,
                                contentDescription = "Recycle"
                            )
                        },
                        label = { Text("تدوير وبيع", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = CyberEmeraldPrimary,
                            selectedTextColor = CyberEmeraldPrimary,
                            indicatorColor = CyberEmeraldPrimary.copy(alpha = 0.2f),
                            unselectedIconColor = TextMedium,
                            unselectedTextColor = TextMedium
                        ),
                        modifier = Modifier.testTag("nav_recycle")
                    )

                    NavigationBarItem(
                        selected = currentTab == EwasteTab.DROP_OFF_KIOSKS,
                        onClick = { currentTab = EwasteTab.DROP_OFF_KIOSKS },
                        icon = {
                            Icon(
                                imageVector = if (currentTab == EwasteTab.DROP_OFF_KIOSKS) Icons.Filled.Place else Icons.Outlined.Place,
                                contentDescription = "Drop-off Kiosks"
                            )
                        },
                        label = { Text("صناديق التجميع", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = CircuitCyan,
                            selectedTextColor = CircuitCyan,
                            indicatorColor = CircuitCyan.copy(alpha = 0.2f),
                            unselectedIconColor = TextMedium,
                            unselectedTextColor = TextMedium
                        ),
                        modifier = Modifier.testTag("nav_kiosks")
                    )

                    NavigationBarItem(
                        selected = currentTab == EwasteTab.REFURB_STORE,
                        onClick = { currentTab = EwasteTab.REFURB_STORE },
                        icon = {
                            Icon(
                                imageVector = if (currentTab == EwasteTab.REFURB_STORE) Icons.Filled.Devices else Icons.Outlined.Devices,
                                contentDescription = "Refurb Store"
                            )
                        },
                        label = { Text("مجدد بضمان", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = CyberEmeraldPrimary,
                            selectedTextColor = CyberEmeraldPrimary,
                            indicatorColor = CyberEmeraldPrimary.copy(alpha = 0.2f),
                            unselectedIconColor = TextMedium,
                            unselectedTextColor = TextMedium
                        ),
                        modifier = Modifier.testTag("nav_store")
                    )

                    NavigationBarItem(
                        selected = currentTab == EwasteTab.WALLET_IMPACT,
                        onClick = { currentTab = EwasteTab.WALLET_IMPACT },
                        icon = {
                            Icon(
                                imageVector = if (currentTab == EwasteTab.WALLET_IMPACT) Icons.Filled.AccountBalanceWallet else Icons.Outlined.AccountBalanceWallet,
                                contentDescription = "Wallet"
                            )
                        },
                        label = { Text("المحفظة والأثر", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = ReclaimedGold,
                            selectedTextColor = ReclaimedGold,
                            indicatorColor = ReclaimedGold.copy(alpha = 0.2f),
                            unselectedIconColor = TextMedium,
                            unselectedTextColor = TextMedium
                        ),
                        modifier = Modifier.testTag("nav_wallet")
                    )

                    NavigationBarItem(
                        selected = currentTab == EwasteTab.API_INTEGRATOR,
                        onClick = { currentTab = EwasteTab.API_INTEGRATOR },
                        icon = {
                            Icon(
                                imageVector = if (currentTab == EwasteTab.API_INTEGRATOR) Icons.Filled.Terminal else Icons.Outlined.Terminal,
                                contentDescription = "API"
                            )
                        },
                        label = { Text("ربط الـ API", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = CircuitCyan,
                            selectedTextColor = CircuitCyan,
                            indicatorColor = CircuitCyan.copy(alpha = 0.2f),
                            unselectedIconColor = TextMedium,
                            unselectedTextColor = TextMedium
                        ),
                        modifier = Modifier.testTag("nav_api")
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentTab) {
                    EwasteTab.RECYCLE_CALCULATOR -> {
                        EwasteRecycleScreen(
                            repository = repository,
                            onRequestPickup = { cat, brand, model, cond, cash ->
                                pendingCategory = cat
                                pendingBrand = brand
                                pendingModel = model
                                pendingCondition = cond
                                pendingCash = cash
                                showPickupDialog = true
                            }
                        )
                    }
                    EwasteTab.DROP_OFF_KIOSKS -> {
                        DropOffMapScreen(repository = repository)
                    }
                    EwasteTab.REFURB_STORE -> {
                        RefurbishedStoreScreen(repository = repository)
                    }
                    EwasteTab.WALLET_IMPACT -> {
                        WalletAndImpactScreen(repository = repository)
                    }
                    EwasteTab.API_INTEGRATOR -> {
                        ApiIntegratorScreen()
                    }
                }
            }

            if (showPickupDialog) {
                PickupRequestDialog(
                    category = pendingCategory,
                    brand = pendingBrand,
                    modelName = pendingModel,
                    condition = pendingCondition,
                    estimatedCash = pendingCash,
                    onDismiss = { showPickupDialog = false },
                    onSubmit = { name, phone, city, address, payoutMethod, payoutAccount ->
                        repository.submitPickupOrder(
                            category = pendingCategory,
                            brand = pendingBrand,
                            modelName = pendingModel,
                            condition = pendingCondition,
                            estimatedCash = pendingCash,
                            payoutMethod = payoutMethod,
                            payoutAccount = payoutAccount,
                            name = name,
                            phone = phone,
                            city = city,
                            address = address
                        )
                        showPickupDialog = false
                        currentTab = EwasteTab.WALLET_IMPACT
                    }
                )
            }
        }
    }
}
