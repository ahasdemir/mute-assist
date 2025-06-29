# Mute Assist Mod - Quick Test Guide

## Testing the Visual Suggestions

After installing the mod and launching Minecraft, follow these steps to test the visual command suggestions:

### 1. Join a Server or Test in Singleplayer
- Open Minecraft with the mod installed
- Join a multiplayer server (or create a world with cheats enabled)

### 2. Test Player Suggestions
1. Press **T** to open chat
2. Type `/mute ` (with space)
3. Press **TAB** - you should see a dropdown with online player names
4. Try typing a few letters of a player name and press TAB again

### 3. Test Duration Suggestions
1. Type `/mute PlayerName ` (replace with actual player)
2. Press **TAB** - you should see duration options like:
   - `10m` with tooltip "10 minutes"
   - `1h` with tooltip "1 hour"
   - `1d` with tooltip "1 day"
   - `perm` with tooltip "Permanent mute"

### 4. Test Reason Suggestions
1. Type `/mute PlayerName 1h ` 
2. Press **TAB** - you should see Turkish reasons like:
   - "Chat Kirletimi"
   - "Hakaret"
   - "Spam"
   - "Küfür Kullanımı"

### 5. Test Other Commands
- Try `/tempmute` - should work the same as `/mute`
- Try `/unmute PlayerName` - should suggest player names

### 6. Test Configuration Commands
- Type `/muteassist` - shows mod info
- Type `/muteassist config` - shows configuration options
- Try adding custom reasons: `/muteassist config add reason "My Custom Reason"`

## Expected Visual Behavior

✅ **Dropdown suggestions** appear below the chat input  
✅ **Tab completion** cycles through suggestions  
✅ **Tooltips** show additional information  
✅ **Real-time filtering** as you type  
✅ **Color-coded text** for different suggestion types  

## Troubleshooting

### If suggestions don't appear:
1. Check that Fabric API is installed
2. Verify you're using Minecraft 1.20.1
3. Check the log for any error messages
4. Make sure you're connected to a server (for player suggestions)

### If commands don't execute:
- The mod only provides suggestions, not actual mute functionality
- Make sure your server has a compatible mute plugin
- Check that you have proper permissions on the server

### Debug Mode:
1. Edit `config/mute-assist.json`
2. Set `"enableDebugLogging": true`
3. Check logs for detailed information

## Performance Test

The mod should have minimal impact on performance:
- Suggestions appear instantly
- No lag when typing
- Memory usage remains low
- Works smoothly with 100+ players online

## Success Indicators

If everything is working correctly, you should see:
- Visual dropdown suggestions like native Minecraft commands
- Smooth tab completion
- Helpful tooltips for durations
- Real-time player list updates
- Color-coded suggestion text

---

**Note**: This mod enhances the command input experience but doesn't execute the actual mute commands. Server-side mute functionality depends on your server's plugin configuration.
