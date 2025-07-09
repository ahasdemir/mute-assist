# Mute Assist Mod

A client-side Fabric mod for Minecraft 1.20.1 that provides **visual command suggestions** for mute commands, just like native Minecraft commands! Perfect for server administrators and moderators who want to streamline their moderation workflow.

## 🎯 Features

### Visual Command Suggestions
- **Native-like Tab Completion**: Shows suggestions in the same style as native Minecraft commands
- **Player Name Suggestions**: Real-time suggestions for online players with visual feedback
- **Duration Suggestions**: Pre-defined common durations with helpful tooltips (10m, 30m, 1h, 3h, 1d, 7d, 30d, perm)
- **Turkish Mute Reasons**: Comprehensive list of Turkish mute reasons with visual previews
- **Tooltips**: Helpful descriptions for durations and reasons

### Supported Commands
- `/mute <player> <duration> <reason>` - Standard mute command with full suggestions
- `/tempmute <player> <duration> <reason>` - Temporary mute alias
- `/unmute <player>` - Unmute command with player suggestions
- **`/mf <player> <reason>`** - **NEW! Fast mute with auto-duration** ⚡

### Visual Experience
✅ **Suggestions appear in dropdown like native commands**  
✅ **Tab completion works seamlessly**  
✅ **Tooltips show additional information**  
✅ **Color-coded suggestions for easy reading**  
✅ **Real-time filtering as you type**  

## 🚀 How It Works

1. **Type a command**: Start typing `/mute ` in chat
2. **See visual suggestions**: A dropdown appears showing online players
3. **Press TAB or click**: Select a player from the visual list
4. **Continue**: Duration suggestions appear with helpful tooltips
5. **Add reason**: Turkish mute reasons appear with previews
6. **Execute**: Press Enter to send the command to the server

### Example Visual Flow:
```
/mute [TAB] → Shows: Alice, Bob, Charlie (online players)
/mute Alice [TAB] → Shows: 10m (10 minutes), 1h (1 hour), 1d (1 day), perm (Permanent mute)
/mute Alice 1h [TAB] → Shows: Chat Kirletimi, Hakaret, Spam, Küfür Kullanımı
/mute Alice 1h Chat Kirletimi [ENTER] → Command sent!
```

### ⚙️ Configurable Fast Mute Mappings
- **Custom reason-duration mappings** for `/mf` command
- **Add/remove/update mappings** through in-game commands  
- **Automatic suggestions** with duration tooltips
- **Consistent moderation** across your team
- **Flexible rule management** for different servers

### ⚙️ Customizable Configuration
- Add your own custom mute reasons
- Add your own custom durations
- Configure reason-to-duration mappings for fast mute
- Enable/disable partial matching for reasons
- Sort player suggestions alphabetically
- Debug logging options

### 🚀 Performance Optimized
- Real-time player list updates
- Efficient suggestion filtering
- Crash-safe implementation
- Minimal performance impact

## Installation

