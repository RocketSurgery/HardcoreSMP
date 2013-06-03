#Hardcore SMP 
plugin by: excaliburHisSheath & the RocketSurgery Team
version: 1.0.0

## Description
Picks a random respawn location for a player when they die, recreating the the effect of having your world wiped when you die in Hardcore SSP, while still allowing for cooperative/competitive co-op play. This improves over the standard method of doing Hardcore SMP, which bans a player after death, defeating the purpose of doing multiplayer in the first place.
Currently a work in progress, with much more functionality planned for future updates.

##Functionality
- On death, a respawn point between the min and max distances specified in the config (1000 and 2000 by default) away from the point of death is chosen, and this is where the player respawns.
- the destination is checked to ensure that it is not in an ocean biome
	
##Bugs/Problems
- no support for multiple worlds; you respawn in whatever world you die in, you won't be returned to the main world
- slow to load the destination, resulting in the appearance of falling through the world before it loads
- occasionally the process of testing random locations will not find a viable location, this will result in the server hanging
- the min distance cannot be larger or equal to the max distance or the plugin will throw exceptions and not work
- permissions need to be added so that at the least ops can access commands
 
##Planned Features
- Multi-World support - Allow you to set a default world for respawn or allow the players to respawn in the same world they died in. Currently the player will respawn in the same world they died in.
- Team Functionality - Allow users to form teams and find their current team members using a compass. Currently there is no way to find other players (other than using the debugging console), so random spawning makes cooperation difficult and large worlds feel desolate. Having an internal mechanic for finding your friends makes it feel more like multiplayer, while requiring them to build a compass ensures that there is at least some barrier to reforming your group after you die.