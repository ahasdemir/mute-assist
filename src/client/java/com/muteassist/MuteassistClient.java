package com.muteassist;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import com.muteassist.config.MuteAssistConfig;
import com.muteassist.util.CommandUtil;

public class MuteassistClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Muteassist.LOGGER.info("Mute Assist Mod client initialized!");

		// Initialize configuration on client startup
		MuteAssistConfig.getInstance();

		// Register the client-side command callback
		ClientCommandRegistrationCallback.EVENT.register(MuteAssistCommand::register);

		// Listen for player join/leave to update player list
		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
			MuteAssistCommand.updatePlayerList(client);
		});

		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
			MuteAssistCommand.clearPlayerList();
		});
	}
}