1. **Download Fabric Loader**: Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 1.20.1
2. **Download Fabric API**: Get the [Fabric API](https://modrinth.com/mod/fabric-api) mod
3. **Install the Mod**: Place the `mute-assist-v0.1.3.jar` file in your `mods` folder
4. **Launch Minecraft**: Start Minecraft with the Fabric profile

## Usage

### Basic Commands

The mod enhances the following commands with intelligent suggestions:

```
/mute <player> <duration> <reason>
/tempmute <player> <duration> <reason>
/mf <player> <reason>          # NEW! Auto-duration fast mute
```

### How to Use

#### Single Player / Local World
1. **Type the command**: Start typing `/mute ` or `/mf ` in chat
2. **Press TAB**: Use the Tab key to cycle through suggestions
3. **Player Names**: The mod will suggest online players
4. **Durations**: Common durations like `10m`, `1h`, `1d`, `perm`
5. **Reasons**: Turkish mute reasons or your custom reasons

#### Multiplayer Servers
**Note**: On multiplayer servers, client-side commands may not show tab completion. However, all commands still work perfectly:

1. **Direct commands work**: Type `/mf PlayerName Chat Kirletimi` directly - it will work!
2. **Helper commands**: Use `/mutehelp`, `/mfhelp`, `/tempmutehelp`, `/unmutehelp` for tab completion
3. **Suggestions still appear**: Helper commands show full tab completion and suggestions

### Example Usage

```
# Direct Commands (work in both single/multiplayer)
/mf PlayerName Chat Kirletimi     # Auto-applies 10m duration
/m PlayerName 1h Spam             # Direct mute command
/tm PlayerName 30m Flood          # Direct temp mute
/um PlayerName                    # Direct unmute

# Helper Commands (better for multiplayer tab completion)
/mfhelp PlayerName Chat Kirletimi # Shows the mute command, you press Enter
/mutehelp PlayerName 1h Spam      # Shows the mute command, you press Enter
/tempmutehelp PlayerName 30m Flood # Shows the temp mute command
/unmutehelp PlayerName            # Shows the unmute command
```

## Configuration

### Configuration Commands

Manage your custom reasons, durations, and fast mute mappings with these commands:

```bash
# Add custom reason
/muteassist config add reason "Custom Reason Here"

# Add custom duration
/muteassist config add duration "2h"

# Add/update fast mute mapping
/muteassist config mapping add "Custom Violation" "1h"

# Remove custom reason
/muteassist config remove reason "Old Reason"

# Remove custom duration
/muteassist config remove duration "2h"

# Remove fast mute mapping
/muteassist config remove mapping "Old Violation"

# List all fast mute mappings
/muteassist config mapping list

# Reload configuration
/muteassist config reload

# Show mod info
/muteassist
```

### Configuration File

The mod creates a configuration file at `config/mute-assist.json`:

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
    "Flood"
    // ... more reasons
  ],
  "enableDebugLogging": false,
  "sortPlayersSuggestions": true,
  "enablePartialReasonMatching": true
}
```

### Configuration Options

- **`enableDebugLogging`**: Enable detailed logging for debugging
- **`sortPlayersSuggestions`**: Sort player name suggestions alphabetically
- **`enablePartialReasonMatching`**: Allow partial matching when suggesting reasons

## Default Turkish Mute Reasons

The mod comes with a comprehensive list of Turkish mute reasons:

- Chat Kirletimi
- Cinsellik
- Argo Kelime Kullanımı
- Amacı Dışına Ada Reklam Kullanımı
- Argo Kelime Benzetmeleri
- Chat Amacı Dışında Kullanma
- Hakaret
- Küfür Kullanımı
- Tartışma
- Kışkırtma
- Yetkilileri Rahatsız Etmek
- Sohbete Ada Reklamını Mesaj Olarak Atmak
- Dini Muhabbet
- Siyasi Muhabet
- Link Paylaşımı
- Reklam
- Ailevi Küfür Kullanımı
- Yetkiliyle Özelden Hakaret
- Spam
- Flood
- Caps Lock Kullanımı
- Rahatsız Edici Davranış
- Trollük
- Oyun Bozmak
- Açık Arama
- Gereksiz Etiketleme
- Anlamsız Mesajlar
- Tekrar Eden Mesajlar
- Büyük Harf Kullanımı
- Gereksiz Tartışma Başlatma

## Compatibility

- **Minecraft Version**: 1.20.1
- **Mod Loader**: Fabric
- **Java Version**: 17+
- **Dependencies**: Fabric API

## Development

### Building from Source

1. Clone the repository
2. Run `./gradlew build`
3. Find the compiled JAR in `build/libs/`

### Project Structure

```
src/
├── client/java/com/muteassist/
│   ├── MuteassistClient.java      # Client-side initialization
│   ├── MuteAssistCommand.java     # Command registration and suggestions
│   └── config/
│       └── MuteAssistConfig.java  # Configuration management
└── main/java/com/muteassist/
    └── Muteassist.java            # Main mod class
```

## Contributing

Contributions are welcome! Please feel free to submit issues and pull requests.

### Adding New Features

1. Fork the repository
2. Create a feature branch
3. Implement your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the CC0 1.0 Universal Licence - see the [LICENSE](LICENSE) file for details.

## Support

If you encounter any issues or have suggestions:

1. Check the [Issues](https://github.com/your-username/mute-assist-mod/issues) page
2. Create a new issue with detailed information
3. Include your Minecraft version, Fabric Loader version, and mod version

## Changelog

### Version v0.1.2

- **Fixed Configuration Serialization**: Fixed the `reasonDurationMap` not being properly saved to the configuration file
- **Enhanced Mapping System**: The reason-to-duration mapping is now fully persistent and configurable
- **Improved Config Loading**: Added proper initialization to ensure mappings are loaded correctly
- **Better Error Handling**: Enhanced configuration loading with proper fallbacks and null checks

### Version v0.1.1
- **NEW: `/mf` (fast mute) command with auto-duration detection**
- Added reason-to-duration mapping table integration
- Enhanced tab-completion with duration tooltips for `/mf` command
- Improved direct command execution system
- Updated configuration system to support reason-duration mappings

### Version v0.1.0
- Initial release
- Smart tab completion for `/mute` and `/tempmute` commands
- Turkish mute reasons support
- Configurable custom reasons and durations
- Real-time player list updates
- Configuration management commands

---

**Note**: This is a client-side mod. It only provides suggestions and does not actually execute mute commands. The actual mute functionality depends on your server's permission system and mute plugin.
