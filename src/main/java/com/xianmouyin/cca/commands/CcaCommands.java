package com.xianmouyin.cca.commands;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.xianmouyin.cca.Utils;
import com.xianmouyin.cca.craft_limiter.CraftTimeSave;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CcaCommands {

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();

        dispatcher.register(
                Commands.literal(Utils.MOD_ID)
                        .requires(src -> src.hasPermission(2))
                        .then(Commands.literal("get_crafted_times")
                                .then(getSlot("abilities"))
                                .then(getSlot("upgrades"))
                                .then(getSlot("defense"))
                                .then(getSlot("soul"))
                        )
                        .then(Commands.literal("add_crafted_times")
                                .then(addSlot("abilities"))
                                .then(addSlot("upgrades"))
                                // Legacy-Typo aus dem alten Mod: "uprades"
                                .then(addSlotAlias("uprades", "upgrades"))
                                .then(addSlot("defense"))
                                .then(addSlot("soul"))
                        )
        );
    }

    private static com.mojang.brigadier.builder.ArgumentBuilder<net.minecraft.commands.CommandSourceStack, ?> getSlot(String slot) {
        return Commands.literal(slot).executes(ctx -> {
            var storage = CraftTimeSave.get(ctx.getSource().getLevel());
            ctx.getSource().sendSuccess(() ->
                            Component.literal("§c§lCrafted times of creative " + slot + " slot: " + storage.getTimes(slot)),
                    false
            );
            return 1;
        });
    }

    private static com.mojang.brigadier.builder.ArgumentBuilder<net.minecraft.commands.CommandSourceStack, ?> addSlot(String slot) {
        return Commands.literal(slot)
                .then(Commands.argument("times", FloatArgumentType.floatArg())
                        .executes(ctx -> {
                            var storage = CraftTimeSave.get(ctx.getSource().getLevel());
                            int times = (int) FloatArgumentType.getFloat(ctx, "times");
                            storage.addTimes(slot, times);
                            ctx.getSource().sendSuccess(() ->
                                            Component.literal("§c§lSuccesfully add " + times + " crafted times to creative " + slot + " slot!"),
                                    false
                            );
                            return 1;
                        }));
    }

    private static com.mojang.brigadier.builder.ArgumentBuilder<net.minecraft.commands.CommandSourceStack, ?> addSlotAlias(String alias, String realSlot) {
        return Commands.literal(alias)
                .then(Commands.argument("times", FloatArgumentType.floatArg())
                        .executes(ctx -> {
                            var storage = CraftTimeSave.get(ctx.getSource().getLevel());
                            int times = (int) FloatArgumentType.getFloat(ctx, "times");
                            storage.addTimes(realSlot, times);
                            ctx.getSource().sendSuccess(() ->
                                            Component.literal("§c§lSuccesfully add " + times + " crafted times to creative " + realSlot + " slot!"),
                                    false
                            );
                            return 1;
                        }));
    }
}
