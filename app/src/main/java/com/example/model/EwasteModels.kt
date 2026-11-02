package com.example.model

enum class DeviceCategory(val titleAr: String, val titleEn: String, val iconName: String) {
    SMARTPHONES("هواتف ذكية", "Smartphones", "Smartphone"),
    LAPTOPS_PCS("لابتوب وأجهزة كمبيوتر", "Laptops & PCs", "Laptop"),
    TABLETS("تابلت وآيباد", "Tablets & iPads", "Tablet"),
    PCBS_COMPONENTS("لوحات إلكترونية ورامات", "PCBs & Components", "Memory"),
    BATTERIES_POWER("بطاريات وباور بانك", "Batteries & Power", "BatteryChargingFull"),
    CABLES_CHARGERS("كابلات وشواحن وخردة", "Cables & E-Scrap", "Cable")
}

enum class DeviceCondition(
    val titleAr: String,
    val titleEn: String,
    val valuationMultiplier: Double,
    val tagColorHex: Long
) {
    LIKE_NEW_WORKING(
        "شغال بحالة ممتازة",
        "Functional & Clean",
        1.0,
        0xFF00C853
    ),
    MINOR_FAULT(
        "شغال مع بطارية ضعيفة / خدوش",
        "Minor Fault / Worn Battery",
        0.75,
        0xFF00E5FF
    ),
    BROKEN_SCREEN(
        "شاشة مكسورة وبوردة سليمة",
        "Cracked Screen / Good Board",
        0.48,
        0xFFFF9800
    ),
    DEAD_BOOT(
        "قاطع باور / لقطع الغيار",
        "Dead Boot / For Parts",
        0.28,
        0xFFE07A5F
    ),
    RAW_PCB_SCRAP(
        "خردة إلكترونية لاستخلاص الذهب والنحاس",
        "E-Waste Scrap for Metal Recovery",
        0.18,
        0xFFFFD700
    )
}

data class EwasteEvaluationResult(
    val estimatedCashEgp: Double,
    val walletBonusEgp: Double, // +20% if taken as ReVive store credit for refurbished tech
    val ecoPoints: Int,
    val goldRecoveredMg: Double,
    val copperRecoveredG: Double,
    val co2DivertedKg: Double
)

enum class PayoutMethod(val titleAr: String, val titleEn: String, val hint: String) {
    INSTAPAY("إنستاباي (InstaPay)", "InstaPay Direct", "تحويل لحظي على عنوان الدفع اللحظي IPA"),
    VODAFONE_CASH("فودافون كاش ومحافظ إلكترونية", "Vodafone Cash / E-Wallet", "إرسال الكاش فور استلام الجهاز وفحصه"),
    REVIVE_STORE_CREDIT("رصيد محفظة ريفايف (+20% بونص)", "ReVive Wallet (+20% Bonus)", "شراء إلكترونيات مجددة بضمان سنة"),
    CASH_ON_PICKUP("كاش عند الاستلام بالمنزل", "Cash on Doorstep Pickup", "استلام الكاش مباشرة من مندوب ريفايف")
}

data class EwastePickupOrder(
    val id: String,
    val deviceCategory: DeviceCategory,
    val brand: String,
    val modelName: String,
    val condition: DeviceCondition,
    val estimatedCashEgp: Double,
    val payoutMethod: PayoutMethod,
    val payoutAccount: String,
    val customerName: String,
    val customerPhone: String,
    val city: String,
    val address: String,
    val status: String, // "REQUESTED", "COURIER_ASSIGNED", "TESTED_AT_LAB", "PAID_OUT"
    val timestamp: Long = System.currentTimeMillis()
)

data class RefurbishedProduct(
    val id: String,
    val name: String,
    val specs: String,
    val originalPriceEgp: Double,
    val refurbPriceEgp: Double,
    val batteryHealthPct: Int,
    val warrantyMonths: Int = 12,
    val conditionGrade: String = "Certified Grade A+",
    val carbonSavedKg: Double,
    val eWasteDivertedGrams: Double,
    val inStock: Boolean = true
)

data class DropOffKiosk(
    val id: String,
    val nameAr: String,
    val nameEn: String,
    val area: String,
    val city: String,
    val address: String,
    val acceptsBatteries: Boolean = true,
    val acceptsLargeElectronics: Boolean = true,
    val bonusMultiplier: String = "+10% Points"
)
