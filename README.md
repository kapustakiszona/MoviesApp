# :clapper:MoviesApp - The Movie Database (TMDB):bulb: API Android Application
An android project with MVVM, Coroutines, Retrofit, Room, RecyclerView, ViewModel, LiveData, Coil, Hilt and TMDB API.
## :open_book:Introduction 
This app shows an up-to-date list of the most popular films according to The Movies Database. When you click on a film you like, you can go to the details screen with more detailed description, cast, etc. You can also filter the list by genre or use the search bar.
## :clipboard:Features
+ Saves the list of films to a local database.
+ Updating the list with pull-to-refresh.
+ Filter by genre.
+ Search bar.
## :film_strip:Preview
<details>
  <summary>Click me</summary>
  
![](https://github.com/kapustakiszona/MoviesApp/blob/master/docs/1.gif) ![](https://github.com/kapustakiszona/MoviesApp/blob/master/docs/2.gif)
![](https://github.com/kapustakiszona/MoviesApp/blob/master/docs/3.gif) ![](https://github.com/kapustakiszona/MoviesApp/blob/master/docs/4.gif)
![](https://github.com/kapustakiszona/MoviesApp/blob/master/docs/5.gif)
</details>

## :hammer:Tech Stack
**Architecture**
+ MVVM
+ Kotlin based
  
**Libraries**
+ [Retrofit](https://square.github.io/retrofit/) -  Execute network service calls
+ [Hilt](https://dagger.dev/hilt/) - Dependency injection
+ [Room](https://developer.android.com/training/data-storage/room) - Save data in a local database
+ [Recyclerview](https://developer.android.com/develop/ui/views/layout/recyclerview) - For creating dynamic lists
+ [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Store UI related data
+ [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - an observable data holder class
+ [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Perform asynchronous operations
+ [Coil](https://coil-kt.github.io/coil/) - An image loading library backed by Kotlin Coroutines
**Services**
+ [TMDB API](https://developer.themoviedb.org/reference/intro/getting-started) - The Movies Database API
