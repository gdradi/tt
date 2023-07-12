package com.technogym.sdk.fitnessmachineservice.model

import android.os.Parcelable
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.OpCodeStatus

data class FTMStatusOp(
        var opCode: OpCodeStatus? = null,
        var parameter: Double? = null
)