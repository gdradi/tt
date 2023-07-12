package com.technogym.sdk.fitnessmachineservice.model.treadmill

data class MyRunPhysicalSensorData(
        var inductivePresence: Boolean = false,
        var opticalPresence: Boolean = false,
        var mainButtonPressed: Boolean = false,
        var speedJoystickState: Int = 0, //0 idle, 1 up, 2 down
        var inclinationJoystickState: Int = 0, //0 idle, 1 up, 2 down
)