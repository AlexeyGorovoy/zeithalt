## Introduction

`📑 Battle Rules`

_Factions Game_ is our local discord-chat-strategy-roleplay-politics game conducted by one if Influence developers _AlexxDev._

If you're here solely for Influence - please visit **[#wtf_is_faction](https://discord.com/channels/562910943848169472/1177313731223486504)** channel for a short Q&A.

These rules formally describe the core process of playing the game, but specifics are usually found in bot commands such as **/faction_action_help** or as tips and comments in bot messages or responses.

If you have any questions - please ask them in **[#faction_game](https://discord.com/channels/562910943848169472/995639554042249246)** channel or anywhere else in the server.

Have fun reading, and happy battling!

<!---
keywords:  
aliases: 
-->
## 1. How Battles Start

`📑 Battle Rules`

- Factions take turns attempting to capture territories, each cycle the order is random.

- Cycle is a set of battles, one for each faction + a [Corruption](../rules/rules_09_corruption.md) battle.

- Faction members are free to decide where to attack by voting or any other process agreed in the faction chat, the battle is can be started by any [Key faction member](../rules/rules_07_key_members.md) using **/faction_battle** command.

- A faction can only **attack neighbouring territories **(where land connection exists) **+ one over territory by sea.**

- A faction **has to announce their intention to attack it within two days** of the previous battle otherwise their turn will be skipped. They do not need to specify the exact time of the attack, nor the territory - just confirm the intention to take their turn.

- Minimum time between battles - **7 days** (7*24h=192h) after previous battle ended.

- Maximum time between battles - **10 days** (240h).

- Same territory can not be attacked twice in a row (does not apply to [Corruption](../rules/rules_09_corruption.md) attacking the land).

<!---
keywords:  
aliases: 
-->
## 2. Initial Bonus Points

`📑 Battle Rules`

Battles usually start from non-zero score. The following bonuses are applied to the attacker or defender score (or both):

**Adjacency +10 base bonus**  - for each land of the same color as attacker or a defender, connected by land.

**Garrison and Supports unit bonuses** - explained in a [separate post](../rules/rules_06_map_units.md).

In case of the *attack on capital region*, some bonuses **are doubled** - rules about that [are located here](../rules/rules_10_sieging_capitals.md).

**[Here's an example from the actual battle](https://discord.com/channels/562910943848169472/995641432742297731/1179425462208966658)** (discord link)

> Note: in the rules you'll often find "base 10" or "base 20" regarding bonuses - this is because actual points are adjusted from time-to-time for the number of people playing the game.
>
> The example above assume x5 multiplier, so base 10 means 50pts, base 20 means 100pts.


<!---
keywords:  
aliases: 
-->
## 3. General Gameplay

`📑 Battle Rules`

During the battle, you just send any message containing "attack" or "defend"  in **[#battle](https://discord.com/channels/562910943848169472/995641432742297731)** to generate a **hit point** for your side.

If you're a [sellsword](../rules/rules_08_sellswords.md) - you can also write "hit" command to join the current [Fighters Guild (lore)](../refs/fighters_guild.md) contract.

After a successful hit, you're put on a **45m cooldown**. You can act again after this cooldown expires.

If your **teammate** sends a message within **1 minute** after yours - it creates a **combo** and increases the hit counter.

If **enemy player**  sends a message within **1 minute** after yours -  they **steal** your team's combo. The hit counter stays the same.

If there are **no messages** within **1 minute** after yours - counter is reset, and the bot sends **score update** message to the chat.

*Examples:*
```
// all messages <1 minute
bot: Battle started

ci_player_1: attack
bot: Hit! CI +1!

ci_player_2: attack
bot: Combo! CI +2!

ci_player_3: attack 
bot: Combo! CI +3!

dc_player_1: defend
bot: Combo stolen! DC +3!

dc_player_2: defend
bot: Combo! MT +4!

// >60s passes
bot: Score update:

Attackers - 6:
--- CI - 6
Defenders - 7:
--- DC - 3
--- MT - 4
```

Or look at the combo in the **[actual battle](https://discord.com/channels/562910943848169472/995641432742297731/1179425490654744668)**.

<!---
keywords:  
aliases: 
-->
## 4. Charged Hits

`📑 Battle Rules`

If you attack within **15m** after your cooldown has expired, your hit is **charged**.

**Charged** hits add +1 bonus point for your team, but this bonus is *erased* when combo is *stolen*.

Each charged hit **during the combo** streak by default increases bonus counter **by 1 point**. However, [based on your performance in the previous battle](../rules/rules_11_battle_rewards.md), you can get a **bonus multiplier** for your charged hits.

If your charged hit **steals** opponent's combo, their bonus points ** are erased**, and your team's bonus points start with 1.

**Examples:**

You attack at 10:00. Your 45m cooldown starts, next time you'll be able to attack at 10:45.
If you attack between 10:45 and 11:00, your hit is **charged** and adds +1 bonus.
If you attack after 11:00, your hit is **not charged** - it's just a normal hit again.

```
// Same example as in rules pt. 3, but all hits are charged
bot: Battle started

ci_player_1: attack
bot: Hit! CI +1! 
CI charged bonus = 1.

ci_player_2: attack
bot: Combo! CI +2! 
CI charged bonus = 2.

ci_player_3: attack 
bot: Combo! CI +3! 
CI charged bonus = 3.

dc_player_1: defend
bot: Combo stolen! DC +3!
CI charge lost.
DC charged bonus = 1.

dc_player_2: defend
bot: Combo! MT +4!
DC charged bonus = 1.
MT charged bonus = 1.

// >60s passes
Attackers - 6:
--- CI - 6
Defenders - 5:
--- DC - 4 (3 stolen + 1 charged)
--- MT - 5 (4 normal + 1 charged)
```

<!---
keywords:  
aliases: 
-->
## 5. How Winners Are Decided

`📑 Battle Rules`

Usually in battles the goal is set to 150 base points.

In this example multiplier is x10, so the goal is 1500 pts (multiplayer may be different in actual battles to adjust for the number of players.

MT is attacking and CI is defending.

**Example #1**
```
Attackers - 1500pts
MT - 800
DC - 700
Defenders - 700pts
CI - 600
DC  - 100

MT captures the land. 
```

**Example #2**
```
Attackers - 1500pts
MT - 700 
DC - 800 <-- here it DOES matter who scored more
Defenders - 70pts
CI - 600
DC  - 100

Even though it was MT's attack, DC overtook them and captured the lands!
```

**Example #3**
```
Attackers - 800pts
MT - 400 
DC - 400 
Defenders - 1500pts
CI - 1000 
DC  - 500 

CI successfully defended their own lands.
```

**Example #4**
```
Attackers - 800pts
MT - 400 
DC - 400 
Defenders - 1500pts
CI - 500 
DC  - 1000 <-- here it does NOT matter who scored more

Even though DC scored higher in defense, defending teams work together. 
CI successfully defended their own lands.
```
## 6. Map Units

`📑 Battle Rules`

Each faction has *two* **Garrison** units and *one* of each - **Support** and **Saboteur** units to place on the map. Factions can move their units **between battles** using **/faction_action** bot command.

__**2 Garrisons**__ - defensive units, so they can only be places on the lands that faction owns. If a territory with a *Garrison* stationed on it is attacked, it gets a **+20 base Garrison bonus** at the start of the battle (similarly to *[Adjacency](../rules/rules_02_initial_bonus_points.md)* bonuses). It’s allowed to place both your *Garrison* units on the same territory for a double *Garrison* bonus.

__**Support**__ - faction places this unit on their own lands to boost *Adjacency* **+10 base bonus** in battles where this faction either attacking or defending. Does not give any bonus when the land with **Support** on it is attacked. 

__**Saboteur**__ - can be deployed on other factions' lands to try and catch their **Support** and erase its bonus. Has no effect for neutral faction.

Units only provide bonuses for factions directly involved in the battle - attacker or a defender. They do not give any bonuses for neutral side in the battle.

Unit bonuses are [doubled in capital sieges](../rules/rules_10_sieging_capitals.md).

<!---
keywords:  
aliases: 
-->
## 7. Key Members and Official Actions

`📑 Battle Rules`

Most of the game is automated by the bot: 
- players can use **/faction_battle** command to [start a battles](../rules/rules_01_how_battles_start.md);
- and **/faction_action** to do actions such as [moving units](../rules/rules_06_map_units.md) or [preparing attacks](../rules/rules_14_faction_actions.md);
- or multiple other commands such as **/faction_balance**, **/faction_craft** etc.

Most of these commands are only available to **Key faction members** - people who are authorized to have access to sensitive faction info and can perform actions or use resources on faction's behalf.

Faction are free to have their own processes to assign new key members using **/vouch_for** command.

There's also a special command **/send_gm_action** that can be used by any player to send the GM a message or "officially" confirm or request things.

Just use **/send_gm_action <text>** command and the bot will ping me in a special hidden channel.

> **/send_gm_action** PS declares that we love this factions game!

> **/send_gm_action** We agree to the terms and sign the contract with the Fighters Guild

> **/send_gm_action** We offer DC to trade lands with us for <...>

These actions are not binding to other players - they can still betray such the pacts or promises, but they are useful to have a record of what was "officially" agreed and when.

<!---
keywords:  
aliases: 
-->
## 8. Sellswords

`📑 Battle Rules`

**Sellswords** - faction-less people who can too participate in battles - basically, everyone on the server who did not join any faction. 

When playing as a sellsword, you need to [send your commands](../rules/rules_03_general_gameplay.md) with a faction tag you fight for, like this - **CI attack**, **DC defend** etc

Alternatively, you can go along with Fighters Guild's active contract by _just typing "hit" during the battle_ - this way the hit will be directed to the correct side automatically.

While fighting as a **sellsword**, to check your [cooldown](../rules/rules_03_general_gameplay.md) or [charged](../rules/rules_04_charged_hits.md) stats - just send **battle:me** command to the _Factions Game Bot_ as a private message.

**Sellswords** have no access to faction chats, therefore they do not represent anyone nor participate in diplomacy, can't see other people's stats in battle, and essentially missing the most part of this game. 

Being a sellswords could be viewed either as a tutorial stage of the game, or a low-participation mode of it.

But **sellswords** are **welcome** to participate in [factions chat](https://discord.com/channels/562910943848169472/995639554042249246) and other role-playing channels as well as this forum, and of course participation in battles is greatly encouraged!

Once you're ready for a more serious commitment - please pick a faction 🙂

<!---
keywords:  
aliases: 
-->
## 9. Corruption

`📑 Battle Rules`

Each cycle, after all factions have made their moves, it's a **Corruption** turn - an NPC force, which attacks a random land on the island and all factions can only defend (or abstain).

Since during a corrupted battle all faction are on the same side, there are no combo steals possible and bonuses do not get erased.

Basic rules:

- **Corruption** does a random roll to determine damage every several minutes;
- if **Corruption** reaches the goal first, it covers the whole area and the land is lost;
- if combined factions' score reaches the goal first, the land is defended and stays under control of its owner;

To determine the next **Corruption** target, a random roll is performed by GM a few days in advance before the battle, following these rules:
- **Corruption** cannot attack capitals;
- **Corruption** cannot attack the same land twice in a row;
- **Corruption** cannot attack the lands adjacent to its previous target;
- **Corruption** can attack the land where the previous normal battle just happened.

> *Example: The first corrupted battle happened in Timeless Desert, so all capitals, 6 adjacent lands and the Desert itself were excluded from the next random roll for Corruption attack.*

By participating in battles with Corruption, factions can [earn special items and get useful rewards](../rules/rules_11_battle_rewards.md).

<!---
keywords:  
aliases: 
-->
## 10. Sieging Capitals

`📑 Battle Rules`

In all battles where **a faction capital land** is attacked, it gets **double initial bonus points**. 

This double points rule is in favor of original capital owner:
- If DC attacks MT capital - MT gets double bonus;
- If DC holds MT capital, and Minds try to take it back - MT gets double bonus.

Double bonus rule **does** apply to [units](../rules/rules_06_map_units.md) and [adjacency points](../rules/rules_02_initial_bonus_points.md), but **does not** apply to [artefacts](../rules/rules_13_corrupted_artefacts.md).

If attack on a capital is successful, the winner has choice to make:

- Burn the city.

    In this case the capital is destroyed, meaning no double bonus for original owner if they want to recapture, but they can establish a new capital in their remaining lands.

- Keep the city.

    In this case, the attacker keeps the city, but the original owner will get double bonus when they attempt to recapture it.

Using all possible bonuses for own capital defense can result in a battle starting where defender is already halfway there to the goal.  

For this reason capital sieges are rare and usually happen only when the attacker is confident in their victory.

<!---
keywords:  
aliases: 
-->
## 11. Battle Rewards

`📑 Term`

Participating in battles yields rewards both in gold and increased [charged bonuses](../rules/rules_04_charged_hits.md) for the next battle.

The gold reward is simply your hits in the battle times 10: **11 hits = 110G**.

Based on your involvement, you're assigned a title that gives you higher charged bonus in the following battle:
- [B] **Best** - **7 pts** - awarded to players with the highest hits;
- [T] **Top** - **5 pts** - awarded to players who were best in their faction;
- [E] **Elite** - **3pts**- all other players in top 10;
- [V] **Veteran** - **2pts** - all other players with 5+ hits.

If the best player in a faction is already the best warrior overall, this role is awarded to the second-best player in faction.

If more than one player eligible for the role - they all get it.

For example, if in a battle both **T95** and **Eldar** did 21 hits (more than anyone else), then in the next battle they both will have 7pts bonus.

To be eligible for any of the titles, the minimum of 5 hits is required.  
A player cannot be a Top or Elite Warrior without meeting the requirement for Veteran Warrior.

<!---
keywords:  
aliases: 
-->
## 12. Using Power Ups

`📑 Battle Rules`

**Power-ups** are "paid" bonuses in battles, you use your own gold and pay to win, easy.

Three bot commands are available during the battles:

> **/influence, cost: 20G, +10pts** - This command checks if you **won** at least one Influence game since your previous hit, if so - deals 10 damage.
>
> **How to use:** do at least 1 hit in battle, go play Influence, get back in [battle channel](https://discord.com/channels/562910943848169472/995641432742297731) and do /influence command.

> **/duel, cost 30G, +10pts** - This command checks if you **played** at least one Influence Duel since your previous hit, if so - deal 10 damage.
>
> **How to use:** do at least 1 hit in battle, go play Influence Duels, get back in [battle channel](https://discord.com/channels/562910943848169472/995641432742297731) and use /duel command. You don't have to win this duel, but both players need to have finished at least one turn.

> **/pay-to-win, cost 100G, +10pts** - This command just deals 10 damage.
>
> **How to use:** pure pay to win.

*Power-ups are separate from [combos](../rules/rules_03_general_gameplay.md) and have no effect on them.*
*Every player can use each power-up only once per battle.*

<!---
keywords:  
aliases: 
-->
## 13. Corrupted Artefacts

`📑 Battle Rules`

After each *[Corrupted Battle](../rules/rules_09_corruption.md)* factions are able to gather residue *Corrupted Matter* and use it to build powerful artefacts.

Each faction gets **1 sample** if factions win together. However, if factions lose, only the faction attacked directly by Corruption gets a sample.

To complete the research and create an artefact, faction needs to collect **2 samples**.

***Corrupted artefacts***

These artefacts can be acquired via *Corrupted Research*:

**Corrupted Shards** - can be used by the attacking faction. Adds points to their initial attack bonus. This is a single use artefact.

**Corrupted Mines** - can be deployed on any lands the faction owns at the moment. If that land is attacked, **Mines** add to the faction's initial defense bonus. This is a single use artefact.

Bonuses from artefacts are **not** doubled in the [capital sieges](../rules/rules_10_sieging_capitals.md).

**Mines** do **not** have to be deployed immediately, faction can hold the mines without deploying them for as long as needed. Once deployed, mines cannot be moved.

The use of artifacts is not visible to other faction until the battle actually starts and [initial bonuses](../rules/rules_02_initial_bonus_points.md) are applied.

> To deploy the **Mines** or **Shards** you should use **/faction_action** command, for more info about these items please use **/faction_action_help**.

<!---
keywords:  
aliases: 
-->
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

Support is free, while sabotage has a price attacked to it. Note that **sabotage price is charged from personal balance unless you're a [key member](../rules/rules_07_key_members.md) of your faction**.

The challenge result is determined by the following formula:  _(support) / (support + sabotage) = xx% completion._

Action **in faction lands** require 25% to pass.
Action **in foreign lands** require 50% to pass.

<!---
keywords:  
aliases: 
-->
## 15. WAR Items

`📑 Battle Rules`

There are three types of items - Weapons, Armor and Relics (WAR for short), all items are unique but items of the same type give the same bonus.

> **Weapon**  
> Basic: +1pt damage  
> Epic: +2pts damage
>
> _T95 attacks with 👊 **Persuasive Knuckles**! +1pt damage!_
>
> Weapons are the most powerful of WAR items - it makes your hits twice as effective, dealin 2pts of damage instead of 1pt.
>
> Yes, this includes the damage done in combos.

> **Armor**  
> Basic: +1pt charge  
> Epic: +3pt charge
>
> _Muslim gets +1pt charge from 🧣 **Scarf of Unsolicited Coziness**_
>
> Armor gives you a +1pt bonus to your charge,  but only if you're already charged.

> **Relic**  
> Basic: +5m charged time  
> Epic: +10m charged time
>
> _Bubaleh gets +5m charged time from 🎲 **Pseudorandom Dice**_
>
> These items reduce your cooldowns and increase your charged time, to give you more flexibility in syncing with other people.

You can only get these benefits from one of each WAR items, so the best "build" is just having 1  epic weapon + 1 epic armor + 1 epic relic in your inventory.

As we learned in the recent battle, sharing items with others can increase the faction's strength, and gives room for cooperation.

**_All 60 items were randomly distributed to players, at this moment there are no plans to add more items._**

<!---
keywords:  
aliases: 
-->
## 16. Resources

`📑 Battle Rules`

There are three primary resources:  
`🪨 Mineral Resources [MIN]`  
`🧪 Science Resources [SCI]`  
`⚡️ Energy  Resources [ERG]`

When the rules or item descriptions mentions RES - it means **any resource** (any ONE resource, no mixing).

**How resources are produced**

Each time you collect income with **/faction_collect_income**, your faction's lands contribute resources towards faction's inventory.

Each land generates one resource that cannot be changed, as determined via [this random roll](https://discord.com/channels/562910943848169472/1109586327328006274), apart from Timeless Desert which produces `⌛ Temporal Resources`.

The **base value is 1 RES.** However, production fluctuates - each 24h new **global** modifiers are randomly generated.
Example:
`🪨+` means **all** sources on the island produce 2 MIN today;
`🧪=` means **all** sources stay at base value of 1 RES today;
`⚡️-` means **all** production of this resource drops to 0 RES today.

RES productions is  each land can be halted in the following cases:
- if the land is attacked - for 5 eons;
- if the land changes owners - 10 eons.

**Timeless Desert** produces its own type of resources - `⏳ Temporal Resources [TMP]`. It can be transmuted into 2 units of any other resource, or 1 of each (3 total).

**[Learn how resources are used in battles](../rules/rules_17_spec_ops.md)**

<!---
keywords:  
aliases: 
-->
## 17. Special Operations

`📑 Battle Rules`

Factions can start three types of special operations:
- `🪨 Brutal Force` started from `🪨 Mineral Resources` 
- `🧪 Special Tactics` started from `🧪 Science Resources`
- `⚡️ Advanced Weapons` started from `⚡️ Energy Resources`

These weapons work a bit like rock-paper-scissors game: `🪨` beats `🧪` beats ️`⚡️` beats `🪨`.

When faction uses any of these resources during battle with **/spec_op** command, they start a respective **60m** *Special Operation*.

During the operation all factions' [key members](../rules/rules_07_key_members.md) can support or undermine it by using [raw resources](../rules/rules_16_resources.md) based on the above rock-paper-scissors principle. 

To do that just type "min", "sci" or "erg". Optionally, you can specify the amount of RES to spend: "min 5" (from 1 to 10, 1 is default).

The cooldown on this action is **10s** enforced by the bot and the slowmode.

When the spec-op timer runs out, the faction which spent the most RES gets bonus points equal to **what was spent by both sides**.

<!---
keywords:  
aliases: 
-->