package cz.ethernal.Commands.Fun;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class CoinFlip extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {

        if(event.getName().equalsIgnoreCase("coinflip")){

            java.util.Random random = new java.util.Random();
            int number = random.nextInt(2) + 1;

            System.out.println(number);

            if(number == 1){

                EmbedBuilder pana = new EmbedBuilder();
                pana.setTitle("Panna!");
                pana.setColor(Color.CYAN);

                event.replyEmbeds(pana.build()).setEphemeral(false).queue(m -> m.deleteOriginal().queueAfter(10, TimeUnit.SECONDS));

            }else if(number == 2){

                EmbedBuilder orel = new EmbedBuilder();
                orel.setTitle("Orel!");
                orel.setColor(Color.CYAN);

                event.replyEmbeds(orel.build()).setEphemeral(false).queue(m -> m.deleteOriginal().queueAfter(10, TimeUnit.SECONDS));
            }

        }

    }
}
