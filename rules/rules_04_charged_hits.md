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