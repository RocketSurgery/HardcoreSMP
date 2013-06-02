Hardcore SMP 
plugin by excaliburHisSheath
version 0.1.1

Picks a random respawn location for a player when he dies, recreating the the effect of having your world wiped when you die in Hardcore SSP.
currently a work in progress.

Functionality:
	- on death, a random distance between 1000 and 2000 and a random direction are picked, and that is where the player is respawned
	- the destination is checked to ensure that it is not in an ocean biome
	
Bugs/Problems
	- no support for multiple worlds; you respawn in whatever world you die in, you won't be returned to the main world
	- slow to load the destination, resulting in the appearance of falling through the world before it loads
	- occasionally the process of testing random locations will not find a viable location, this will result in the server hanging
	- the min distance cannot be larger or equal to the max distance or the plugin will throw exceptions and not work
	- permissions need to be added so that at the least on ops can access commands