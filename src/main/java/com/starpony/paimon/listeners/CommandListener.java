package com.starpony.paimon.listeners;

import com.starpony.paimon.commands.SlashCommand;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


public class CommandListener {
    private final List<SlashCommand> commands;

    public CommandListener(List<SlashCommand> commands) {
        this.commands = commands;
    }

    public Mono<Void> handle(ChatInputInteractionEvent event) {
        return Flux.fromIterable(commands)
                .filter(command -> command.getName().equals(event.getCommandName()))
                .next().flatMap(command -> command.handle(event));
    }
}
