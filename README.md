# Popular Movies, Stage 1

### Project Overview
Most of us can relate to kicking back on the couch and enjoying a movie with friends and family. In this project, I built an app to allow users to discover the most popular movies playing. The development of this app is split in two stages. First, let's talk about stage 1.

In this stage I built the core experience of the movies app.

The app:

- Presents the user with a grid arrangement of movie posters upon launch.
- Allows your user to change sort order via a setting:
  - The sort order can be by most popular or by highest-rated
- Allows the user to tap on a movie poster and transition to a details screen with additional information such as:
  - original title
  - movie poster image thumbnail
  - A plot synopsis (called overview in the api)
  - user rating (called vote_average in the api)
  - release date

### Why this Project
To become an Android developer, we must know how to bring particular mobile experiences to life. Specifically, we need to know how to build clean and compelling user interfaces (UIs), fetch data from network services, and optimize the experience for various mobile devices.
By building this app, I can demonstrate my understanding of the foundational elements of programming for Android. The app communicates with the Internet and provide a responsive and delightful user experience.
### What I Learnt?
- Fetch data from the Internet with theMovieDB API.
- Use adapters and custom list layouts to populate list views.
- Incorporate libraries to simplify the amount of code you need to write
---

# Project Review

Congratulations, you did a great job, fixed the reported issue from the previous review, now it is all good :clap: :clap:

Happy learning and good luck with the rest of the projects :smiley:

### Common Project Requirements
##### :heavy_check_mark: App is written solely in the Java Programming Language.

##### :heavy_check_mark: Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.

##### :heavy_check_mark: UI contains an element (i.e a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated.

##### :heavy_check_mark: UI contains a screen for displaying the details for a selected movie.

##### :heavy_check_mark: Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.

### User Interface - Function
##### :heavy_check_mark: When a user changes the sort criteria (“most popular and highest rated”) the main view gets updated correctly.

##### :heavy_check_mark: When a movie poster thumbnail is selected, the movie details screen is launched.

### Network API Implementation
##### :heavy_check_mark: In a background thread, app queries the ```/movie/popular``` or ```/movie/top_rated``` API for the sort criteria specified in the settings menu.

### General Project Guidelines
##### :heavy_check_mark:App conforms to common standards found in the [Android Nanodegree General Project Guidelines](http://udacity.github.io/android-nanodegree-guidelines/core.html).

---

# Popular Movies, Stage 2

### Project Overview
Welcome back to Popular Movies! In this second and final stage, I added additional functionality to the app I built in Stage 1.

I added more information to the movie details view:

- Allow users to view and play trailers ( either in the youtube app or a web browser).
- Allow users to read reviews of a selected movie.
- Allow users to mark a movie as a favorite in the details view by tapping a button(star).
- Create a database and content provider to store the names and ids of the user's favorite movies (and optionally, the rest of the information needed to display their favorites collection while offline).
- Modify the existing sorting criteria for the main view to include an additional pivot to show their favorites collection.
### What I Learnt?
- I built a fully featured application that looks and feels natural on the latest Android operating system (Nougat, as of November 2016).

---

# Project Review

### Common Project Requirements
##### :heavy_check_mark: App is written solely in the Java Programming Language.

Nice job! Your app is implemented using Java!

##### :heavy_check_mark:App conforms to common standards found in the [Android Nanodegree General Project Guidelines](http://udacity.github.io/android-nanodegree-guidelines/core.html).

Good job implementing Popular Movie Projects! You have done a great job so far! :smiley:

##### :heavy_check_mark:App utilizes stable release versions of all libraries, Gradle, and Android Studio.



### User Interface - Layout

##### :heavy_check_mark:  UI contains an element (e.g., a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated.

Your UI contains an element to choose the sort order by "most popular", "highest reated" and "favorited"

##### :heavy_check_mark:  Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.

Your app can display a grid of corresponding movie poster thumbnails

##### :heavy_check_mark:  UI contains a screen for displaying the details for a selected movie.

Your app is able to display the details for a selected movie

##### :heavy_check_mark:  Movie Details layout contains title, release date, movie poster, vote average, and plot synopsis.

##### :heavy_check_mark:  Movie Details layout contains a section for displaying trailer videos and user reviews.

### User Interface - Function
##### :heavy_check_mark:  When a user changes the sort criteria (most popular, highest rated, and favorites) the main view gets updated correctly.

##### :heavy_check_mark:  When a movie poster thumbnail is selected, the movie details screen is launched.

##### :heavy_check_mark:  When a trailer is selected, app uses an Intent to launch the trailer.

##### :heavy_check_mark: In the movies detail screen, a user can tap a button(for example, a star) to mark it as a Favorite.

### Network API Implementation
##### :heavy_check_mark: In a background thread, app queries the ```/movie/popular``` or ```/movie/top_rated API``` for the sort criteria specified in the settings menu.

##### :heavy_check_mark: App requests for related videos for a selected movie via the ```/movie/{id}/videos``` endpoint in a background thread and displays those details when the user selects a movie.

Good job! Your app could use /movie/{id}/videos to fetch movie trailers.

##### :heavy_check_mark: App requests for user reviews for a selected movie via the ```/movie/{id}/reviews``` endpoint in a background thread and displays those details when the user selects a movie.

Good job! Your app could use /movie/{id}/reviews to fetch movie reviews.

### Data Persistence
##### :heavy_check_mark: The titles and IDs of the user’s favorite movies are stored in a native SQLite database and are exposed via a ```ContentProvider```. This ```ContentProvider``` is updated whenever the user favorites or unfavorites a movie. No other persistence libraries are used.

The data layer is implemented very well. Good job!

##### :heavy_check_mark: When the "favorites" setting option is selected, the main view displays the entire favorites collection based on movie ids stored in the ```ContentProvider```.
