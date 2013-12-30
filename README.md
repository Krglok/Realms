Realms
======

Bukkit Plugin for managing Realms and settlements. DO no protections . 
Based on the ideas of Dominion from Pico52 i created this plugin.
I want to resolve some design issues in the dominion design
- no automatic building verification
- small bandwidth of produced items and blocks
- many different units with only virtual representation
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

Realm realize the feudalsystem
- King
- Highlord
- Lord
- Knight
- Settler

Settlement realize the urban organization
- Warehouse
- Resident
- Town-hall
- Buildings
- Trader
- Barrack with military units   
  
Units represent the military and support entitys
- settler
- worker
- militia
- scout
- archer
- light infantry
- heavy infantry
- knight
- commander

  