# Jackdaw Framework

Amateur framework with which I work to make 2d javafx and swing based games.

## Instructions

please read trough all instructions at least one before starting anything.
if all is followed step by step, you should be able to have a JavaFX project with the jacdaw framework ready in no time.

### download : 
 - json library : https://github.com/stleary/JSON-java
 - javafx sdk : https://gluonhq.com/products/javafx/
 - this framework as a library : https://github.com/ArtixAllMighty/JackDawFramework/releases/download/JDF_2.0_1.0.0.0/JackdawFramework_2.0-1.0.0.0.jar
 
### instructions
  - start javafx project in intellij
  - extract javafx zip somewhere
  - add javafx lib folder as library
  - add json jar as library
  - add framework jar as library
> adding a library : go to file > project structure > library > click the +, pick java option, > navigate to jar or folder you want to add.

### get started with the framework
  - make one class extending GameStateHandler
  - make one class extending GamePanel
  - in your GamePanel, override getCustomGameStateHandler and pass down a new instance of your GameStateHandler
  - in the constructor of your GameStateHandler, call #addGameState in which you can pass a GameState and a unique identifier String.
        this registeres your gamestates for usage.


GameStates can be switched with GameStateHandler#changeGameState(String)

The first game state that needs to be rendered/called needs to be registered under the name "first_screen".
A static final variable is available in GameState , called FIRST_SCREEN for you to use.

To load resources, you should extend a class with LoadState, override loadResources , and you can call ImageLoader with a small variety of methods to load in sprites and arrays of images.
Caching these images will be best practice.

to compress images loslessly, and get rid of all their metadata, you could use pingo or pinga.

