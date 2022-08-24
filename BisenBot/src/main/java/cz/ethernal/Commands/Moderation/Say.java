package cz.ethernal.Commands.Moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Say extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {

        if(e.getName().equalsIgnoreCase("say")){

            if(!e.getInteraction().getMember().hasPermission(Permission.MESSAGE_MANAGE)){
                EmbedBuilder nopemrs = new EmbedBuilder();
                nopemrs.setColor(Color.RED);
                nopemrs.setTitle("Na tento příkaz nemáš dostatečná oprávnění!");

                e.replyEmbeds(nopemrs.build()).setEphemeral(true).queue();
                return;
            }

            e.reply("Done!").queue(m -> m.deleteOriginal().queueAfter(1, TimeUnit.MICROSECONDS));

            OptionMapping message = e.getOption("zpráva");
            String zprava = message.getAsString();

            e.getChannel().sendMessage(zprava).queue();

        }

    }
}
