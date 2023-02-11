package neeleshpoli.teamchat;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import static net.minecraft.server.command.CommandManager.*;

public class TeamChat implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("teamchat");
	private HashMap<String, Boolean> allChatUsage = new HashMap<>();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Starting Teamchat");

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("chat")
			.then(literal("team").executes(context -> {
				if (!(context.getSource() instanceof ServerCommandSource)) {
					context.getSource().sendError(Text.translatable("teamchat.nonClient"));
					return 0;
				}
				allChatUsage.put(context.getSource().getEntity().getUuidAsString(), false);
				context.getSource().sendMessage(Text.translatable("teamchat.chatChangedToTeam"));
				return 1;
			})
			.then(literal("all")).executes(context -> {
				if (!(context.getSource() instanceof ServerCommandSource)) {
					context.getSource().sendError(Text.translatable("teamchat.nonClient"));
					return 0;
				}
				allChatUsage.put(context.getSource().getEntity().getUuidAsString(), true);
				context.getSource().sendMessage(Text.translatable("teamchat.chatChangedToAll"));
				return 1;
			})
		)));
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->dispatcher.register(literal("shout")
		
		));
	}
}