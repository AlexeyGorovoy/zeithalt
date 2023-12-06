## 14. Faction Actions In-between Battles

`📑 Battle Rules`

Factions can perform **actions** that affect both gameplay and lore in-between battles.

These actions are strictly performed only **while the attack window is closed**.
Each faction can take** one action before each battle**.
If an action challenge already started, a new action cannot be sent by anyone until it ends.
Each action has a base price associated with it.

When the faction wants to perform a battle action, they should use **/faction_action** command - each action has **costs**, usually it's some amount of gold and a `⚔️ Battle Action Point`. 

> For more info on specific actions please use **/faction_action_help** command.  
> This page describes how actions work in general

Immediate actions are completed... immediately.

Timed actions start a new challenge in [actions channel](https://discord.com/channels/562910943848169472/1064627177116684400) - the completion of which will determine the additional extra price faction has to pay in order to complete the action.

During the challenge players can either **support** or **sabotage** the action by playing Influence during the challenge. Players can contribute to a challenge **every 30m** + one more time in **the last 5m** of the challenge.

Support is free, while sabotage has a price attacked to it. Note that **sabotage price is charged from personal balance unless you're a [key member](../discord_battle_rules/rules_07_key_members.md) of your faction**.

The challenge result is determined by the following formula:  _(support) / (support + sabotage) = xx% completion._

Action **in faction lands** require 25% to pass.
Action **in foreign lands** require 50% to pass.

<!---
keywords:  
aliases: 
-->