# JackDawFramework
The framework I use in all of my games. 

This framework allows to make a 2d game with gamestates with a little effort.
most is taken care of.

warning : very simple frame
improvements will always be made
Check bottom for other requiered libraries

#HOW TO : 
suggestion : make a package called mygame.framehook or mygame.main in which you will put the following :

- a Class that has the main method in it, calling the main from the framework (framework.main.Main.main(args);)
- a Class extending GameStateHandler in which you will call 'addGameState(GameState, int)' in it's constructor
       for every GameState you will create.
- a Class extending GamePanel, in which you will override 
       getCustomGameStateHandler() and return your own GameStateHandler
- if you'd like, a class extending GameState, to personalize the game and update methods

-side note on gamestates :

      1.You cannot register a GameState with identifier -128. it already exists.
      
      2.The gamestate with identifier 0 will be loaded first. if no gamestate with identifier 0 exists,
      running the game will result in crash and a nullpointer exception
      
      3.If you need to load in resources, I advice you to make a gamestate that extends LoadState,
      whicha has a nifty 'loadResources()' method in which you can load any resources needed, while still being able
      to draw to the screen and make something fancy out of the loading.

External libraries Required : 
#sound decoding
jl1.0.1
mp3spi1.9.5
tritonus_share

mp3 spi jar : http://www.javazoom.net/mp3spi/sources.html
j layer jar : http://www.javazoom.net/javalayer/sources.html
tritonus share jar : http://www.tritonus.org/plugins.html

#external save writing
json-stlearly
https://github.com/stleary/JSON-java
(has to be downloaded and exported as jar by user)
