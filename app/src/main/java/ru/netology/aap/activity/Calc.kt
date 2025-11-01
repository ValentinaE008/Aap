package ru.netology.aap.activity

object Calc {
    fun intToText(like: Int): String {
        return when (like) {
            in 0..999 -> like.toString()
            in 1000..9999 -> {
                val hundreds = (like / 100).toDouble() / 10
                String.format("%.1fK", hundreds)
            }

            in 10000..999999 -> {
                "${like / 1000}K"
            }

            in 1000000..999999999 -> {
                val millions = (like / 100000).toDouble() / 10
                String.format("%.1fM", millions)
            }

            else -> "Более 1 Billion"
        }
    }
}
