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
* I have seen some issues when clicking fast on save new item and sometimes when opening the app it cannot retrieve the latest item to set the new status. I took a quick look into it and maybe it is due the handling of the backpressure on room database. Need to ivestigate a further more.
* Sometimes, when I click in a button I have an impression that this button does not listen the event.


