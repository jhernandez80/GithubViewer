# GitHub Viewer
<img align="left" width="150dp" src="https://user-images.githubusercontent.com/34455740/94374349-8d57e280-00c0-11eb-9e82-b090da96d7b8.png">
<br/><br/>A Kotlin first, github browsing app made to test out new Android components and ideas. Utilizes clean architecture, MVVM, and Android Jetpack/Architecture Components.
<br/><br/><br/>
<p align="center">
  <img width="250dp" src="https://user-images.githubusercontent.com/34455740/94374825-c5145980-00c3-11eb-93b9-ed86320eb6dc.gif">
</p>


## Architecture (TODO)
This area is a work a progress, but the goal is to use Room for persistence and as the single source of truth (SSOT). The Repository layer will consist solely of RxJava while the ViewModel will expose Android LiveData components for the UI.

## Libraries Used

### Android JetPack & Views
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- [ConstraintLayout](https://developer.android.com/training/constraint-layout/index.html)
- [SwipeRefreshLayout](https://developer.android.com/training/swipe/add-swipe-interface)
- [Preferences](https://developer.android.com/guide/topics/ui/settings)
- [ViewPager2](https://developer.android.com/guide/navigation/navigation-swipe-view-2)
- [Material Components](https://material.io/develop/android)


### Network Request
- [Retrofit](https://square.github.io/retrofit/): A type-safe HTTP client  for Android and Java
- [Gson](https://github.com/google/gson): A Java serialization/deserialization library to convert Java Objects into JSON and back

### Persistence
-  [Room](https://developer.android.com/training/data-storage/room): The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.

### Reactive Components
- [RxJava](https://github.com/ReactiveX/RxJava): A library for composing asynchronous and event-based programs by using observable sequences.
- [RxAndroid](https://github.com/ReactiveX/RxAndroid): RxJava bindings for Android

### Image Handling
- [Glide](https://bumptech.github.io/glide/): An image loading and caching library for Android focused on smooth scrolling



## TODO
 - [ ] Add API authentication to prevent rate limiting
 - [ ] Define models separately as DTOs and Domain Models
 - [ ] Establish clearer relations in Domain Models
 - [ ] Add an expiration to the cache
 - [ ] Add paging to home and issue pages
 - [ ] Add both unit and integration tests
 - [ ] Add dependency injection via [Dagger](https://dagger.dev/) or [Koin](https://insert-koin.io/)
 - [ ] Add more animations with [MotionLayout](https://developer.android.com/training/constraint-layout/motionlayout)
 - [ ] Try to group ui package by feature instead of types
