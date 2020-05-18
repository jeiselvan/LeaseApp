# LeaseApp

General overview
-----------------
This app contains information about the lease list and corressponding lease information of the selected lease list items. 

Language
---------
This app is written in Kotlin.

Architecture
--------------
MVVM design pattern is used with LiveData in conjunction. 

Network
---------
The network module is powered by Retrofit and RxJava libraries to ensure smooth api calling is performed without any performance leaks.
The RXJava's observer idealogy is used to make sure web calls are run in seperate thread rather than the main thread, so that the Android OS is not blocked (To prevent app getting stuck).

Code Readability & maintainability
----------------------------------
The network related functions are grouped together in as network module, which can be used later by Dagger2 to draw the dagger graph and later use it other parts of the project without having to redefine over and over again.
A utility package is created where utility functions such as Network availability are available.

Testing
--------
Unit tests are conducted for the webservices which are implemented in this app. The testing frameworks which were used :- Mockito, Mockk and JUnit Framework.
Please run the test a second time if it fails the first time due to the fact that in some instances "RxImmediateSchedulerRule" Rule is not getting initialized. This error is common when RxJava functions are involved.
This app is tested in Google Pixel 5 emulator.


