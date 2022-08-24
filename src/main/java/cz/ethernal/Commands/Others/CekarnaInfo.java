package cz.ethernal.Commands.Others;

import cz.ethernal.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class CekarnaInfo extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {

        if(e.getName().equalsIgnoreCase("tinfo")){

            if(!e.getInteraction().getMember().getId().equals(Config.id_majitele) && !e.getInteraction().getMember().getId().equals("415136736717438976")){
                e.reply("Nedostatečná práva!").queue(m -> m.deleteOriginal().queueAfter(1, TimeUnit.SECONDS));
                return;
            }

            EmbedBuilder tinfo = new EmbedBuilder();
            tinfo.setColor(Color.YELLOW);
            tinfo.setTitle("**Ethernal.cz | Čekárna**");
            tinfo.setDescription("Místnost čekárna slouží k řešení veškerých problémů týkající se serveru. Náš Team ti s radostí pomůže tvůj problém vyřešit, za podmínky, že se budeš v hovoru vyjadřovat slušně.");

            EmbedBuilder tinfo2 = new EmbedBuilder();
            tinfo2.setColor(Color.YELLOW);
            tinfo2.setTitle("**Pravidla Čekárny**");
            tinfo2.setDescription("`1.` V případě, že pobýváš v Čekárně, musíš být u počítače\n`2.` Je přísný zákaz rozhovor s členem A-Teamu nahrávat či jinak dokumentovat\n`3.` Řešte pouze věci týkající se serveru Ethernal.cz\n`4.` V rámci poděkování našim podporovatelům, si vyhrazujeme právo na jejich upřednostnění (Všechny druhy VIP a server Boosteři)\n`5.` Každý člen Týmu má právo na odmítnutí řešení probému bez udání důvodu");

            EmbedBuilder tinfo3 = new EmbedBuilder();
            tinfo3.setColor(Color.YELLOW);
            tinfo3.setTitle("**Pracovní Doba**");
            tinfo3.setDescription("Po - Pá » 11:00 - 19:00\nSo - Ne » 09:00 - 20:00");
            tinfo3.setFooter("*Mimo výše určené pracovní doby není žádný člen Týmu povinen poskytnout pomoc! Doba je taktéž čistě orientační!");

            e.getInteraction().getChannel().sendMessageEmbeds(tinfo.build()).queue();
            e.getInteraction().getChannel().sendMessageEmbeds(tinfo2.build()).queue();
            e.getInteraction().getChannel().sendMessageEmbeds(tinfo3.build()).queue();

        }


    }
}
