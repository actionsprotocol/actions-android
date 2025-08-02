package app.actionsfun.common.ui.avatar

import app.actionsfun.common.ui.R

val String.avatarByWalletAddress: Int
    get() {
        var hash = 0
        for (i in 0 until minOf(8, this.length)) {
            hash = hash * 31 + this[i].code
        }

        val avatarIndex = kotlin.math.abs(hash) % 7

        return when (avatarIndex) {
            0 -> R.drawable.ic_avatar_pepe_0
            1 -> R.drawable.ic_avatar_pepe_1
            2 -> R.drawable.ic_avatar_pepe_2
            3 -> R.drawable.ic_avatar_pepe_3
            4 -> R.drawable.ic_avatar_pepe_4
            5 -> R.drawable.ic_avatar_pepe_5
            6 -> R.drawable.ic_avatar_pepe_6
            else -> R.drawable.ic_avatar_pepe_0
        }
    }
