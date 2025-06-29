# Mute Assist Mod - Usage Examples

This document provides practical examples of how to use the Mute Assist Mod for Minecraft 1.20.1.

## Installation Verification

After installing the mod, you should see the following message in your logs:
```
[INFO] Mute Assist Mod initialized!
[INFO] Mute Assist Mod client initialized!
```

## Command Completion Examples

### Basic Mute Command

1. **Type the command**: `/mute `
2. **Press TAB**: See suggestions for online players
3. **Continue typing**: `/mute PlayerName `
4. **Press TAB**: See duration suggestions like `10m`, `1h`, `1d`, `perm`
5. **Complete the command**: `/mute PlayerName 1h `
6. **Press TAB**: See Turkish mute reasons

### Complete Example Session

```bash
# Start typing the command
/mute

# Press TAB to see player suggestions
/mute Alice
/mute Bob
/mute Charlie

# Select a player and continue
/mute Alice 

# Press TAB to see duration suggestions
/mute Alice 10m
/mute Alice 30m
/mute Alice 1h
/mute Alice 1d
/mute Alice perm

# Select duration and continue
/mute Alice 1h 

# Press TAB to see reason suggestions
/mute Alice 1h Chat Kirletimi
/mute Alice 1h Hakaret
/mute Alice 1h Spam
/mute Alice 1h Küfür Kullanımı

# Final command
/mute Alice 1h Chat Kirletimi
```

## Configuration Examples

### Adding Custom Reasons

```bash
# Add a custom reason
/muteassist config add reason "Özel Sebep"

# Add multiple reasons
/muteassist config add reason "Sunucu Kurallarını İhlal"
/muteassist config add reason "Yetkisiz Reklam"
```

### Adding Custom Durations

```bash
# Add custom durations
/muteassist config add duration "2h"
/muteassist config add duration "15d"
/muteassist config add duration "90d"
```

### Removing Custom Items

```bash
# Remove a custom reason
/muteassist config remove reason "Eski Sebep"

# Remove a custom duration
/muteassist config remove duration "2h"
```

### Managing Configuration

```bash
# Show mod information
/muteassist

# Show configuration commands
/muteassist config

# Reload configuration
/muteassist config reload
```

## Advanced Usage

### Partial Matching

The mod supports partial matching for reasons when enabled in config:

```bash
# Typing "spam" will suggest:
/mute Player 1h Spam
/mute Player 1h Chat Kirletimi  # (if it contains "spam" in Turkish)
```

### Multiple Command Support

The mod works with both commands:

```bash
# Standard mute command
/mute PlayerName 1h Hakaret

# Temporary mute command (alias)
/tempmute PlayerName 30m Spam
```

## Configuration File Example

Location: `config/mute-assist.json`

```json
{
  "customDurations": [
    "5m", "10m", "15m", "30m", "45m", "1h", "2h", "3h", 
    "6h", "12h", "1d", "3d", "7d", "14d", "30d", "perm"
  ],
  "customReasons": [
    "Chat Kirletimi",
    "Cinsellik",
    "Argo Kelime Kullanımı",
    "Hakaret",
    "Spam",
    "Flood",
    "Caps Lock Kullanımı",
    "Trollük",
    "Özel Sebep",
    "Sunucu Kurallarını İhlal"
  ],
  "enableDebugLogging": false,
  "sortPlayersSuggestions": true,
  "enablePartialReasonMatching": true
}
```

## Performance Tips

1. **Player List Updates**: The mod automatically updates the player list when players join/leave
2. **Efficient Filtering**: Suggestions are filtered in real-time as you type
3. **Memory Usage**: The mod has minimal memory footprint
4. **Debug Mode**: Enable debug logging only when troubleshooting

## Troubleshooting

### Common Issues

1. **No Suggestions Appearing**
   - Ensure Fabric API is installed
   - Check that you're using Minecraft 1.20.1
   - Verify the mod is loaded in the mods list

2. **Configuration Not Saving**
   - Check file permissions in the config directory
   - Ensure the config directory exists
   - Try running `/muteassist config reload`

3. **Player Names Not Showing**
   - Make sure you're connected to a server
   - Check that other players are online
   - Player list updates automatically when players join/leave

### Debug Mode

Enable debug logging in the configuration file:

```json
{
  "enableDebugLogging": true
}
```

This will provide detailed logs about:
- Player list updates
- Suggestion filtering
- Configuration loading
- Command processing

## Best Practices

1. **Regular Updates**: Keep your custom reasons and durations up to date
2. **Server Compatibility**: Test the mod on your target servers
3. **Backup Config**: Keep a backup of your configuration file
4. **Performance**: Disable debug logging in production use

## Integration with Server Plugins

This mod works with popular server mute plugins:

- **EssentialsX**: `/mute` command
- **LuckPerms**: `/lp user <user> permission set <permission> false`
- **LiteBans**: `/mute` command
- **AdvancedBan**: `/mute` command

The mod only provides suggestions - the actual mute functionality depends on your server's permission system.
