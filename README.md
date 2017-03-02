# sg_kata

- Tennis Game Test support:

    - Human (manual) Game with the ability to directly force a selected player (by index) to win a point
    - Auto Game useful to show a random execution (print) :
        - Simul the Ball Exchange (Point) with :
            - Send a ball is simuled with the generation of an INT from 0 to 6:
                - From 1 to 5 is inside the stadium, the other player could sendBack if he generate a near INT.
                - 1 or 6 is outside (player loose the point).
    - Handle the Deuce case and the loose of adventage.
    - Handle the Round Winner (6 points with +2 points)
    - Handle the Set and the Match score (point in the round not the number of round).
    - Tie-break should be implemented but not sure i understand what you mean by it.
    - The Score is always avalaible.    
    - The Winner of a Point, Game, a Set and A Match (6 points with +2 points) is accessible.
    - Check The Game (Ball Exchanges), Set, Match and the Game State (Default, Ready, Started, Ended)
    - Allow players to join a game.
    
- Tennis Game (Nice to have):
    - Adding multi-threading support.
    - Handling Players leave the game
    - GameConsole should be able to handle many matchs
    
- The projet requires JAVA8

- Demonstrating GIT branching/merging worflow 

![alt tag](http://img4.hostingpics.net/pics/741144gitworkflow.jpg)


- Tennis Game
- SPRINT1 : manage a tennis GAME within a set of a tennis match
- User Story 1 :
                As a tennis referee
I want to manage the score of a game of a set of a tennis match between 2 players with simple Game rules
In order to display the current Game score of each player
 
                Rules details:
-          The game starts with a score of 0 point for each player
-          Each time a player win a point, the Game score changes as follow:
0 -> 15 -> 30 -> 40-> Win game
 
-User Story 2 :
                 As a tennis referee
 I want to manage the specific of the rule DEUCE at the end of a Game
 In order to display the current Game score of each player
  
                 Rules details:
 -          If the 2 players reach the score 40, the DEUCE rule is activated
 -          If the score is DEUCE , the player who  win the point take the ADVANTAGE
 -          If the player who has the ADVANTAGE win the  point, he win the game
 -          If the player who has the ADVANTAGE loose the point, the score is DEUCE
 
 SPRINT2 : manage a Tennis SET within a tennis match
 User Story 1 :
                 As a tennis referee
 I want to manage the score of a set of a tennis match between 2 players
 In order to display the current Game & Set score of each player
  
                 Rules details:
 -          The set starts with a score of 0 Game for each player
 -          Each time a player win a Game, the Set score changes as follow:
 1 -> 2 -> 3 -> 4 -> 5 -> 6 (-> 7)
 -          If a player reach the Set score of 6 and the other player has a Set score of 4 or lower, the player win the Set
 -          If a player win a Game and reach the Set score of 6 and the other player has a Set score of 5, a new Game must be played and the first player who reach the score of 7 wins the match
  
  User Story 2 :
                  As a tennis referee
  I want to manage the specific of the rule of Tie-Break at the end of the Set
  In order to display the current Game, Set score & Tie-Break score of each player
   
                  Rules details:
  -          If the 2 players reach the score of 6 Games , the Tie-Break rule is activated
  -          Each time a player win a point, the score changes as follow:
  1 -> 2 -> 3 -> 4 -> 5 -> 6 (-> 7-> 8-> 9-> 10-> …)
  -          The Tie-Break ends as soon as a player gets a least 6 points and gets 2 points more than his opponent
  -          The player who wins the Tie-Break wins the Set and the match
