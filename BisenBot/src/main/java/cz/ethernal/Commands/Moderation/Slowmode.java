package cz.ethernal.Commands.Moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Channel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Slowmode extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {

        if(!e.getName().equalsIgnoreCase("slowmode") && !e.getName().equalsIgnoreCase("cooldown"))
            return;


        if(!e.getInteraction().getMember().hasPermission(Permission.MANAGE_CHANNEL)){

            EmbedBuilder nopemrs = new EmbedBuilder();
            nopemrs.setColor(Color.RED);
            nopemrs.setTitle("Na tento příkaz nemáš dostatečná oprávnění!");

            e.replyEmbeds(nopemrs.build()).setEphemeral(true).queue();

            return;
        }

        e.deferReply(true).queue();

        OptionMapping time = e.getOption("sekundy");
        OptionMapping canal = e.getOption("kanál");

        if(time == null || time.equals(null)){
            return;
        }

        Integer sekundy = time.getAsInt();
        Channel kanál = canal.getAsMessageChannel();



        if(!canal.equals(null) || canal != null || kanál != null || !kanál.equals(null)){


            TextChannel channel = e.getGuild().getTextChannelById(kanál.getId());
            channel.getManager()
                    .setSlowmode(sekundy)
                    .queue();


            EmbedBuilder kanal = new EmbedBuilder();

            kanal.setTitle("Akce úspěšná!");
            kanal.setDescription("Úspěšně jsi nastavil/a slowmode v kanále " + kanál.getAsMention() + " na " + sekundy + " sekund!");
            kanal.setColor(Color.GREEN);


            e.getHook().sendMessageEmbeds(kanal.build()).queue();
            return;


        }else {

            e.deferReply(true).queue();

            TextChannel channel = e.getGuild().getTextChannelById(e.getInteraction().getChannel().getId());
            channel.getManager()
                    .setSlowmode(sekundy)
                    .queue();

            EmbedBuilder nekanal = new EmbedBuilder();

            nekanal.setTitle("Akce úspěšná!");
            nekanal.setDescription("Úspěšně jsi nastavil/a slowmode v tomto kanále na " + sekundy + " sekund!");
            nekanal.setColor(Color.GREEN);


            e.getHook().sendMessageEmbeds(nekanal.build()).queue();
            return;

        }




    }
}
