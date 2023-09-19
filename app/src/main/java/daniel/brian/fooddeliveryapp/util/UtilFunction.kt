package daniel.brian.fooddeliveryapp.util

//This is an extension function that can be called on any instance of a string
fun String.shortenName():String{
    //it takes the original mealName and splits it into an array of strings.
    // It filters the substrings that are equal to "and" then combines the first two substrings using joinToString method.
    val mealNameArray = split(" ").filter { it != "and" && it != "&"}.take(2)
    return mealNameArray.joinToString(" ")
}