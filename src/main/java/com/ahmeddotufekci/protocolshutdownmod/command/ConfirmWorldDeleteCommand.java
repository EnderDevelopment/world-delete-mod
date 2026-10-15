package com.ahmeddotufekci.protocolshutdownmod.command;

import com.ahmeddotufekci.protocolshutdownmod.ProtocolShutdownMod;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import static net.minecraft.server.command.CommandManager.literal;

public class ConfirmWorldDeleteCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("confirm_world_delete")
                .executes(context -> {
                    ProtocolShutdownMod.confirmWorldDelete();
                    return 1;
                }));
    }
}