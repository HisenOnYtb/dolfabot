package cz.ethernal.Commands.Others;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class DirectMsg extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {

        if(e.getName().equalsIgnoreCase("pm") || e.getName().equalsIgnoreCase("dm")){

            if(!e.getInteraction().getMember().hasPermission(Permission.KICK_MEMBERS)){
                EmbedBuilder nopemrs = new EmbedBuilder();
                nopemrs.setColor(Color.RED);
                nopemrs.setTitle("Na tento příkaz nemáš dostatečná oprávnění!");

                e.replyEmbeds(nopemrs.build()).setEphemeral(true).queue();

                return;
            }

            e.deferReply(true).queue();

            OptionMapping member = e.getOption("hráč");
            OptionMapping message = e.getOption("zpráva");

            Member hrac = member.getAsMember();
            String zprava = message.getAsString();


            hrac.getUser().openPrivateChannel().queue(channel -> channel.sendMessage(zprava).queue());

            EmbedBuilder sent = new EmbedBuilder();

            sent.setColor(Color.GREEN);
            sent.setTitle("Úspěšně jsi poslal/a soukromou zprávu hráči " + hrac.getEffectiveName());

            e.getHook().sendMessageEmbeds(sent.build()).queue();

        }


    }
}
