package com.ahmeddotufekci.protocolshutdownmod;

import net.fabricmc.api.ModInitializer;
import com.ahmeddotufekci.protocolshutdownmod.command.ConfirmWorldDeleteCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.literal;

public class ProtocolShutdownMod implements ModInitializer {
    public static final String MOD_ID = "protocolshutdownmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static MinecraftServer server;

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing ProtocolShutdownMod");
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("world_delete")
                    .requires(source -> source.hasPermissionLevel(4))
                    .executes(context -> {
                        server = context.getSource().getServer();
                        broadcastConfirmationMessage();
                        return 1;
                    }));
            ConfirmWorldDeleteCommand.register(dispatcher);
        });
    }

    private void broadcastConfirmationMessage() {
        Text message = Text.literal("Are you sure you want to delete the world?")
                .formatted(Formatting.RED)
                .append(Text.literal(" [Confirm]").formatted(Formatting.GREEN)
                        .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/confirm_world_delete"))));
        server.getPlayerManager().broadcast(message, false);
    }

    public static void confirmWorldDelete() {
        if (server != null) {
            server.stop(false);
            LOGGER.info("Server shutdown initiated");
        }
    }
}