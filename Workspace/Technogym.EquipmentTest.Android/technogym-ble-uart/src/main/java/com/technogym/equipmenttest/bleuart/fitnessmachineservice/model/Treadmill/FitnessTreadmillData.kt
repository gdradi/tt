package com.technogym.sdk.fitnessmachineservice.model.treadmill

data class FitnessTreadmillData(
        var instantaneousSpeed: Double = -1.0,
        var averageSpeed: Double = 0.0,
        var totalDistance: Int = -1,
        var inclination: Double = -1.0,
        var rampAngleSetting: Double = -1.0,
        var elevationGainPositive: Double = -1.0,
        var elevationGainNegative: Double = -1.0,
        var instantaneousPace: Double = -1.0,
        var averagePace: Double = 0.0,
        var totalEnergy: Int = -1,
        var energyPerHour: Int = -1,
        var energyPerMinute: Int = -1,
        var heartRate: Int = -1,
        var metabolicEquivalent: Double = -1.0,
        var elapsedTime: Int = -1,
        var remainingTime: Int = -1,
        var forceOnBelt: Int = -1,
        var powerOutput: Int = -1
)