package daniel.brian.fooddeliveryapp.util

fun String.shortenName():String{
    val mealNameArray = split(" ").filter { it != "and" }.take(2)
    return mealNameArray.joinToString(" ")
}