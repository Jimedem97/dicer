package de.jimedem.dicer.data

sealed class Animation(val displayText: String, val dtoText: String) {
    object None: Animation("Keine Animation", "NONE")
    object Animation1: Animation("Animation 1", "ANIMATION1")
    object Animation2: Animation("Animation 2", "ANIMATION2")

    companion object{
        fun of(text: String): Animation{
            return when(text.uppercase()){
                "ANIMATION1" -> Animation1
                "ANIMATION2" -> Animation2
                else -> None
            }
        }
    }
}