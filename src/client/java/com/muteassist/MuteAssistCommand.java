package com.muteassist;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.muteassist.config.MuteAssistConfig;
import com.muteassist.util.CommandUtil;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class MuteAssistCommand {

    private static List<String> onlinePlayers = new ArrayList<>();

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        // Register client-side assistance commands that don't conflict with server commands
        // These use different names to avoid conflicts with server /mute, /tempmute, /unmute
        
        // Register /muteassist command with suggestions (this acts as a helper)
        dispatcher.register(literal("mutehelp")
                .then(argument("player", StringArgumentType.word())
                        .suggests(MuteAssistCommand::suggestPlayers)
                        .then(argument("duration", StringArgumentType.word())
                                .suggests(MuteAssistCommand::suggestDurations)
                                .then(argument("reason", StringArgumentType.greedyString())
                                        .suggests(MuteAssistCommand::suggestReasons)
                                        .executes(context -> {
                                            // Build the command and put it in chat for user to send
                                            String player = StringArgumentType.getString(context, "player");
                                            String duration = StringArgumentType.getString(context, "duration");
                                            String reason = StringArgumentType.getString(context, "reason");
                                            
                                            String fullCommand = String.format("/mute %s %s %s", player, duration, reason);
                                            CommandUtil.openChatWithCommand(fullCommand);
                                            CommandUtil.sendActionBar("§aMute command ready! Press Enter to send.");
                                            return 1;
                                        }))
                                .executes(context -> {
                                    // Partial command (player + duration only)
                                    String player = StringArgumentType.getString(context, "player");
                                    String duration = StringArgumentType.getString(context, "duration");
                                    
                                    String partialCommand = String.format("/mute %s %s ", player, duration);
                                    CommandUtil.openChatWithCommand(partialCommand);
                                    return 1;
                                }))
                        .executes(context -> {
                            // Partial command (player only)
                            String player = StringArgumentType.getString(context, "player");
                            
                            String partialCommand = String.format("/mute %s ", player);
                            CommandUtil.openChatWithCommand(partialCommand);
                            return 1;
                        })));

        // Register /tempmutehelp as helper for tempmute
        dispatcher.register(literal("tempmutehelp")
                .then(argument("player", StringArgumentType.word())
                        .suggests(MuteAssistCommand::suggestPlayers)
                        .then(argument("duration", StringArgumentType.word())
                                .suggests(MuteAssistCommand::suggestDurations)
                                .then(argument("reason", StringArgumentType.greedyString())
                                        .suggests(MuteAssistCommand::suggestReasons)
                                        .executes(context -> {
                                            String player = StringArgumentType.getString(context, "player");
                                            String duration = StringArgumentType.getString(context, "duration");
                                            String reason = StringArgumentType.getString(context, "reason");
                                            
                                            String fullCommand = String.format("/tempmute %s %s %s", player, duration, reason);
                                            CommandUtil.openChatWithCommand(fullCommand);
                                            CommandUtil.sendActionBar("§aTemp mute command ready! Press Enter to send.");
                                            return 1;
                                        }))
                                .executes(context -> {
                                    String player = StringArgumentType.getString(context, "player");
                                    String duration = StringArgumentType.getString(context, "duration");
                                    
                                    String partialCommand = String.format("/tempmute %s %s ", player, duration);
                                    CommandUtil.openChatWithCommand(partialCommand);
                                    return 1;
                                }))
                        .executes(context -> {
                            String player = StringArgumentType.getString(context, "player");
                            
                            String partialCommand = String.format("/tempmute %s ", player);
                            CommandUtil.openChatWithCommand(partialCommand);
                            return 1;
                        })));

        // Register /unmutehelp for unmute assistance
        dispatcher.register(literal("unmutehelp")
                .then(argument("player", StringArgumentType.word())
                        .suggests(MuteAssistCommand::suggestPlayers)
                        .executes(context -> {
                            String player = StringArgumentType.getString(context, "player");
                            String command = String.format("/unmute %s", player);
                            CommandUtil.openChatWithCommand(command);
                            CommandUtil.sendActionBar("§aUnmute command ready! Press Enter to send.");
                            return 1;
                        })));

        // Register /mfhelp for fast mute assistance with reason suggestions
        dispatcher.register(literal("mfhelp")
                .then(argument("player", StringArgumentType.word())
                        .suggests(MuteAssistCommand::suggestPlayers)
                        .then(argument("reason", StringArgumentType.greedyString())
                                .suggests(MuteAssistCommand::suggestFastMuteReasons)
                                .executes(context -> {
                                    String player = StringArgumentType.getString(context, "player");
                                    String reason = StringArgumentType.getString(context, "reason");
                                    
                                    // Get duration from mapping
                                    String duration = MuteAssistConfig.getInstance().getDurationForReason(reason);
                                    
                                    if (duration != null) {
                                        String command = String.format("/mute %s %s %s", player, duration, reason);
                                        CommandUtil.openChatWithCommand(command);
                                        CommandUtil.sendActionBar(String.format("§aFast mute command ready! Auto-duration: %s", duration));
                                    } else {
                                        // If reason not found in mapping, open chat for manual entry
                                        String partialCommand = String.format("/mute %s  %s", player, reason);
                                        CommandUtil.openChatWithCommand(partialCommand);
                                        CommandUtil.sendActionBar("§eReason not in fast mute table, please add duration manually");
                                    }
                                    return 1;
                                }))
                        .executes(context -> {
                            // Show available fast mute reasons
                            String player = StringArgumentType.getString(context, "player");
                            sendFeedback(context.getSource(), "§eFast Mute Help: Use /mfhelp " + player + " <reason>. Available reasons:");
                            for (String reason : MuteAssistConfig.getInstance().getFastMuteReasons()) {
                                String duration = MuteAssistConfig.getInstance().getDurationForReason(reason);
                                sendFeedback(context.getSource(), "§7- " + reason + " §8(§6" + duration + "§8)");
                            }
                            return 1;
                        }))
                .executes(context -> {
                    sendFeedback(context.getSource(), "§eFast Mute Help: /mfhelp <player> <reason> - Shows mute command with auto-duration");
                    return 1;
                }));

        // Register configuration commands for managing custom reasons and durations
        dispatcher.register(literal("muteassist")
                .then(literal("config")
                        .then(literal("add")
                                .then(literal("reason")
                                        .then(argument("reason", StringArgumentType.greedyString())
                                                .executes(context -> {
                                                    String reason = StringArgumentType.getString(context, "reason");
                                                    MuteAssistConfig.getInstance().addCustomReason(reason);
                                                    sendFeedback(context.getSource(), "Added custom reason: " + reason);
                                                    return 1;
                                                })))
                                .then(literal("duration")
                                        .then(argument("duration", StringArgumentType.string())
                                                .executes(context -> {
                                                    String duration = StringArgumentType.getString(context, "duration");
                                                    MuteAssistConfig.getInstance().addCustomDuration(duration);
                                                    sendFeedback(context.getSource(), "Added custom duration: " + duration);
                                                    return 1;
                                                }))))
                        .then(literal("remove")
                                .then(literal("reason")
                                        .then(argument("reason", StringArgumentType.greedyString())
                                                .suggests(MuteAssistCommand::suggestConfigReasons)
                                                .executes(context -> {
                                                    String reason = StringArgumentType.getString(context, "reason");
                                                    MuteAssistConfig.getInstance().removeReason(reason);
                                                    sendFeedback(context.getSource(), "Removed custom reason: " + reason);
                                                    return 1;
                                                })))
                                .then(literal("duration")
                                        .then(argument("duration", StringArgumentType.string())
                                                .suggests(MuteAssistCommand::suggestConfigDurations)
                                                .executes(context -> {
                                                    String duration = StringArgumentType.getString(context, "duration");
                                                    MuteAssistConfig.getInstance().removeDuration(duration);
                                                    sendFeedback(context.getSource(), "Removed custom duration: " + duration);
                                                    return 1;
                                                })))
                                .then(literal("mapping")
                                        .then(argument("reason", StringArgumentType.greedyString())
                                                .suggests(MuteAssistCommand::suggestFastMuteReasons)
                                                .executes(context -> {
                                                    String reason = StringArgumentType.getString(context, "reason");
                                                    if (MuteAssistConfig.getInstance().getReasonDurationMap().containsKey(reason)) {
                                                        String oldDuration = MuteAssistConfig.getInstance().getDurationForReason(reason);
                                                        MuteAssistConfig.getInstance().removeReasonDurationMapping(reason);
                                                        sendFeedback(context.getSource(), "Removed fast mute mapping: " + reason + " (was " + oldDuration + ")");
                                                    } else {
                                                        sendFeedback(context.getSource(), "Reason not found in fast mute mappings!");
                                                    }
                                                    return 1;
                                                }))))
                        .then(literal("mapping")
                                .then(literal("add")
                                        .then(argument("reason", StringArgumentType.greedyString())
                                                .then(argument("duration", StringArgumentType.string())
                                                        .suggests(MuteAssistCommand::suggestConfigDurations)
                                                        .executes(context -> {
                                                            String reason = StringArgumentType.getString(context, "reason");
                                                            String duration = StringArgumentType.getString(context, "duration");
                                                            
                                                            String oldDuration = MuteAssistConfig.getInstance().getDurationForReason(reason);
                                                            MuteAssistConfig.getInstance().addReasonDurationMapping(reason, duration);
                                                            
                                                            if (oldDuration != null) {
                                                                sendFeedback(context.getSource(), "Updated fast mute mapping: " + reason + " (" + oldDuration + " → " + duration + ")");
                                                            } else {
                                                                sendFeedback(context.getSource(), "Added fast mute mapping: " + reason + " → " + duration);
                                                            }
                                                            return 1;
                                                        }))))
                                .then(literal("list")
                                        .executes(context -> {
                                            var mappings = MuteAssistConfig.getInstance().getReasonDurationMap();
                                            
                                            if (mappings.isEmpty()) {
                                                sendFeedback(context.getSource(), "No fast mute mappings configured.");
                                                return 1;
                                            }
                                            
                                            sendFeedback(context.getSource(), "=== Fast Mute Mappings ===");
                                            for (var entry : mappings.entrySet()) {
                                                sendFeedback(context.getSource(), entry.getKey() + " → " + entry.getValue());
                                            }
                                            sendFeedback(context.getSource(), "Total: " + mappings.size() + " mappings");
                                            return 1;
                                        }))
                                .executes(context -> {
                                    sendFeedback(context.getSource(), "Fast Mute Mapping Commands:");
                                    sendFeedback(context.getSource(), "/muteassist config mapping add <reason> <duration> - Add/update mapping");
                                    sendFeedback(context.getSource(), "/muteassist config remove mapping <reason> - Remove mapping");
                                    sendFeedback(context.getSource(), "/muteassist config mapping list - List all mappings");
                                    return 1;
                                }))
                        .then(literal("reload")
                                .executes(context -> {
                                    // Force reload configuration
                                    MuteAssistConfig.getInstance().save();
                                    sendFeedback(context.getSource(), "Configuration reloaded!");
                                    return 1;
                                }))
                        .executes(context -> {
                            sendFeedback(context.getSource(), "=== Mute Assist Configuration ===");
                            sendFeedback(context.getSource(), "Reasons: " + MuteAssistConfig.getInstance().getReasons().size() + " configured");
                            sendFeedback(context.getSource(), "Durations: " + MuteAssistConfig.getInstance().getDurations().size() + " configured");
                            sendFeedback(context.getSource(), "Fast Mute Mappings: " + MuteAssistConfig.getInstance().getReasonDurationMap().size() + " configured");
                            sendFeedback(context.getSource(), "");
                            sendFeedback(context.getSource(), "Available commands: add, remove, mapping, reload");
                            return 1;
                        }))
                .executes(context -> {
                    sendFeedback(context.getSource(), "Mute Assist Mod v0.1.3 - Use /muteassist config for configuration");
                    return 1;
                }));

        // Register /m as a smart shortcut that directly sends mute commands to server
        dispatcher.register(literal("m")
                .then(argument("player", StringArgumentType.word())
                        .suggests(MuteAssistCommand::suggestPlayers)
                        .then(argument("duration", StringArgumentType.word())
                                .suggests(MuteAssistCommand::suggestDurations)
                                .then(argument("reason", StringArgumentType.greedyString())
                                        .suggests(MuteAssistCommand::suggestReasons)
                                        .executes(context -> {
                                            String player = StringArgumentType.getString(context, "player");
                                            String duration = StringArgumentType.getString(context, "duration");
                                            String reason = StringArgumentType.getString(context, "reason");
                                            
                                            String fullCommand = String.format("mute %s %s %s", player, duration, reason);
                                            CommandUtil.sendCommand(fullCommand);
                                            CommandUtil.sendActionBar(String.format("§aMuted %s for %s: %s", player, duration, reason));
                                            return 1;
                                        }))
                                .executes(context -> {
                                    String player = StringArgumentType.getString(context, "player");
                                    String duration = StringArgumentType.getString(context, "duration");
                                    
                                    String partialCommand = String.format("/mute %s %s ", player, duration);
                                    CommandUtil.openChatWithCommand(partialCommand);
                                    return 1;
                                }))
                        .executes(context -> {
                            String player = StringArgumentType.getString(context, "player");
                            
                            String partialCommand = String.format("/mute %s ", player);
                            CommandUtil.openChatWithCommand(partialCommand);
                            return 1;
                        })));

        // Register /tm as shortcut for tempmute - directly sends to server
        dispatcher.register(literal("tm")
                .then(argument("player", StringArgumentType.word())
                        .suggests(MuteAssistCommand::suggestPlayers)
                        .then(argument("duration", StringArgumentType.word())
                                .suggests(MuteAssistCommand::suggestDurations)
                                .then(argument("reason", StringArgumentType.greedyString())
                                        .suggests(MuteAssistCommand::suggestReasons)
                                        .executes(context -> {
                                            String player = StringArgumentType.getString(context, "player");
                                            String duration = StringArgumentType.getString(context, "duration");
                                            String reason = StringArgumentType.getString(context, "reason");
                                            
                                            String fullCommand = String.format("tempmute %s %s %s", player, duration, reason);
                                            CommandUtil.sendCommand(fullCommand);
                                            CommandUtil.sendActionBar(String.format("§aTemp muted %s for %s: %s", player, duration, reason));
                                            return 1;
                                        }))
                                .executes(context -> {
                                    String player = StringArgumentType.getString(context, "player");
                                    String duration = StringArgumentType.getString(context, "duration");
                                    
                                    String partialCommand = String.format("/tempmute %s %s ", player, duration);
                                    CommandUtil.openChatWithCommand(partialCommand);
                                    return 1;
                                }))
                        .executes(context -> {
                            String player = StringArgumentType.getString(context, "player");
                            
                            String partialCommand = String.format("/tempmute %s ", player);
                            CommandUtil.openChatWithCommand(partialCommand);
                            return 1;
                        })));

        // Register /um as shortcut for unmute - directly sends to server
        dispatcher.register(literal("um")
                .then(argument("player", StringArgumentType.word())
                        .suggests(MuteAssistCommand::suggestPlayers)
                        .executes(context -> {
                            String player = StringArgumentType.getString(context, "player");
                            String command = String.format("unmute %s", player);
                            CommandUtil.sendCommand(command);
                            CommandUtil.sendActionBar(String.format("§aUnmuted %s", player));
                            return 1;
                        })));

        // Register /mf (mute fast) - automatically determines duration and sends directly to server
        dispatcher.register(literal("mf")
                .then(argument("player", StringArgumentType.word())
                        .suggests(MuteAssistCommand::suggestPlayers)
                        .then(argument("reason", StringArgumentType.greedyString())
                                .suggests(MuteAssistCommand::suggestFastMuteReasons)
                                .executes(context -> {
                                    String player = StringArgumentType.getString(context, "player");
                                    String reason = StringArgumentType.getString(context, "reason");
                                    
                                    // Get duration from mapping
                                    String duration = MuteAssistConfig.getInstance().getDurationForReason(reason);
                                    
                                    if (duration != null) {
                                        String command = String.format("mute %s %s %s", player, duration, reason);
                                        CommandUtil.sendCommand(command);
                                        CommandUtil.sendActionBar(String.format("§aFast muted %s for %s: %s", player, duration, reason));
                                    } else {
                                        // If reason not found in mapping, open chat for manual entry
                                        String partialCommand = String.format("/mute %s  %s", player, reason);
                                        CommandUtil.openChatWithCommand(partialCommand);
                                        CommandUtil.sendActionBar("§eReason not in fast mute table, please add duration manually");
                                    }
                                    return 1;
                                }))
                        .executes(context -> {
                            // Show available fast mute reasons
                            String player = StringArgumentType.getString(context, "player");
                            sendFeedback(context.getSource(), "§eFast Mute: Use /mf " + player + " <reason>. Available reasons:");
                            for (String reason : MuteAssistConfig.getInstance().getFastMuteReasons()) {
                                String duration = MuteAssistConfig.getInstance().getDurationForReason(reason);
                                sendFeedback(context.getSource(), "§7- " + reason + " §8(§6" + duration + "§8)");
                            }
                            return 1;
                        }))
                .executes(context -> {
                    sendFeedback(context.getSource(), "§eFast Mute: /mf <player> <reason> - Auto-determines duration from reason");
                    return 1;
                }));
    }

    // --- Suggestion Methods ---

    private static CompletableFuture<Suggestions> suggestPlayers(
            CommandContext<FabricClientCommandSource> context,
            SuggestionsBuilder builder) {
        
        updatePlayerList(MinecraftClient.getInstance());
        String remaining = builder.getRemaining().toLowerCase();
        
        // Filter and suggest players
        int suggestionsAdded = 0;
        for (String name : onlinePlayers) {
            if (name.toLowerCase().startsWith(remaining)) {
                builder.suggest(name);
                suggestionsAdded++;
            }
        }
        
        // Add helpful tooltip if no matches
        if (suggestionsAdded == 0 && !remaining.isEmpty()) {
            builder.suggest(remaining, Text.literal("§cNo online players match '" + remaining + "'"));
        }
        
        logSuggestions("Player", suggestionsAdded, remaining);
        return builder.buildFuture();
    }

    private static CompletableFuture<Suggestions> suggestDurations(
            CommandContext<FabricClientCommandSource> context,
            SuggestionsBuilder builder) {
        
        String remaining = builder.getRemaining().toLowerCase();
        int suggestionsAdded = 0;
        
        // Get durations from config and suggest them with helpful descriptions
        for (String duration : MuteAssistConfig.getInstance().getDurations()) {
            if (duration.toLowerCase().startsWith(remaining)) {
                Text tooltip = getDurationTooltip(duration);
                if (tooltip != null) {
                    builder.suggest(duration, tooltip);
                } else {
                    builder.suggest(duration);
                }
                suggestionsAdded++;
            }
        }
        
        logSuggestions("Duration", suggestionsAdded, remaining);
        return builder.buildFuture();
    }

    private static CompletableFuture<Suggestions> suggestReasons(
            CommandContext<FabricClientCommandSource> context,
            SuggestionsBuilder builder) {
        
        String remaining = builder.getRemaining().toLowerCase();
        boolean enablePartialMatching = MuteAssistConfig.getInstance().enablePartialReasonMatching;
        int suggestionsAdded = 0;
        
        // Get reasons from config and suggest them
        for (String reason : MuteAssistConfig.getInstance().getReasons()) {
            if (reason.toLowerCase().startsWith(remaining) || 
                (enablePartialMatching && remaining.length() > 2 && reason.toLowerCase().contains(remaining))) {
                builder.suggest(reason, Text.literal("§7" + reason));
                suggestionsAdded++;
                if (suggestionsAdded >= 10) break; // Limit to prevent overwhelming UI
            }
        }
        
        // If no matches and partial matching is enabled, suggest creating a custom reason
        if (suggestionsAdded == 0 && remaining.length() > 3) {
            builder.suggest(remaining, Text.literal("§eCustom reason: " + remaining));
        }
        
        logSuggestions("Reason", suggestionsAdded, remaining);
        return builder.buildFuture();
    }

    private static CompletableFuture<Suggestions> suggestConfigReasons(
            CommandContext<FabricClientCommandSource> context,
            SuggestionsBuilder builder) {
        
        String remaining = builder.getRemaining().toLowerCase();
        
        MuteAssistConfig.getInstance().getReasons().stream()
                .filter(reason -> reason.toLowerCase().startsWith(remaining))
                .forEach(builder::suggest);
        
        return builder.buildFuture();
    }

    private static CompletableFuture<Suggestions> suggestConfigDurations(
            CommandContext<FabricClientCommandSource> context,
            SuggestionsBuilder builder) {
        
        String remaining = builder.getRemaining().toLowerCase();
        
        MuteAssistConfig.getInstance().getDurations().stream()
                .filter(duration -> duration.toLowerCase().startsWith(remaining))
                .forEach(builder::suggest);
        
        return builder.buildFuture();
    }

    private static CompletableFuture<Suggestions> suggestFastMuteReasons(
            CommandContext<FabricClientCommandSource> context,
            SuggestionsBuilder builder) {
        
        String remaining = builder.getRemaining().toLowerCase();
        int suggestionsAdded = 0;
        
        // Get fast mute reasons from config and suggest them with duration tooltips
        for (String reason : MuteAssistConfig.getInstance().getFastMuteReasons()) {
            if (reason.toLowerCase().startsWith(remaining)) {
                String duration = MuteAssistConfig.getInstance().getDurationForReason(reason);
                Text tooltip = Text.literal("§6" + duration + " §7- " + reason);
                builder.suggest(reason, tooltip);
                suggestionsAdded++;
            }
        }
        
        // If no matches, show all available reasons
        if (suggestionsAdded == 0 && !remaining.isEmpty()) {
            builder.suggest(remaining, Text.literal("§cNo fast mute reason matches '" + remaining + "'"));
        }
        
        logSuggestions("Fast Mute Reason", suggestionsAdded, remaining);
        return builder.buildFuture();
    }

    // --- Helper Methods ---

    private static Text getDurationTooltip(String duration) {
        switch (duration.toLowerCase()) {
            case "10m": return Text.literal("§710 minutes");
            case "30m": return Text.literal("§730 minutes");
            case "1h": return Text.literal("§71 hour");
            case "3h": return Text.literal("§73 hours");
            case "6h": return Text.literal("§76 hours");
            case "12h": return Text.literal("§712 hours");
            case "1d": return Text.literal("§71 day");
            case "3d": return Text.literal("§73 days");
            case "7d": return Text.literal("§71 week");
            case "30d": return Text.literal("§730 days");
            case "perm": return Text.literal("§cPermanent mute");
            default: return null;
        }
    }

    // --- Utility Methods ---

    public static void updatePlayerList(MinecraftClient client) {
        try {
            if (client != null && client.player != null && client.getNetworkHandler() != null) {
                onlinePlayers = client.getNetworkHandler().getPlayerList()
                        .stream()
                        .map(entry -> entry.getProfile().getName())
                        .filter(name -> name != null && !name.isEmpty())
                        .distinct()
                        .collect(Collectors.toList());
                
                if (MuteAssistConfig.getInstance().enableDebugLogging) {
                    Muteassist.LOGGER.debug("Updated player list with {} players", onlinePlayers.size());
                }
            } else {
                onlinePlayers.clear();
                if (MuteAssistConfig.getInstance().enableDebugLogging) {
                    Muteassist.LOGGER.debug("Cleared player list - client/player/network handler not available");
                }
            }
        } catch (Exception e) {
            Muteassist.LOGGER.warn("Error updating player list: {}", e.getMessage());
            onlinePlayers.clear();
        }
    }

    public static void clearPlayerList() {
        onlinePlayers.clear();
        if (MuteAssistConfig.getInstance().enableDebugLogging) {
            Muteassist.LOGGER.debug("Cleared player list due to disconnect");
        }
    }

    private static void logSuggestions(String type, int count, String input) {
        if (MuteAssistConfig.getInstance().enableDebugLogging && Muteassist.LOGGER.isDebugEnabled()) {
            Muteassist.LOGGER.debug("Providing {} {} suggestions for input '{}'", 
                    count, type, input);
        }
    }

    private static void sendFeedback(FabricClientCommandSource source, String message) {
        CommandUtil.sendMessage(CommandUtil.formatMessage(message));
    }

    // Public getter for player list (useful for debugging or other features)
    public static List<String> getOnlinePlayers() {
        return new ArrayList<>(onlinePlayers);
    }
}
