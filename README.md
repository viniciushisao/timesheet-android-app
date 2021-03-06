# timesheet-android-app

# What is
This app is a work timesheet. You are able to mark when you start (arrive) and you stop (leave) to work (main screen).

In the reports screen, you choose one day and you are able to see the report of entries on that day. Also, in this
screen you can edit an entry or delete it.

# Compile
Android project running on kotlin and Gradle. Use your favorite tools to compile and run

# Architecture
It is designed as [MVVM](https://developer.android.com/jetpack/guide) using jetpack, described by Google. Also, other architecure and practices found here is based on [Audacity](https://classroom.udacity.com/courses/ud9012) course, also endorsed by Google.


## Diagrams
These diagrams are generated using [PlantUML](https://plantuml.com/). 
The main files are [here](https://github.com/viniciushisao/timesheet-android-app/tree/main/docs/puml). 
Feel free to generate it in other formats if you wish to.

### Class Diagram
![Class Diagram](https://github.com/viniciushisao/timesheet-android-app/blob/main/docs/puml/classdiagram.png?raw=true)
* The diagram displays the relevant classes.

### Sequence Diagram
![Sequence Diagram](https://github.com/viniciushisao/timesheet-android-app/blob/main/docs/puml/sequencediagram.png?raw=true)

# TODO
This project is a work in progress project and that is why it is missing some code:

## Testing
There is a few unit tests. Coverage is very low.ø

## UI
This code shows only *wireframes* and focus on the background architecure.
It is ready to receive any other format of design because it is implemented in the most simple way.

## Issues
* There are some points where the error handling was not implemented that is why some unexpected issues may occur.
* Coroutines were implmented in some key points but most of then are hidden. This may generate some unpredictable concurrences failure.

# Wireframes

![Edit Screen](https://github.com/viniciushisao/timesheet-android-app/blob/main/docs/edit_screen.png?raw=true)
![List Screen](https://github.com/viniciushisao/timesheet-android-app/blob/main/docs/list_screen.png?raw=true)
![Main Screen](https://github.com/viniciushisao/timesheet-android-app/blob/main/docs/main_screen.png?raw=true)
![Select Date Screen](https://github.com/viniciushisao/timesheet-android-app/blob/main/docs/select_date_screen.png?raw=true)

