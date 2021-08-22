package com.github.klainstom.resourcepacktest;

import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.extensions.Extension;
import net.minestom.server.resourcepack.ResourcePack;
import net.minestom.server.utils.NamespaceID;

public class ExtensionMain extends Extension {
    private final String resourcePackUrl = "http://localhost:8000/resource-pack.zip";
    private final String resourcePackHash = "ea7146b08a857ed116dee949a411ed30c60c7b53";

    private final ResourcePack resourcePack = ResourcePack.forced(
            resourcePackUrl, resourcePackHash, Component.text("Take it or leave it!"));

    @Override
    public void initialize() {
        MinecraftServer.LOGGER.info("$name$ initialize.");
        MinecraftServer.LOGGER.info(String.format("%s -> %s", resourcePackUrl, resourcePackHash));

        getEventNode().addListener(PlayerSpawnEvent.class, event -> {
            if (event.isFirstSpawn()) event.getPlayer().setResourcePack(resourcePack);
        });

        getEventNode().addListener(PlayerChatEvent.class, playerChatEvent -> {
            Player player = playerChatEvent.getPlayer();

            player.sendMessage(Component.translatable("demo.translate"));
            player.playSound(Sound.sound(
                    NamespaceID.from("minestom", "demo.sound"),
                    Sound.Source.MASTER, 1.0f, 1.0f));
        });
    }

    @Override
    public void terminate() {
        MinecraftServer.LOGGER.info("$name$ terminate.");

    }
}
