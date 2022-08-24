package cz.ethernal.SuggestSystem;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class SuggestCmd extends ListenerAdapter {

    int pro = 0;
    int proti = 0;

    ArrayList<String> hlasoval = new ArrayList<String>();


    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {

        if(e.getName().equalsIgnoreCase("suggest") || e.getName().equalsIgnoreCase("nápad")){

            OptionMapping message = e.getOption("zpráva");
            String zprava = message.getAsString();

            EmbedBuilder deleted = new EmbedBuilder();

            deleted.setTitle("Úspěšně jsi vytvořil/a návrh v kanále 💡│nápady");
            deleted.setColor(Color.GREEN);

            e.replyEmbeds(deleted.build()).setEphemeral(true).queue();

            //--------------------------------------------------------------------------------------------------------

            EmbedBuilder suggest = new EmbedBuilder();

            Button prob = Button.secondary("pro-vote", "\uD83D\uDC4D");
            Button protib = Button.secondary("proti-vote", "\uD83D\uDC4E");

            suggest.setAuthor("Návrh hráče " + e.getInteraction().getMember().getEffectiveName(), e.getInteraction().getMember().getAvatarUrl(), e.getInteraction().getMember().getEffectiveAvatarUrl());
            suggest.setColor(Color.YELLOW);
            suggest.setDescription("**Návrh**\n" + zprava + "**\n\nHlasování**\n" + "Pro » " + pro + "\n" + "Proti » " + proti);

            TextChannel channel = e.getGuild().getTextChannelById("987736131749679134");

            channel.sendMessageEmbeds(suggest.build())
                    .setActionRows(ActionRow.of(prob, protib))
                    .queue();
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent e) {

        EmbedBuilder suggest = new EmbedBuilder();

        suggest.setAuthor("Návrh hráče " + e.getInteraction().getMember().getEffectiveName(), e.getInteraction().getMember().getAvatarUrl(), e.getInteraction().getMember().getEffectiveAvatarUrl());
        suggest.setColor(Color.YELLOW);
        suggest.setDescription("**Návrh**\n" + "zprava" + "**\n\nHlasování**\n" + "Pro » " + pro + "\n" + "Proti » " + proti);

        if (e.getButton().getId().equals("pro-vote")) {

            if(!hlasoval.contains(e.getInteraction().getMember().getId())){

                hlasoval.add(e.getInteraction().getMember().getId());
                pro = pro+1;
                e.getMessage().editMessageEmbeds(suggest.build()).queue();
                return;

            }else{

                EmbedBuilder uzhlasoval = new EmbedBuilder();
                uzhlasoval.setColor(Color.RED);
                uzhlasoval.setTitle("Hlasovat můžeš pouze jednou!");

                e.replyEmbeds(uzhlasoval.build()).setEphemeral(true).queue();
            }
        }

        if (e.getButton().getId().equals("proti-vote")) {

            if(!hlasoval.contains(e.getInteraction().getMember().getId())){

                hlasoval.add(e.getInteraction().getMember().getId());
                proti = proti+1;
                e.getMessage().editMessageEmbeds(suggest.build()).queue();
                return;

            }else{

                EmbedBuilder uzhlasoval = new EmbedBuilder();
                uzhlasoval.setColor(Color.RED);
                uzhlasoval.setTitle("Hlasovat můžeš pouze jednou!");

                e.replyEmbeds(uzhlasoval.build()).setEphemeral(true).queue();
            }
        }

    }
}
