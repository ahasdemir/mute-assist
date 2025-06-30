# Mute Assist Mod - Helper Commands Guide

## Important: This Mod Uses Helper Commands

**This mod does NOT override server `/mute` commands** to avoid conflicts. Instead, it provides helper commands that assist you in building the proper server commands.

## Quick Reference

| Helper Command | Purpose | Server Command It Builds |
|----------------|---------|--------------------------|
| `/mutehelp` | Assists with mutes | `/mute` |
| `/tempmutehelp` | Assists with temp mutes | `/tempmute` |
| `/unmutehelp` | Assists with unmutes | `/unmute` |
| `/m` | Quick mute shortcut | `/mute` |
| `/tm` | Quick tempmute shortcut | `/tempmute` |
| `/um` | Quick unmute shortcut | `/unmute` |

## How It Works

1. **Type helper command**: `/m Player123 1h spam`
2. **Mod prepares server command**: `/mute Player123 1h spam` appears in your chat
3. **Press Enter**: Command is sent to the server

## Detailed Usage Examples

### Example 1: Quick Mute
```
You type: /m BadPlayer 2h Küfür
Result: Chat opens with "/mute BadPlayer 2h Küfür"
Action: Press Enter to execute on server
```

### Example 2: Step-by-Step with Suggestions
```
Step 1: /mutehelp [TAB] → Shows online players
Step 2: /mutehelp Player123 [TAB] → Shows durations (30m, 1h, 2h, etc.)
Step 3: /mutehelp Player123 1h [TAB] → Shows reasons (Küfür, Spam, etc.)
Step 4: /mutehelp Player123 1h Spam → Opens chat with "/mute Player123 1h Spam"
Step 5: Press Enter to send to server
```

### Example 3: Partial Commands
```
/m Player123 → Opens chat with "/mute Player123 " (add duration & reason)
/m Player123 1h → Opens chat with "/mute Player123 1h " (add reason)
/tm Player → Opens chat with "/tempmute Player " (for temp mutes)
/um Player → Opens chat with "/unmute Player" (for unmutes)
```

## Built-in Turkish Reasons

- **Küfür** - Swearing/Profanity
- **Spam** - Spamming messages
- **Reklam** - Advertising
- **Trolling** - Trolling behavior
- **Saygısızlık** - Disrespect
- **Caps** - Writing in all caps
- **Flood** - Message flooding
- **Taciz** - Harassment
- **Hakaret** - Insult
- **Kavga** - Fighting/Arguing

## Built-in Durations

- **30m** - 30 minutes
- **1h** - 1 hour
- **2h** - 2 hours
- **6h** - 6 hours
- **12h** - 12 hours
- **1d** - 1 day
- **3d** - 3 days
- **7d** - 7 days
- **14d** - 14 days
- **30d** - 30 days
- **perm** - Permanent

## Configuration Commands

### View Configuration
```
/muteassist                    # Show mod info
/muteassist config            # Show config options
```

### Add Custom Content
```
/muteassist config add reason "Kötü Davranış"
/muteassist config add duration "45m"
/muteassist config add duration "2w"
```

### Remove Custom Content
```
/muteassist config remove reason "Kötü Davranış"
/muteassist config remove duration "45m"
```

## Advantages of Helper Commands

✅ **No Server Conflicts**: Works with ANY server's mute system  
✅ **Full Compatibility**: No server-side installation needed  
✅ **Same Features**: All suggestions and tab completion  
✅ **Easy to Use**: Just press Enter after building command  
✅ **Safe**: Cannot break server plugins  

## Troubleshooting

### Problem: "Unknown command"
**Solution**: Use helper commands (`/mutehelp`, `/m`) not server commands (`/mute`)

### Problem: "No suggestions appear"
**Solutions**:
- Make sure you're connected to a server
- Try pressing TAB after typing the command
- Check that players are visible in the player list

### Problem: "Command doesn't work on server"
**Solution**: The helper command prepares the server command for you - press Enter to send it

## Server Compatibility

This mod works with ALL servers that have:
- `/mute` commands
- `/tempmute` commands  
- `/unmute` commands

Popular compatible plugins:
- **EssentialsX**
- **LiteBans**
- **AdvancedBan**
- **BanManager**
- **Liberty Bans**
- And many others!

## Tips for Moderators

1. **Muscle Memory**: Use `/m` for quick permanent mutes
2. **Temp Mutes**: Use `/tm` for temporary mutes
3. **Quick Unmutes**: Use `/um` for fast unmuting
4. **Custom Reasons**: Add server-specific reasons to config
5. **Partial Commands**: Don't worry about completing everything - use partial commands to get started

## Configuration File Location

Your settings are saved in:
```
.minecraft/config/mute-assist.json
```

This file contains your custom reasons and durations.
