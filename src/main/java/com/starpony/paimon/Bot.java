package com.starpony.paimon;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;

public class Bot {
    public static void main(String[] args) {
        DiscordClient client = DiscordClient.create("MTA2ODU5MTEzMDEzMzkzODI2Nw.GnP3sJ.0v4qBhpzdFo9OsRCJ6BPXCYrLgOcX2psHtnGho");

        Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) -> {
            //  Пишет информацию о боте при успешном подключении
            Mono<Void> printInfoOnLogin = gateway.on(ReadyEvent.class, readyEvent ->
                    Mono.fromRunnable(() -> {
                        final User self = readyEvent.getSelf();
                        System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
                    })).then();
            //  Обработка сообщений пользователя
            Mono<Void> handlePingCommand = gateway.on(MessageCreateEvent.class, messageCreateEvent -> {
                    Message message = messageCreateEvent.getMessage();

                    if (message.getContent().equalsIgnoreCase("/ping")) {
                        return  message.getChannel().flatMap(channel -> channel.createMessage("pong!"));
                    }

                    return Mono.empty();
                    }).then();

            return printInfoOnLogin.and(handlePingCommand);
        });

        login.block();
    }
}
