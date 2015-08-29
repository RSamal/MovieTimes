# MovieTimes
This is the Udacity Nanodegree Moviee App Stage One. This app has the features to display list of movies feteched from MovieDb. 
The main screen will display these movies based on two tabs "Popular" and "High Rate". Based on the user selection the Tabs will
fetch the appropriate list and display on the main screen.

Once user select a specific movie from the list, A detail view will be shown which has the following things.
* Movie Trailer
* Movie Poster
* Movie Titile
* Movie release date
* Movie user rating in stars
* Movie Description

### Developer API key
To run this app through android studio , we need to chhange the MovieDb API key and Google Developer API key in the below class
**`com.udacity.movietimes.utils.MovieConfig`**

### External Jars/API
* Google Volley - To do network call
* Google GSON   - To parse JSON data
* Picasso       - To load images

### Stage 1 Rubric changes
* Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails :                           **I have used RecyclerView and CardView to display the details as that is latest.**
* UI contains an element (i.e a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated : 
**I am aware of the concept SharedPreference and how to use it through Settings menu. However I would like to use the more advance TabLayout and VageViewer. Hence used them instead of changing the choice through Settings.**

