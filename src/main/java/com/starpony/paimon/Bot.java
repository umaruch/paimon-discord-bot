package com.starpony.paimon;

import com.starpony.paimon.commands.InfoCommand;
import com.starpony.paimon.commands.SlashCommand;
import com.starpony.paimon.listeners.CommandListener;
import com.starpony.paimon.listeners.MessageListener;
import com.starpony.paimon.listeners.OnConnectListener;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.discordjson.json.ApplicationCommandRequest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Bot {
    private final static List<SlashCommand> commands = new LinkedList<>();

    static {
        // Заполнение списка комманд
        commands.add(new InfoCommand());
    }

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        GatewayDiscordClient client = DiscordClientBuilder.create(configuration.getDiscordToken()).build().login().block();

        //  Регистрация комманд приложения для отображения в клиенте Discord
        System.out.println("Регистрация команд на сервере");
        List<ApplicationCommandRequest> commandRequests = new ArrayList<>();
        for (SlashCommand command: commands) {
            commandRequests.add(command.toCommandRequest());
        }
        client.getRestClient().getApplicationService().bulkOverwriteGlobalApplicationCommand(
                client.getRestClient().getApplicationId().block(), commandRequests
        ).doOnNext(cmd -> System.out.println(cmd.name() + " successfully registered"))
                .doOnError(e -> System.out.println("Failed to register global commands " + e))
                .subscribe();

        System.out.println("Регистрация обработчиков");
        //  Регистрация обработчиков событий
        client.on(ReadyEvent.class, new OnConnectListener()::handle).then()
                .and(client.on(MessageCreateEvent.class, new MessageListener()::handle).then())
                .and(client.on(ChatInputInteractionEvent.class, new CommandListener(commands)::handle).then())
                .block();
    }
}
