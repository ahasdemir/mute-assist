# Fast Mute Command (`/mf`) Documentation

## 🚀 Overview

The `/mf` (mute fast) command is a powerful new feature that automatically determines mute duration based on the violation reason. No need to remember or type durations manually!

## ⚡ How It Works

When you type `/mf <player> <reason>`, the mod:
1. Looks up the reason in the predefined duration table
2. Automatically applies the correct duration
3. Sends the complete mute command to the server

## 📝 Usage Examples

### Basic Fast Mute
```bash
/mf BadPlayer Cinsellik
# Automatically sends: /mute BadPlayer 60m Cinsellik
```

### More Examples
```bash
/mf Player123 Hakaret           → /mute Player123 3h Hakaret
/mf TrollPlayer Chat Kirletimi  → /mute TrollPlayer 10m Chat Kirletimi
/mf SpamBot Reklam             → /mute SpamBot 12h Reklam
```

### Tab Completion
```bash
/mf Player [TAB]     → Shows fast mute reasons with durations
/mf Player C [TAB]   → Shows: Cinsellik (60m), Chat Kirletimi (10m)
```

## 📊 Predefined Duration Table

| Reason | Duration | Description |
|--------|----------|-------------|
| **Chat Kirletimi** | 10m | Chat pollution |
| **Cinsellik** | 60m | Sexual content |
| **Argo Kelime Kullanımı** | 60m | Slang usage |
| **Amacı Dışında AdaReklam Kullanımı** | 60m | Misuse of advertising |
| **Argo Kelime Benzetmeleri** | 30m | Slang word similarities |
| **Chati Amacı Dışında Kullanma** | 35m | Chat misuse |
| **Hakaret** | 3h | Insult |
| **Küfür Kullanımı** | 3h | Profanity |
| **Tartışma** | 3h | Arguing |
| **Kışkırtma** | 3h | Provocation |
| **Yetkilileri Rahatsız Etmek** | 3h | Bothering staff |
| **Sohbete Ada Reklamını Mesaj Olarak Atmak** | 3h | Advertising in chat |
| **Dini Muhabbet** | 9h | Religious discussion |
| **Siyasi Muhabbet** | 9h | Political discussion |
| **Link Paylaşımı** | 12h | Link sharing |
| **Reklam** | 12h | Advertising |
| **Ailevi Küfür Kullanımı** | 12h | Family insults |
| **Yetkiliye Özelden Hakaret** | 12h | Private insult to staff |

## 🎯 Smart Features

### Auto-Duration Detection
- Type reason → Duration automatically applied
- No need to memorize duration for each violation
- Consistent punishment across all moderators

### Helpful Suggestions
- Tab completion shows available reasons
- Tooltips display duration for each reason
- Visual feedback shows what will be executed

### Fallback Behavior
```bash
# If reason not in table:
/mf Player CustomReason
# Opens chat with: "/mute Player  CustomReason"
# You can manually add duration
```

## 🔧 Command Variations

### Full Command
```bash
/mf <player> <reason>
# Example: /mf BadPlayer Hakaret
# Result: /mute BadPlayer 3h Hakaret
```

### Show Available Reasons
```bash
/mf <player>
# Shows list of all fast mute reasons with durations
```

### Help
```bash
/mf
# Shows basic usage information
```

## 💡 Advantages Over Regular Commands

### ✅ **Speed**
- No need to type duration
- One command instead of three parameters
- Instant execution

### ✅ **Consistency**
- Same punishment for same violation
- No human error in duration selection
- Standardized across all staff

### ✅ **Ease of Use**
- Remember reasons, not durations
- Tab completion with visual hints
- Clear tooltips showing what will happen

### ✅ **Flexibility**
- Still works if reason not in table
- Can be combined with other commands
- Integrates with existing mod features

## 🎮 Practical Workflow

### Old Way (Multiple Steps)
```bash
1. /m Player123 [TAB] → select duration
2. Think: "What duration for this violation?"
3. /m Player123 3h [TAB] → select reason
4. /m Player123 3h Hakaret → send
```

### New Way (One Step)
```bash
1. /mf Player123 Hakaret → done!
```

## 🔄 Integration with Other Commands

The `/mf` command works alongside existing commands:

| Command | Purpose | Use Case |
|---------|---------|----------|
| `/mf` | Fast mute with auto-duration | Standard violations with known penalties |
| `/m` | Manual mute with custom duration | Special cases or custom punishments |
| `/mutehelp` | Helper that opens chat | When you want to see the command before sending |

## 📈 Performance & Efficiency

- **Instant lookup**: Reason-to-duration mapping is cached
- **No server load**: All processing happens client-side
- **Minimal latency**: Direct command sending without chat interaction
- **Memory efficient**: Small mapping table with common violations

## 🛠️ Customization

The duration table is fully customizable through the configuration file:
```json
{
  "reasonDurationMap": {
    "Custom Violation": "45m",
    "Special Case": "6h"
  }
}
```

Future versions may include in-game commands to manage the mapping table.

## 📋 Quick Reference

| Action | Command | Result |
|--------|---------|--------|
| Fast mute | `/mf Player Reason` | Auto-duration mute |
| Show reasons | `/mf Player` | List available reasons |
| Help | `/mf` | Usage information |
| Manual mute | `/m Player Duration Reason` | Custom duration |

The `/mf` command revolutionizes the moderation workflow by combining speed, consistency, and ease of use! 🚀
