# DemoMovieCompose

**Screenshots:**
| <img src="docs/screenshots/splash.jpg">          | <img src="docs/screenshots/popular_movies.jpg"> | <img src="docs/screenshots/search_movie.jpg"> |
| ------------------------------------------------ | ----------------------------------------------- | --------------------------------------------- |
| <img src="docs/screenshots/favorite_movies.jpg"> | <img src="docs/screenshots/movie_details.jpg">  | <img src="docs/screenshots/burger_menu.jpg">  |

**Technology Stack:**
- Kotlin Programming Language
- Clean Architecture
- MVVM Architecture Pattern
- Hilt Dependency Injection
- Jetpack Compose
- ViewModel
- ROOM Database
- Retrofit REST + OkHttp
- Coroutine
- Flow
- GSON Serialization
- Coil
- Gradle build flavors
- BuildSrc + Kotlin DSL
- Proguard
- Git

**To run the project in DEBUG MODE:**

Get the access token (not the api key) from [api.themoviedb.org](https://api.themoviedb.org/).

Create file namely “key.properties” in the root project with following contents:

storePassword=<your_keystore_password> <br />
keyPassword=<your_key_password> <br />
keyAlias=<your_key_alias> <br />
storeFile=<path_to_keystore_file> <br />
accessToken=<your_access_token> <br />

once you have created it, open the project with Android Studio, build the project and run the project.
