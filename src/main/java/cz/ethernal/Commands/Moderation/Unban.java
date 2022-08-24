package cz.ethernal.Commands.Moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Unban extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {

        if(true)
            return;

        if(!e.getInteraction().getMember().hasPermission(Permission.BAN_MEMBERS)){
            EmbedBuilder nopemrs = new EmbedBuilder();
            nopemrs.setColor(Color.RED);
            nopemrs.setTitle("Na tento příkaz nemáš dostatečná oprávnění!");

            e.replyEmbeds(nopemrs.build()).setEphemeral(true).queue();

            return;
        }

        e.deferReply(true).queue();

    }
}
