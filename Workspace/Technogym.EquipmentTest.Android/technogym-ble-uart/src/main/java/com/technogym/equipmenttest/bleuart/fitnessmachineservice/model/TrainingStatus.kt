package com.technogym.sdk.fitnessmachineservice.model

enum class TrainingStatus {
    Other,
    Idle,
    WarmingUp,
    LowIntensityInterval,
    HighIntensityInterval,
    RecoveryInterval,
    Isometric,
    HearRateControl,
    FitnessTest,
    SpeedOutsideControlRegionLow,
    SpeedOutsideControlRegionHigh,
    CoolDown,
    WattControl,
    ManualModeQuickStart,
    PreWorkout,
    PostWorkout
}