# Rogue_Expanded

This is the Rogue game with some additional features. The first one is the ability to run a game (map with items and monsters) from a game file.
The monster will chase after the player if the player is within two units of the monster. 
The chasing position is determined in the current round but is only rendered in the next round. 
Hence, it was necessary to store the current and the next predicted positions of the monsters.
The monster will disappear from the map once it is defeated.
The player and momster can't pass across sea and mountain terrains, represented by '~' and '#'
The player can pick up items which heal the player, proceed the player to the next level (returning to the main menu in this case), and increase the attack of the player
The Unit is an ancestor class inherited by Player and Monster class to avoid duplication of code as they have similar fields such as damageIncurred, maxHealth, and name.
The default game has only one player and monster
The player file is saved at some point of the game and can be loaded (using PrintWritter and Scanner objects)

This is the second stage project for the subject COMP90041, Programming and Software Development.
Obtained a grade of 86/100
