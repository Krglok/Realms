Realms
======
Bukkit Plugin for managing settlements and kingdoms. Do protection with Stronghold . 
Based on the ideas of Dominion from Pico52, this plugin was designed and created.
I want to resolve some design issues in the dominion design.
After some analysis of the dominion design i made the decision to make  a new design 
with a different architecture.

Architecture requirements
- realms design without direct plugin connection
- Unittest for the model
- Stronghold (Herostronghold) as verification for buildings and settlements
- Independent datatypes, not directly based on Minecraft datatypes
- Use of basic types String, integer, double, enums
- Realm model run with events , triggered from outside the model
- Layer model for realm model, data handling, command and events, pluginframe
- Units are always virtual and have a citizens representation as entity in game
- Units will spawn and interact with players

Design objects and requirements
- Owner Object , playername, UUID, Achievments
- Kingdom Object
- Lehen Object
- Settlement Object
- Units Object
- Regiment Object for movable units
- Protection system for areas or chunks with Stronghold
- NPC player are possible 

Realm based on the feudalsystem
- King
- Lord
- Earl
- Knight
- Commoner

The player start as commoner. The player must earn Realms specific achievements. 
The player can donate and trade with settlements.
The player can buy houses in a settlement.
The player can assume a settlement 

The commoner can reach different technology levels
- Tech0, everyone , simple houses
- Tech1 , founding HAMLET , buildings for KNIGTH level
- Tech2 , simple craftsman
- Tech3 , extended craftsman, simple military
- Tech4 , founding TOWN , buildings for EARL level
- Tech5 , founding CITY , buildings for LORD level
- Tech6 , extended military
- Tech7 , founding METROPOLIS, buildings for KING level

The techlevel can reach by create buildings or to read a TechBook

Settlement include the urban organization
- Town-hall 
- Resident
- Warehouse
- Buildings
- Production
- Trader
- Barrack with military units   
  
Units represent the military and support entitys
- settler
- militia
- scout
- archer
- light infantry
- heavy infantry
- knight
- commander

The project is divide into 3 phases to reach Version 1.0
- Phase 1, economy and buildings
- Phase 2, units and unit relevant buildings
- Phase 3, Realms and Conflicts
 


