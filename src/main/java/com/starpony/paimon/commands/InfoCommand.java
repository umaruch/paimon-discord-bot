package com.starpony.paimon.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.discordjson.json.ApplicationCommandRequest;
import discord4j.rest.util.Color;
import reactor.core.publisher.Mono;


public class InfoCommand implements SlashCommand{
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        EmbedCreateSpec embed = EmbedCreateSpec.builder()
                .color(Color.WHITE).title("Паймон").description("Я бесполезная консервная банка")
                .addField("Исходный код", "https://github.com/umaruch/paimon-discord-bot", false)
                .build();
        return event.reply().withEphemeral(true).withEmbeds(embed);
    }

    @Override
    public ApplicationCommandRequest toCommandRequest() {
        return ApplicationCommandRequest.builder()
                .name(getName()).description("Отправляет информацию о боте").build();
    }
}
