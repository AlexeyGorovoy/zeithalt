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