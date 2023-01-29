package com.starpony.paimon.listeners;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class MessageListener {
    public Mono<Void> handle(MessageCreateEvent event) {
        Message message = event.getMessage();
        Optional<User> author = message.getAuthor();

        if (author.isEmpty() || author.get().isBot()) return Mono.empty();

        return message.getChannel().flatMap(channel -> channel.createMessage("Я не консерва")).then();
    }
}
