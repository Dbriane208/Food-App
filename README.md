# FoodDeliveryApp! 🍹🍸

An Android app built with Views consuming [COCKTAIL](https://www.thecocktaildb.com/) and the [MEAL](https://www.themealdb.com/) APIs to help ordering food and drinks. The app basically demonstrates the use of Room database, the MVVM architecture and how to consume a RESTFUL api and handle the responses by displaying them in the UI.

# Design Inspiration
Below is the design that inspired and guided the UI of the application. Get it on [dribble](https://dribbble.com/shots/21826039-Food-App-Design)

# Features
- Displaying different types of food in various categories
- Adding different kinds of food and drinks to the cart and favorite section
- Deleting of items from the favorite and cart section

# Tech Stack

- <b>[Kotlin](https://developer.android.com/kotlin):</b> Kotlin is a JVM-compatible programming language. Google announced Kotlin as one of its officially supported programming languages in Android Studio.
- <b>[MVVM Architecture](https://developer.android.com/topic/architecture)</b>: The app is designed using the Model-View-ViewModel architectural pattern for better separation of concerns and maintainability.
- <b>[Retrofit](https://square.github.io/retrofit/)</b>: It's a straightforward network library used for network communications. This library allows us to easily capture JSON responses from web services and web APIs.Used for making network requests to fetch data from the [CocktailDB](https://www.thecocktaildb.com/) and the [MealDB](https://www.themealdb.com/) APIs.
- <b>[LiveData](https://developer.android.com/topic/libraries/architecture/livedata)</b>: LiveData respects the lifecycle of other app components, like activities, fragments, and services, in contrast to regular observables. LiveData only updates app component observers that are in an active lifecycle state thanks to this awareness.
- <b>[Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)</b>: A design pattern for concurrency that you can apply to Android to make asynchronous code execution simpler.
- <b>[Room](https://developer.android.com/training/data-storage/sqlite)</b>: The Room persistence library enables fluid database access while utilizing all of SQLite's capabilities by providing an abstraction layer over SQLite,.
- <b>[Timber](https://github.com/JakeWharton/timber)</b>: A logger with a small, extensible API which provides utility on top of Android's normal Log class.
- <b>[Glide](https://github.com/bumptech/glide)</b>: An image loading and caching library for Android focused on smooth scrolling.
- <b>[Intuit](https://github.com/intuit/sdp)</b>: An Android lib that provides a new size unit - sdp (scalable dp). This size unit scales with the screen size.
- <b>[View Model](https://github.com/bumptech/glide)</b>: The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way.
- <b>[Gson](https://github.com/square/retrofit/blob/master/retrofit-converters/gson/README.md)</b>: JSON Parser,used to parse requests on the data layer for Entities and understands Kotlin non-nullable and default parameters.
  

## Setup Instructions

- Clone the repository to your local machine.
- Open the project in Android Studio.
- Build and run the app on your preferred Android emulator or physical device.
- Allow users to search for meals

# Project Images
<div style="display:flex;">
    <img src="https://github.com/Dbriane208/FoodDeliveryApp/blob/main/Images/Home%20Screen.png" alt="auth_start" width="200"/>
    <img src="https://github.com/Dbriane208/FoodDeliveryApp/blob/main/Images/Favorites.png" alt="auth_reg_login" width="200"/>
    <img src="https://github.com/Dbriane208/FoodDeliveryApp/blob/main/Images/Search%20feature.png" alt="auth_login" width="200"/>
    <img src="https://github.com/Dbriane208/FoodDeliveryApp/blob/main/Images/Demo%201.png" alt="auth_reg" width="200">
</div>

  ## Dependancies
  
      
    //intuit
    implementation 'com.intuit.sdp:sdp-android:1.0.6'
    implementation 'com.intuit.ssp:ssp-android:1.0.6'

    //retrofit and gson converter
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation 'com.squareup.retrofit2:converter-gson:2.3.0'

    //glide
    implementation 'com.github.bumptech.glide:glide:4.13.0'

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
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    //noinspection KaptUsageInsteadOfKsp
    kapt("androidx.room:room-compiler:$room_version")
  
    
## Contributing
If you would like to contribute to this project, feel free to fork the repository, make your changes, and create a pull request. Your contributions is much appreciated!
