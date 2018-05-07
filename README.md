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
