Common Project Requirements

App is written solely in the Java Programming Language
Yes

App is written solely in the Java Programming Language
Yes

Submission must use stable release versions of all libraries, Gradle, and Android Studio. Debug/beta/canary versions are not acceptable.
Yes

App utilizes stable release versions of all libraries, Gradle, and Android Studio.
Yes

General App Usage
Display recipes - App should display recipes from provided network resource.
Yes, From the provided link and parsed the json object into a my objects

App Navigation - App should allow navigation between individual recipes and recipe steps.
yes

Utilization of RecyclerView - App uses RecyclerView and can handle recipe steps that include videos or images.
RecyclerView is used for Recipe list and steps are handled that include videos or images.

App conforms to common standards found in the Android Nanodegree General Project Guidelines.
yes

Components and Libraries

Master Detail Flow and Fragments  - Application uses Master Detail Flow to display recipe steps and navigation between them.
Yes, used fragments for the master recipe list and recipe details which show the ingredients and steps


Exoplayer(MediaPlayer) to display videos - Application uses Exoplayer to display videos.
yes, used for the step video when there is video URL


Proper network asset utilization - Application should properly retrieve media assets from the provided network links. It should properly handle network requests.
yes

UI Testing - Application makes use of Espresso to test aspects of the UI.
yes, used Idling Resources with RecyclerView to test clicking first item and asserting the call for details view


Third-party libraries - Application sensibly utilizes a third-party library to enhance the app's features. That could be helper library to interface with ContentProviders if you choose to store the recipes, a UI binding library to avoid writing findViewById a bunch of times, or something similar.
Yes, used Picaso library to load images

Homescreen Widget

Application has a companion homescreen widget.
yes


Widget displays ingredient list for desired recipe.
yes and recipes can be changed by clicking on the recipe name