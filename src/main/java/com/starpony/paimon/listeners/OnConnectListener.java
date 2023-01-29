package com.starpony.paimon.listeners;

import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;

public class OnConnectListener {
    public Mono<Void> handle(ReadyEvent readyEvent) {
        User self = readyEvent.getSelf();
        System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
        return Mono.empty();
    }
}
