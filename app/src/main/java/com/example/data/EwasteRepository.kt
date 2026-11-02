package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

data class UserImpactStats(
    val totalEwasteRecycledKg: Double = 18.4,
    val goldRecoveredMg: Double = 642.0, // milligrams of 24k gold from recovered PCBs
    val copperRecoveredG: Double = 4850.0,
    val co2PreventedKg: Double = 142.0,
    val totalCashEarnedEgp: Double = 8450.0,
    val ecoPointsBalance: Int = 1850
)

class EwasteRepository {

    private val _impactStats = MutableStateFlow(UserImpactStats())
    val impactStats: StateFlow<UserImpactStats> = _impactStats.asStateFlow()

    private val _pickupOrders = MutableStateFlow(
        listOf(
            EwastePickupOrder(
                id = "REC-EGY-4921",
                deviceCategory = DeviceCategory.LAPTOPS_PCS,
                brand = "Dell",
                modelName = "Inspiron 15 (8th Gen i5)",
                condition = DeviceCondition.BROKEN_SCREEN,
                estimatedCashEgp = 3200.0,
                payoutMethod = PayoutMethod.INSTAPAY,
                payoutAccount = "user@instapay",
                customerName = "كريم سامح",
                customerPhone = "01098765432",
                city = "القاهرة - مدينة نصر",
                address = "شارع عباس العقاد - عمارة 14",
                status = "COURIER_ASSIGNED"
            ),
            EwastePickupOrder(
                id = "REC-EGY-3819",
                deviceCategory = DeviceCategory.SMARTPHONES,
                brand = "Apple",
                modelName = "iPhone 11 (64GB)",
                condition = DeviceCondition.LIKE_NEW_WORKING,
                estimatedCashEgp = 7800.0,
                payoutMethod = PayoutMethod.VODAFONE_CASH,
                payoutAccount = "01012345678",
                customerName = "أحمد رضوان",
                customerPhone = "01012345678",
                city = "الجيزة - الدقي",
                address = "شارع التحرير - برج الأطباء",
                status = "PAID_OUT"
            )
        )
    )
    val pickupOrders: StateFlow<List<EwastePickupOrder>> = _pickupOrders.asStateFlow()

    val dropOffKiosks = listOf(
        DropOffKiosk(
            id = "kiosk-01",
            nameAr = "نقطة تجميع القرية الذكية (Smart Village)",
            nameEn = "Smart Village E-Waste Smart Bin",
            area = "مبنى B24 - البوابة الرئيسية",
            city = "الجيزة",
            address = "طريق مصر إسكندرية الصحراوي، القرية الذكية",
            acceptsBatteries = true,
            acceptsLargeElectronics = true,
            bonusMultiplier = "+15% بونص نقاط"
        ),
        DropOffKiosk(
            id = "kiosk-02",
            nameAr = "نقطة تجميع كايرو فيستيفال سيتي",
            nameEn = "CFC Mall Green Drop Box",
            area = "التجمع الخامس - بجوار مدخل إيكيا",
            city = "القاهرة الجديدة",
            address = "الطريق الدائري، القاهرة الجديدة",
            acceptsBatteries = true,
            acceptsLargeElectronics = false,
            bonusMultiplier = "+10% بونص نقاط"
        ),
        DropOffKiosk(
            id = "kiosk-03",
            nameAr = "محطة سموحة لإعادة التدوير",
            nameEn = "Smouha Green Tech Depot",
            area = "ميدان فيكتور عمانويل",
            city = "الإسكندرية",
            address = "شارع فوزي معاذ، سموحة، الإسكندرية",
            acceptsBatteries = true,
            acceptsLargeElectronics = true,
            bonusMultiplier = "+12% بونص نقاط"
        ),
        DropOffKiosk(
            id = "kiosk-04",
            nameAr = "نقطة دمنهور المركزية للمخلفات التقنية",
            nameEn = "Damanhour Tech Hub Kiosk",
            area = "مجمع الكليات - شارع الجمهورية",
            city = "دمنهور - البحيرة",
            address = "شارع عبد السلام الشاذلي، دمنهور",
            acceptsBatteries = true,
            acceptsLargeElectronics = true,
            bonusMultiplier = "+20% بونص مضاعف"
        )
    )

