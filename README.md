Realms
======

Bukkit Plugin for managing Realms and settlements. No protrections . 
Based on the ideas of the plugin Dominion from Pico52 a created this plugin.
I want to resolve some design issues in the dominion design
- no automatic building verification
- small bandwidth of produced items and blocks
- many different units with only virtual representation
After some analysis of the dominion design i made the decision to make  a new design 
with a different design and architecture.

Archtecture requirements
- model design without plugin requirements
- unittest for zhe model
- Herostronghold as verification for buildings and settlements
- independant datatypes not directly based on Minecraft datatypes
- use of basic types String, integer, Double, Enums
- model run with events , triggered from outside the model
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

  