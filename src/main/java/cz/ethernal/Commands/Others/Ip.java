package cz.ethernal.Commands.Others;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Ip extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {

        if(e.getName().equalsIgnoreCase("ip")){

            EmbedBuilder ip = new EmbedBuilder();

            ip.setTitle("IP » play.ethernal.cz");
            ip.setColor(Color.YELLOW);

            e.replyEmbeds(ip.build()).queue();

        }


    }
}
