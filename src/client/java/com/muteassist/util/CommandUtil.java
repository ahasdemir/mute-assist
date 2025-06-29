package com.muteassist.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.text.Text;

public class CommandUtil {
    
    /**
     * Opens chat screen with pre-filled command text
     */
    public static void openChatWithCommand(String command) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            client.setScreen(new ChatScreen(command));
        }
    }
    
    /**
     * Sends a command directly to the server
     */
    public static void sendCommand(String command) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            // Remove leading slash if present
            String cleanCommand = command.startsWith("/") ? command.substring(1) : command;
            client.player.networkHandler.sendChatCommand(cleanCommand);
        }
    }
    
    /**
     * Sends a message to the player's chat
     */
    public static void sendMessage(String message) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.sendMessage(Text.literal(message), false);
        }
    }
    
    /**
     * Sends a message to the player's action bar
     */
    public static void sendActionBar(String message) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.sendMessage(Text.literal(message), true);
        }
    }
    
    /**
     * Formats a mute assist message with consistent styling
     */
    public static String formatMessage(String message) {
        return "§7[§bMute Assist§7] §f" + message;
    }
}
