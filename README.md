## Fake Online Meal Shopping App

This is a simple and mock online meal shopping app built using Kotlin and follows the MVVM architecture pattern. It utilizes Retrofit for API communication, Room for local data storage, and LiveData for observing data changes.
The app fetches meal and cocktail information from the MealDB and CocktailDB APIs, respectively. It provides a user-friendly interface for users to browse and "shop" for meals and cocktails.

## Design
- Attached is the [Dribble design](https://dribbble.com/shots/21826039-Food-App-Design) I was following when building the app with a few changes on the design

## Features

- Browse a variety of meals and cocktails.
- View details of a specific meal or cocktail.
- Save favorite meals and cocktails.
- Browse saved favorite meals and cocktails.
- Simple and intuitive user interface.
- Pay for meals as you check out

 ## Features not yet implemented(will work on them soon)
 
- Authentication
- Adding a meal to a cart
- Payment methods
- User profile segment

## Demo

<div style="display: flex; flex-direction: row;">
  <img src="https://github.com/Dbriane208/FoodDeliveryApp/blob/main/Images/Home%20Screen.png" alt="Image 1" width="300" style="margin-right: 10px;">
  <img src="https://github.com/Dbriane208/FoodDeliveryApp/blob/main/Images/Demo%201.png" alt="Image 2" width="300" style="margin-right: 10px;">
  <img src="https://github.com/Dbriane208/FoodDeliveryApp/blob/main/Images/Favorites.png" alt="Image 3" width="300" style="margin-right: 10px;">
  <img src="https://github.com/Dbriane208/FoodDeliveryApp/blob/main/Images/Search%20feature.png" alt="Image 3" width="300" style="margin-right: 10px;">
  <img src="https://github.com/Dbriane208/FoodDeliveryApp/blob/main/Images/categories%20page.png" alt="Image 3" width="300" style="margin-right: 10px;">
</div>

## Technologies Used

- <b>[Kotlin](https://kotlinlang.org/)</b>: The programming language used for building the app.
- <b>[MVVM Architecture](https://developer.android.com/topic/architecture)</b>: The app is designed using the Model-View-ViewModel architectural pattern for better separation of concerns and maintainability.
- <b>[Retrofit](https://square.github.io/retrofit/)</b>: Used for making network requests to fetch data from the MealDB and CocktailDB APIs.
- <b>[Room](https://developer.android.com/jetpack/androidx/releases/room)</b>: Utilized for local storage of favorite meals and cocktails.
- <b>[LiveData](https://developer.android.com/topic/libraries/architecture/livedata)</b>: Used for observing changes in data and updating the UI accordingly.
- <b>[NavGraph](https://developer.android.com/guide/navigation/get-started)</b>: Used to implement the navigation of various fragments on the app.
- <b>[Timber](https://github.com/JakeWharton/timber)</b>: A logger with a small, extensible API which provides utility on top of Android's normal Log class.

## Setup Instructions

- Clone the repository to your local machine.
- Open the project in Android Studio.
- Build and run the app on your preferred Android emulator or physical device.
- Explore the app and enjoy browsing meals and cocktails!

## Dependancies
  
    def nav_version = "2.7.0"
    def lifecycle_version = "2.4.0-rc01"
    def room_version = "2.5.2"

    //intuit
    implementation 'com.intuit.sdp:sdp-android:1.0.6'
    implementation 'com.intuit.ssp:ssp-android:1.0.6'

    //retrofit and gson converter
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation 'com.squareup.retrofit2:converter-gson:2.3.0'

    //glide
    implementation 'com.github.bumptech.glide:glide:4.12.0'

    //videoModel mvvm
    //noinspection GradleDependency
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    //noinspection GradleDependency
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
    implementation "android.arch.lifecycle:extensions:1.1.1"
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2"

    //timber
    implementation 'com.jakewharton.timber:timber:5.0.1'

    //room
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

## API Usage

- <b>MealDB API</b>: The app fetches meal data using the MealDB API. For more information, visit [MealDB API](https://www.themealdb.com/api.php).
- <b>CocktailDB API</b>: The app fetches cocktail data using the CocktailDB API. For more information, visit [CocktailDB API](https://thecocktaildb.com/api.php).

## Contributing
If you would like to contribute to this project, feel free to fork the repository, make your changes, and create a pull request. Your contributions is much appreciated!
