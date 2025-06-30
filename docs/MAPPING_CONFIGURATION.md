# Fast Mute Mapping Configuration

The Mute Assist Mod allows you to configure reason-to-duration mappings for the `/mf` (fast mute) command. This lets you customize which durations are automatically applied to specific mute reasons.

## Configuration Commands

### Adding/Updating Mappings
```bash
/muteassist config mapping add <reason> <duration>
```

**Examples:**
```bash
/muteassist config mapping add "Spam" "30m"
/muteassist config mapping add "Hakaret" "6h"
/muteassist config mapping add "Custom Violation" "2h"
```

### Removing Mappings
```bash
/muteassist config remove mapping <reason>
```

**Examples:**
```bash
/muteassist config remove mapping "Spam"
/muteassist config remove mapping "Chat Kirletimi"
```

### Listing All Mappings
```bash
/muteassist config mapping list
```

Shows all configured reason-duration mappings.

### Getting Help
```bash
/muteassist config mapping
```

Shows available mapping commands.

## How It Works

1. **Add a mapping**: Associates a reason with a specific duration
2. **Use fast mute**: `/mf PlayerName Spam` automatically becomes `/mute PlayerName 30m Spam`
3. **Auto-completion**: Tab completion shows reasons with their durations as tooltips
4. **Fallback**: If a reason isn't mapped, the chat opens for manual duration entry

## Default Mappings

| Reason | Duration | Severity |
|--------|----------|----------|
| Chat Kirletimi | 10m | Light |
| Cinsellik | 60m | Moderate |
| Hakaret | 3h | Serious |
| Dini Muhabbet | 9h | Very Serious |
| Ailevi Küfür | 12h | Severe |

## Advanced Features

### Auto-Addition to Lists
When you add a mapping:
- The **reason** is automatically added to custom reasons if not already present
- The **duration** is automatically added to custom durations if not already present

### Persistent Configuration
All mappings are saved to `config/mute-assist.json` and persist between game sessions.

### Tab Completion
- Reason suggestions show duration tooltips: `Hakaret (3h)`
- Duration suggestions available when adding mappings
- Existing reasons suggested when removing mappings

## Examples

### Setting up Custom Server Rules
```bash
# Light violations
/muteassist config mapping add "Mild Spam" "5m"
/muteassist config mapping add "Caps Lock" "10m"

# Moderate violations  
/muteassist config mapping add "Harassment" "1h"
/muteassist config mapping add "Inappropriate Language" "2h"

# Severe violations
/muteassist config mapping add "Hate Speech" "1d"
/muteassist config mapping add "Serious Threats" "7d"
```

### Updating Existing Rules
```bash
# Update existing mapping
/muteassist config mapping add "Hakaret" "6h"  # Was 3h, now 6h

# Remove outdated rule
/muteassist config remove mapping "Old Rule"
```

## Configuration File

The mappings are stored in `config/mute-assist.json`:

```json
{
  "reasonDurationMap": {
    "Chat Kirletimi": "10m",
    "Hakaret": "3h",
    "Custom Rule": "1h"
  }
}
```

## Benefits

✅ **Consistency**: Same violations always get same duration  
✅ **Speed**: No need to remember or type durations  
✅ **Flexibility**: Easily customize for your server's rules  
✅ **Error Prevention**: Eliminates duration typos  
✅ **Team Coordination**: All moderators use same standards  

## Tips

1. **Start with defaults**: Modify existing mappings rather than creating from scratch
2. **Use clear reasons**: Make reason names descriptive and consistent
3. **Test changes**: Use `/mf` commands to verify mappings work as expected
4. **Document rules**: Keep a server-side record of your custom mappings
5. **Share config**: The config file can be shared between moderators
