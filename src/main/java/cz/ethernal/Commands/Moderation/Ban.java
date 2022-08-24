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

public class Ban extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {


        if(e.getName().equalsIgnoreCase("ban")){

            if(!e.getInteraction().getMember().hasPermission(Permission.BAN_MEMBERS)){
                EmbedBuilder nopemrs = new EmbedBuilder();
                nopemrs.setColor(Color.RED);
                nopemrs.setTitle("Na tento příkaz nemáš dostatečná oprávnění!");

                e.replyEmbeds(nopemrs.build()).setEphemeral(true).queue();

                return;
            }

            e.deferReply(true).queue();

            OptionMapping member = e.getOption("hráč");
            OptionMapping reason = e.getOption("důvod");
            OptionMapping time = e.getOption("čas");

            Member hrac = member.getAsMember();
            String duvod = reason.getAsString();
            Integer cas = time.getAsInt();


            if(hrac == e.getGuild().getOwner()){

                EmbedBuilder majitel = new EmbedBuilder();

                majitel.setTitle("Nemůžeš zabanovat majitele!");
                majitel.setColor(Color.RED);


                e.getHook().sendMessageEmbeds(majitel.build()).queue();
                return;
            }

            if (hrac == null){

                EmbedBuilder sam = new EmbedBuilder();

                sam.setTitle("Tento člen neexistuje!");
                sam.setColor(Color.RED);

                e.getHook().sendMessageEmbeds(sam.build()).queue();
                return;
            }

            if(hrac.equals(e.getGuild().getMemberById("980069184895598592"))){

                EmbedBuilder bot = new EmbedBuilder();

                bot.setTitle("Mě nemůžeš zabanovat!");
                bot.setColor(Color.RED);

                e.getHook().sendMessageEmbeds(bot.build()).queue();
                return;
            }


            if(hrac.equals(e.getInteraction().getMember())){

                EmbedBuilder sam = new EmbedBuilder();

                sam.setTitle("Nemůžeš zabanovat sám sebe!");
                sam.setColor(Color.RED);

                e.getHook().sendMessageEmbeds(sam.build()).queue();
                return;
            }

            if(reason == null){
                return;
            }


            if(cas == 0){
                EmbedBuilder vyhozeno = new EmbedBuilder();

                vyhozeno.setTitle("__Oznámení o zabanování na Serveru__");
                vyhozeno.setColor(Color.RED);
                vyhozeno.setDescription("Zdá se, že jsi pravděpodobně **porušil/a** nějaké pravidlo našeho komunitního Discord Serveru. Čti níže, aby ses seznámil/a s ostatními informacemi **kvůli** tvému zabanování.\n\n**Datum** » " + e.getInteraction().getTimeCreated().getDayOfMonth() + "/" + e.getInteraction().getTimeCreated().getMonthValue() + "/" + e.getInteraction().getTimeCreated().getYear() + " " + e.getInteraction().getTimeCreated().getHour() + ":" + e.getInteraction().getTimeCreated().getMinute() + ":" + e.getInteraction().getTimeCreated().getSecond() + "\n\n**Zabanován členem** » " + e.getInteraction().getMember().getAsMention() + "\n\n**Důvod** » " + duvod + "\n\n**Doba Trvání** » " + "Permanentní" +"\n\n**Nárok na opětovné napojení** » " + "Ne" + "\n\nPokud máš pocit, že ti byl ban udělen neprávem, kontaktuj někoho z A-Teamu");

                hrac.getUser().openPrivateChannel().queue(channel -> channel.sendMessageEmbeds(vyhozeno.build()).queue());
                e.getInteraction().getGuild().ban(hrac.getUser(), cas, duvod).queueAfter(1, TimeUnit.SECONDS);

                EmbedBuilder uspesne = new EmbedBuilder();

                uspesne.setColor(Color.RED);
                uspesne.setTitle("__Úspěšně zabanován/a__");
                uspesne.setDescription("\n\n**Zabanovaný člen** » " + hrac.getAsMention() + "\n**Důvod** » " + duvod  + "\n**Doba Trvání** » " + "Permanentní" + "\n**Čas a Datum** » " + e.getInteraction().getTimeCreated().getDayOfMonth() + "/" + e.getInteraction().getTimeCreated().getMonthValue() + "/" + e.getInteraction().getTimeCreated().getYear() + " " + e.getInteraction().getTimeCreated().getHour() + ":" + e.getInteraction().getTimeCreated().getMinute() + ":" + e.getInteraction().getTimeCreated().getSecond());

                e.getHook().sendMessageEmbeds(uspesne.build()).queue();
                return;

            }else{

                EmbedBuilder vyhozeno = new EmbedBuilder();

                vyhozeno.setTitle("__Oznámení o zabanování na Serveru__");
                vyhozeno.setColor(Color.RED);
                vyhozeno.setDescription("Zdá se, že jsi pravděpodobně **porušil/a** nějaké pravidlo našeho komunitního Discord Serveru. Čti níže, aby ses seznámil/a s ostatními informacemi **kvůli** tvému zabanování.\n\n**Datum** » " + e.getInteraction().getTimeCreated().getDayOfMonth() + "/" + e.getInteraction().getTimeCreated().getMonthValue() + "/" + e.getInteraction().getTimeCreated().getYear() + " " + e.getInteraction().getTimeCreated().getHour() + ":" + e.getInteraction().getTimeCreated().getMinute() + ":" + e.getInteraction().getTimeCreated().getSecond() + "\n\n**Zabanován členem** » " + e.getInteraction().getMember().getAsMention() + "\n\n**Důvod** » " + duvod + "\n\n**Doba Trvání** » " + cas + " Den/Dní" +"\n\n**Nárok na opětovné napojení (po banu)** » " + "Ano" + "\n\nPokud máš pocit, že ti byl ban udělen neprávem, kontaktuj někoho z A-Teamu");

                hrac.getUser().openPrivateChannel().queue(channel -> channel.sendMessageEmbeds(vyhozeno.build()).queue());
                e.getInteraction().getGuild().ban(hrac.getUser(), cas, duvod).queueAfter(1, TimeUnit.SECONDS);

                EmbedBuilder uspesne = new EmbedBuilder();

                uspesne.setColor(Color.RED);
                uspesne.setTitle("__Úspěšně zabanován/a__");
                uspesne.setDescription("\n\n**Zabanovaný člen** » " + hrac.getAsMention() + "\n**Důvod** » " + duvod  + "\n**Doba Trvání** » " + cas + " Den/Dní" + "\n**Čas a Datum** » " + e.getInteraction().getTimeCreated().getDayOfMonth() + "/" + e.getInteraction().getTimeCreated().getMonthValue() + "/" + e.getInteraction().getTimeCreated().getYear() + " " + e.getInteraction().getTimeCreated().getHour() + ":" + e.getInteraction().getTimeCreated().getMinute() + ":" + e.getInteraction().getTimeCreated().getSecond());

                e.getHook().sendMessageEmbeds(uspesne.build()).queue();
            }


        }

    }

}

