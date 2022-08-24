package cz.ethernal.Commands.Moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

public class Clear extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {


        if(e.getName().equalsIgnoreCase("clear")) {

            if(!e.getInteraction().getMember().hasPermission(Permission.MANAGE_CHANNEL)){
                EmbedBuilder nopemrs = new EmbedBuilder();
                nopemrs.setColor(Color.RED);
                nopemrs.setTitle("Na tento příkaz nemáš dostatečná oprávnění!");

                e.replyEmbeds(nopemrs.build()).setEphemeral(true).queue();
                return;
            }

            e.deferReply(true).queue();

            OptionMapping ammount = e.getOption("počet");

            if (ammount == null || ammount.equals(null)) {
                return;
            }

            Integer pocet = ammount.getAsInt();



            if (pocet <= 2) {

                EmbedBuilder malo = new EmbedBuilder();
                malo.setColor(Color.RED);
                malo.setTitle("Lze smazat pouze 2 a více zpráv!");

                e.getHook().sendMessageEmbeds(malo.build()).queue();

                return;
            }

            if (pocet >= 100) {

                EmbedBuilder malo = new EmbedBuilder();
                malo.setColor(Color.RED);
                malo.setTitle("Lze smazat maximálně 99 zpráv!");

                e.getHook().sendMessageEmbeds(malo.build()).queue();

                return;
            }


            if (e.getName().equalsIgnoreCase("clear")) {

                EmbedBuilder deleted = new EmbedBuilder();

                deleted.setTitle("Úspěšně jsi smazal/a " + pocet + " zpráv v tomto kanále!");
                deleted.setColor(Color.GREEN);

                List<Message> messages = e.getChannel().getHistory().retrievePast(pocet).complete();
                e.getTextChannel().deleteMessages(messages).queue();

                e.getHook().sendMessageEmbeds(deleted.build()).queue();


            }

        }


    }
}