    val refurbishedCatalog = listOf(
        RefurbishedProduct(
            id = "ref-001",
            name = "Lenovo ThinkPad T480s (Intel i7 8th Gen, 16GB RAM, 512GB NVMe)",
            specs = "شاشة 14 بوصة FHD IPS • بطارية جديدة بنسبة 96% • شهادة فحص واختبار 42 نقطة • كيبورد مضيئة",
            originalPriceEgp = 22000.0,
            refurbPriceEgp = 11400.0,
            batteryHealthPct = 96,
            warrantyMonths = 12,
            carbonSavedKg = 180.0,
            eWasteDivertedGrams = 1350.0
        ),
        RefurbishedProduct(
            id = "ref-002",
            name = "Apple iPhone 12 Pro Max 128GB Pacific Blue (Certified)",
            specs = "بطارية أصلية 92% • شاشة Super Retina XDR • خالي من الخدوش • تقرير فحص 3uTools بنسبة 98%",
            originalPriceEgp = 38000.0,
            refurbPriceEgp = 23900.0,
            batteryHealthPct = 92,
            warrantyMonths = 12,
            carbonSavedKg = 72.0,
            eWasteDivertedGrams = 228.0
        ),
        RefurbishedProduct(
            id = "ref-003",
            name = "Dell Latitude 7490 Ultrabook (16GB RAM, 256GB SSD)",
            specs = "جسم كاربون فايبر فائق المتانة • بطارية ممتازة 89% • ويندوز 11 برو أصلي • شاحن Dell أصلي Type-C",
            originalPriceEgp = 18500.0,
            refurbPriceEgp = 8900.0,
            batteryHealthPct = 89,
            warrantyMonths = 12,
            carbonSavedKg = 155.0,
            eWasteDivertedGrams = 1400.0
        ),
        RefurbishedProduct(
            id = "ref-004",
            name = "Anker PowerCore 20,000mAh PD Fast Charge (Renewed Cells)",
            specs = "خلايا ليثيوم بوليمر مجددة ومعايرة • دعم PD 20W • منافذ Type-C و USB-A مزدوجة • شهادة أمان CE",
            originalPriceEgp = 2100.0,
            refurbPriceEgp = 890.0,
            batteryHealthPct = 98,
            warrantyMonths = 6,
            carbonSavedKg = 18.0,
            eWasteDivertedGrams = 350.0
        )
    )

    fun calculateValuation(
        category: DeviceCategory,
        brand: String,
        modelText: String,
        condition: DeviceCondition
    ): EwasteEvaluationResult {
        // Base category values in Egypt Market
        val basePrice = when (category) {
            DeviceCategory.SMARTPHONES -> {
                when {
                    brand.contains("Apple", ignoreCase = true) || modelText.contains("iPhone", ignoreCase = true) -> 8500.0
                    brand.contains("Samsung", ignoreCase = true) -> 6200.0
                    else -> 3800.0
                }
            }
            DeviceCategory.LAPTOPS_PCS -> {
                when {
                    brand.contains("Apple", ignoreCase = true) || modelText.contains("MacBook", ignoreCase = true) -> 14000.0
                    brand.contains("Dell", ignoreCase = true) || brand.contains("ThinkPad", ignoreCase = true) -> 7500.0
                    else -> 5200.0
                }
            }
            DeviceCategory.TABLETS -> 4200.0
            DeviceCategory.PCBS_COMPONENTS -> 1800.0
            DeviceCategory.BATTERIES_POWER -> 450.0
            DeviceCategory.CABLES_CHARGERS -> 250.0
        }

        val estimatedCash = (basePrice * condition.valuationMultiplier).coerceAtLeast(100.0)
        val walletBonus = estimatedCash * 1.20 // +20% bonus

        val goldMg = when (category) {
            DeviceCategory.PCBS_COMPONENTS -> 120.0
            DeviceCategory.SMARTPHONES -> 35.0
            DeviceCategory.LAPTOPS_PCS -> 75.0
            else -> 10.0
        }

        val copperG = when (category) {
            DeviceCategory.CABLES_CHARGERS -> 280.0
            DeviceCategory.LAPTOPS_PCS -> 190.0
            DeviceCategory.PCBS_COMPONENTS -> 140.0
            else -> 45.0
        }

        val co2Kg = estimatedCash * 0.015

        return EwasteEvaluationResult(
            estimatedCashEgp = estimatedCash,
            walletBonusEgp = walletBonus,
            ecoPoints = (estimatedCash / 5).toInt(),
            goldRecoveredMg = goldMg,
            copperRecoveredG = copperG,
            co2DivertedKg = co2Kg
        )
    }

    fun submitPickupOrder(
        category: DeviceCategory,
        brand: String,
        modelName: String,
        condition: DeviceCondition,
        estimatedCash: Double,
        payoutMethod: PayoutMethod,
        payoutAccount: String,
        name: String,
        phone: String,
        city: String,
        address: String
    ): EwastePickupOrder {
        val order = EwastePickupOrder(
            id = "REC-EGY-${Random.nextInt(1000, 9999)}",
            deviceCategory = category,
            brand = brand,
            modelName = modelName,
            condition = condition,
            estimatedCashEgp = estimatedCash,
            payoutMethod = payoutMethod,
            payoutAccount = payoutAccount,
            customerName = name,
            customerPhone = phone,
            city = city,
            address = address,
            status = "REQUESTED"
        )

        _pickupOrders.update { listOf(order) + it }

        // Update impact stats
        _impactStats.update {
            it.copy(
                totalEwasteRecycledKg = it.totalEwasteRecycledKg + 1.2,
                goldRecoveredMg = it.goldRecoveredMg + 35.0,
                copperRecoveredG = it.copperRecoveredG + 80.0,
                co2PreventedKg = it.co2PreventedKg + 14.0,
                ecoPointsBalance = it.ecoPointsBalance + (estimatedCash / 5).toInt()
            )
        }

        return order
    }
}
