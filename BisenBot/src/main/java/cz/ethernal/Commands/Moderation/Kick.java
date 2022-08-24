package cz.ethernal.Commands.Moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Kick extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {

        if(e.getName().equalsIgnoreCase("kick")){

            if(!e.getInteraction().getMember().hasPermission(Permission.KICK_MEMBERS)){
                EmbedBuilder nopemrs = new EmbedBuilder();
                nopemrs.setColor(Color.RED);
                nopemrs.setTitle("Na tento příkaz nemáš dostatečná oprávnění!");

                e.replyEmbeds(nopemrs.build()).setEphemeral(true).queue();

                return;
            }

            e.deferReply(true).queue();

            OptionMapping member = e.getOption("hráč");
            OptionMapping reason = e.getOption("důvod");

            Member hrac = member.getAsMember();
            String duvod = reason.getAsString();


            if(hrac == e.getGuild().getOwner()){

                EmbedBuilder majitel = new EmbedBuilder();

                majitel.setTitle("Nemůžeš vyhodit majitele!");
                majitel.setColor(Color.RED);


                e.getHook().sendMessageEmbeds(majitel.build()).queue();
                return;
            }

            if(hrac.equals(e.getGuild().getMemberById("980069184895598592"))){

                EmbedBuilder bot = new EmbedBuilder();

                bot.setTitle("Mě nemůžeš vyhodit!");
                bot.setColor(Color.RED);

                e.getHook().sendMessageEmbeds(bot.build()).queue();
                return;
            }



            if(hrac.equals(e.getInteraction().getMember())){

                EmbedBuilder sam = new EmbedBuilder();

                sam.setTitle("Nemůžeš vyhodit sám sebe!");
                sam.setColor(Color.RED);

                e.getHook().sendMessageEmbeds(sam.build()).queue();
                return;
            }


            if(reason == null){
                e.getHook().sendMessage("kicked").queue();
                return;
            }else{

                EmbedBuilder vyhozeno = new EmbedBuilder();

                vyhozeno.setTitle("__Oznámení o vyhození ze Serveru__");
                vyhozeno.setColor(Color.ORANGE);
                vyhozeno.setDescription("Zdá se, že jsi pravděpodobně **porušil/a** nějaké pravidlo našeho komunitního Discord Serveru. Čti níže, aby ses seznámil/a s ostatními informacemi **kvůli** tvému vyhození.\n\n**Datum** » " + e.getInteraction().getTimeCreated().getDayOfMonth() + "/" + e.getInteraction().getTimeCreated().getMonthValue() + "/" + e.getInteraction().getTimeCreated().getYear() + " " + e.getInteraction().getTimeCreated().getHour() + ":" + e.getInteraction().getTimeCreated().getMinute() + ":" + e.getInteraction().getTimeCreated().getSecond() + "\n\n**Vyhozen členem** » " + e.getInteraction().getMember().getAsMention() + "\n\n**Důvod** » " + duvod + "\n\n**Nárok na opětovné napojení** » " + "Ano" + "\n\nPokud máš pocit, že ti byl kick udělen neprávem, prosím vytvoř si ticket v textovém kanálu  <#949588769210781706>, nebo kontaktuj někoho z A-Teamu");

                hrac.getUser().openPrivateChannel().queue(channel -> channel.sendMessageEmbeds(vyhozeno.build()).queue());
                e.getGuild().kick(hrac.getUser()).queueAfter(1, TimeUnit.SECONDS);

                EmbedBuilder uspesne = new EmbedBuilder();

                uspesne.setColor(Color.YELLOW);
                uspesne.setTitle("__Úspěšně vyhozen/a__");
                uspesne.setDescription("\n\n**Vyhozený člen** » " + hrac.getAsMention() + "\n**Důvod** » " + duvod + "\n**Čas a Datum** » " + e.getInteraction().getTimeCreated().getDayOfMonth() + "/" + e.getInteraction().getTimeCreated().getMonthValue() + "/" + e.getInteraction().getTimeCreated().getYear() + " " + e.getInteraction().getTimeCreated().getHour() + ":" + e.getInteraction().getTimeCreated().getMinute() + ":" + e.getInteraction().getTimeCreated().getSecond());

                e.getHook().sendMessageEmbeds(uspesne.build()).queue();
            }

        }

    }


}
