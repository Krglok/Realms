Realms
======
Bukkit Plugin for managing Realms and settlements. Do NO protections . 
Based on the ideas of Dominion from Pico52, this plugin was designed and created.
I want to resolve some design issues in the dominion design.
After some analysis of the dominion design i made the decision to make  a new design 
with a different architecture.

Architecture requirements
- realm design without direct plugin connection
- unittest for the model
- Herostronghold as verification for buildings and settlements
- independant datatypes not directly based on Minecraft datatypes
- use of basic types String, integer, double, enums
- realm model run with events , triggered from outside the model
- layer model for realm model, data handling, command and events, pluginframe
- Units are always virtual and have no representation as entity in game

Design objects and requirements
- Owner Object , playername
- Realm Object
- Settlement Object
- Units
- Regiment for movable units
- no protection system for areas or chunks
- npc player are posible

Realm based on the feudalsystem
- King
- Highlord
- Lord
- Knight
- Settler

Settlement realize the urban organization
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

The project is devide into 3 phases to reach Version 1.0
- Phase 1, economy and buildings
- Phase 2, units and unit relevant buildings
- Phase 3, Realms and Conflicts
 
In progress:
- Phase 2, units and unit relevant buildings

Ready and published
- Phase 1, ready, publish as beta 0.8.2


