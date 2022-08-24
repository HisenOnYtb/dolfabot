package cz.ethernal.VCSystem.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Help extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {

        if(e.getName().equalsIgnoreCase("voice")){

            EmbedBuilder help = new EmbedBuilder();

            help.setColor(Color.YELLOW);
            help.setTitle("Příkazy pro ovládání hlasových kanálů");
            help.setDescription("Všechny příkazy níže lze využít pouze na tvůj vlastní kanál. Pokud budeš napojen/á v hlasovém kanále někoho jiného, příkazy ti fungovat nebudou!");
            help.addField("Veřejné Příkazy", "`/voice-lock` - Nastavíš svůj kanál jako soukromý" +
                    "\n`/voice-unlock` - Uvolníš svůj kanál pro veřejnost" +
                    "\n`/voice-rename` - Přejmenuješ svůj kanál" +
                    "\n`/voice-add` - Povolíš hráčovi vstup do kanálu (pokud je kanál soukromý)" +
                    "\n`/voice-remove` - Odebereš hráčovi přístup do kanálu (pokud je kanál soukromý)", false);
            help.addField("Premium Příkazy", "`/voice-limit` - Nastavíš maximální limit hráčů v kanále" +
                    "\n`/voice-hide` - Skryješ svůj kanál před ostatními hráči" +
                    "\n`/voice-show` - Zobrazíš svůj kanál všem ostatním hráčům", false);
            help.setFooter("*Premium příkazy slouží pro všechny druhy VIP a Boostery Discord serveru!");

            e.replyEmbeds(help.build()).setEphemeral(true).queue();





        }

    }
}
