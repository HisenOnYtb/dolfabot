package cz.ethernal.TicketSystem;

import cz.ethernal.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Tickets extends ListenerAdapter {

    String resitel = "Nikdo";
    String theme = null;
    String text = null;
    private String nazevticketu = null;
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {



        if(e.getName().equalsIgnoreCase("tsetup")){

            if(!e.getInteraction().getMember().getId().equals(Config.id_majitele) && !e.getInteraction().getMember().getId().equals("415136736717438976")){
                e.reply("Nedostatečná práva!").queue(m -> m.deleteOriginal().queueAfter(1, TimeUnit.SECONDS));
                return;
            }

            SelectMenu menu = SelectMenu.create("menu:class")
                    .setPlaceholder("Vyber svůj problém...")
                    .setRequiredRange(1, 1)
                    .addOption("❔ - Obecný Problém", "obecny-sm")
                    .addOption("\uD83D\uDEAA - Problém si Připojením", "pripojeni-sm")
                    .addOption("❗ - Nahlášení Bugu", "bug-sm")
                    .addOption("\uD83D\uDE4E - Report Hráče", "report-sm")
                    .addOption("\uD83D\uDD28 - Žádost o Unban", "unban-sm")
                    .addOption("\uD83D\uDD12 - Zapomenuté Heslo", "heslo-sm")
                    .addOption("\uD83D\uDCD1 - Nábor", "nabor-sm")
                    .addOption("\uD83D\uDCB3 - Problém s Platbou", "platba-sm")
                    .addOption("\uD83E\uDD1D - Žádost o Spolupráci", "spoluprace-sm")
                    .addOption("⚠️ - Ostatní", "ostatni-sm")
                    .build();

            EmbedBuilder ticket = new EmbedBuilder();

            ticket.setColor(Color.YELLOW);
            ticket.setTitle("**Ethernal.cz | Tickety**");
            ticket.setDescription("Jestliže máš nějaký problém, tady ti ho pomůžeme vyřešit! V menu níže si vyber příslušnou kategorii odpovídající tvému problému a v ticketu vypiš svůj problém! Pak už jen stačí vyčkat na odpověď člena Teamu!");
            ticket.addField("Pravidla Ticketů", "`1.` Zneužívání ticketů se trestá\n`2.` Každý ticket bude po 48 hodinách neaktivity smazán\n`3.` Člen Teamu má právo na uzavření ticketu bez udání důvodu", false);
            ticket.addField("Pracovní Doba", "Na tickety odpovídáme v rámci **24 hodin** od jeho založení", false);

            e.replyEmbeds(ticket.build())
                    .addActionRow(menu)
                    .queue();
        }
    }

    @Override
    public void onSelectMenuInteraction(@NotNull SelectMenuInteractionEvent e) {


        Button close = Button.danger("close-btn", "\uD83D\uDD12 Uzavřít");
        Button prevzit = Button.success("prevzit-btn", "\uD83D\uDE4B Převzít");
        Button prenechat = Button.secondary("prenechat-btn", "❌ Nesplněno");


        if(e.getInteraction().getValues().contains("pripojeni-sm")){

            theme = "Problém si Připojením";
            text = "\n\n`1.` Tvůj herní nick\n`2.` Název klientu\n`3.` Verze na které hraješ\n`4.` Screenshot a Popis problému";

        }else if(e.getInteraction().getValues().contains("obecny-sm")){

            theme = "Obecný Problém";
            text = "\n\n`1.` Tvůj herní nick\n`2.` Screenshot nebo Popis problému";

        }else if(e.getInteraction().getValues().contains("bug-sm")){

            theme = "Nahlášení Bugu";
            text = "\n\n`1.` Tvůj herní nick\n`2.` Popis bugu\n`3.` Název serveru (BoxFight, EggWars, ...)\n`4.` Obrázek či Video bugu";


        }else if(e.getInteraction().getValues().contains("report-sm")){

            theme = "Report Hráče";
            text = "\n\n`1.` Tvůj herní nick\n`2.` Jméno hráče\n`3.` Důkaz (Video či Obrázek)\n`4.` Název serveru (BoxFight, EggWars, ...)\n`5.` Popis činnosti";

        }else if(e.getInteraction().getValues().contains("unban-sm")){

            theme = "Žádost o Unban";
            text = "\n\n`1.` Tvůj herní nick\n`2.` Screenshot banu\n`3.` Tvůj dodatek (Nepovinné)";

        }else if(e.getInteraction().getValues().contains("heslo-sm")){

            theme = "Zapomenuté Heslo";
            text = "\n\n`1.` Tvůj herní nick\n`2.` Vlastníš originální Minecraft účet?";

        }else if(e.getInteraction().getValues().contains("nabor-sm")){

            theme = "Nábor";
            text = "\n\n`1.` Tvůj herní nick\n`2.` Pozice o kterou žádáš\n`3.` Něco o sobě\n`4.` Zkušenosti (Nepovinné)";

        }else if(e.getInteraction().getValues().contains("platba-sm")){

            theme = "Problém s Platbou";
            text = "\n\n`1.` Tvůj herní nick\n`2.` Název produktu\n`3.` Popis problému";


        }else if(e.getInteraction().getValues().contains("ostatni-sm")){

            theme = "Ostatní";
            text = "\n\n`1.` Tvůj herní nick\n`2.` Popis problému";

        }else if(e.getInteraction().getValues().contains("spoluprace-sm")){

            theme = "Žádost o Spolupráci";
            text = "\n\n`1.` Něco o sobě\n`2.` Link na danou sociální síť\n`3.` Tvůj dodatek";

        }


        EnumSet<Permission> ano = EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY, Permission.MESSAGE_SEND, Permission.MESSAGE_ADD_REACTION, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_EMBED_LINKS, Permission.USE_APPLICATION_COMMANDS);
        EnumSet<Permission> ne = EnumSet.of(Permission.MANAGE_CHANNEL, Permission.MESSAGE_MANAGE);

        if(Config.id_category_tickets == null){
            System.out.println("Není specifikována kategorie kam vytvořit tickety!");
            return;
        }

        Category category = e.getGuild().getCategoryById(Config.id_category_tickets);

        resitel = "Nikdo";

        if(category == null){
            return;
        }

        TextChannel kanal = category.createTextChannel("ticket-" + e.getInteraction().getMember().getEffectiveName())
                .addMemberPermissionOverride(e.getInteraction().getMember().getIdLong(), Permission.VIEW_CHANNEL.getRawValue() | Permission.MESSAGE_HISTORY.getRawValue() | Permission.MESSAGE_SEND.getRawValue() | Permission.MESSAGE_ADD_REACTION.getRawValue() | Permission.MESSAGE_ATTACH_FILES.getRawValue() | Permission.MESSAGE_EMBED_LINKS.getRawValue() | Permission.USE_APPLICATION_COMMANDS.getRawValue(), Permission.MANAGE_CHANNEL.getRawValue() | Permission.MESSAGE_MANAGE.getRawValue())
                .addRolePermissionOverride(890675653089832994L, Permission.MANAGE_CHANNEL.getRawValue() | Permission.VIEW_CHANNEL.getRawValue() | Permission.KICK_MEMBERS.getRawValue(), Permission.MANAGE_PERMISSIONS.getRawValue())
                .addRolePermissionOverride(890675653073059851L, Permission.MANAGE_CHANNEL.getRawValue() | Permission.VIEW_CHANNEL.getRawValue() | Permission.KICK_MEMBERS.getRawValue(), Permission.MANAGE_PERMISSIONS.getRawValue())
                .addRolePermissionOverride(890675653102403639L, Permission.MANAGE_CHANNEL.getRawValue() | Permission.VIEW_CHANNEL.getRawValue() | Permission.KICK_MEMBERS.getRawValue(), Permission.MANAGE_PERMISSIONS.getRawValue())
                .addRolePermissionOverride(986696172670419014L, Permission.MANAGE_CHANNEL.getRawValue() | Permission.VIEW_CHANNEL.getRawValue() | Permission.KICK_MEMBERS.getRawValue(), Permission.MANAGE_PERMISSIONS.getRawValue())
                .addRolePermissionOverride(890675653102403634L, Permission.MANAGE_CHANNEL.getRawValue() | Permission.VIEW_CHANNEL.getRawValue() | Permission.KICK_MEMBERS.getRawValue(), Permission.MANAGE_PERMISSIONS.getRawValue())
                .addRolePermissionOverride(979327269686349855L, Permission.MANAGE_CHANNEL.getRawValue() | Permission.VIEW_CHANNEL.getRawValue() | Permission.KICK_MEMBERS.getRawValue(), Permission.MANAGE_PERMISSIONS.getRawValue())
                .addRolePermissionOverride(890675653102403635L, Permission.MANAGE_CHANNEL.getRawValue() | Permission.VIEW_CHANNEL.getRawValue() | Permission.KICK_MEMBERS.getRawValue(), Permission.MANAGE_PERMISSIONS.getRawValue())
                .addRolePermissionOverride(932327601048010762L, Permission.MANAGE_CHANNEL.getRawValue() | Permission.VIEW_CHANNEL.getRawValue() | Permission.KICK_MEMBERS.getRawValue(), Permission.MANAGE_PERMISSIONS.getRawValue())
                .addRolePermissionOverride(890675653102403641L, Permission.MANAGE_CHANNEL.getRawValue() | Permission.VIEW_CHANNEL.getRawValue() | Permission.KICK_MEMBERS.getRawValue(), Permission.MANAGE_PERMISSIONS.getRawValue())
                .addRolePermissionOverride(1003746592630775829L, Permission.MANAGE_CHANNEL.getRawValue() | Permission.VIEW_CHANNEL.getRawValue() | Permission.KICK_MEMBERS.getRawValue(), Permission.MANAGE_PERMISSIONS.getRawValue())
                .addRolePermissionOverride(e.getGuild().getPublicRole().getIdLong(), Permission.MESSAGE_ADD_REACTION.getRawValue(), Permission.VIEW_CHANNEL.getRawValue())
                .setTopic("Řešitel » " + resitel)
                .complete();


        EmbedBuilder info = new EmbedBuilder();


        info.setColor(Color.YELLOW);

        info.setAuthor("Problém hráče " + e.getInteraction().getMember().getEffectiveName(), e.getInteraction().getMember().getAvatarUrl(), e.getInteraction().getMember().getEffectiveAvatarUrl());
        info.setDescription("\nDěkujeme ti za kontaktování našeho A-Teamu\nProsím, vypiš svůj problém níže a vyčkej na odpověď.\n\nNezapomeň dodržovat pravidla v <#949588769210781706>, příslušný člen Teamu se ti jistě brzy ozve! Mezitím co čekáš, odpověz prosím na pár otázek:" + text + "\n\n**Téma » **" + theme + "**\nZaloženo » **" + e.getInteraction().getTimeCreated().getDayOfMonth() + "/" + e.getInteraction().getTimeCreated().getMonthValue() + "/" + e.getInteraction().getTimeCreated().getYear() + " " + e.getInteraction().getTimeCreated().getHour() + ":" + e.getInteraction().getTimeCreated().getMinute() + ":" + e.getInteraction().getTimeCreated().getSecond() + "\n**Řešitel » **" + resitel);

        EmbedBuilder uspech = new EmbedBuilder();
        uspech.setColor(Color.GREEN);
        uspech.setTitle("Úspěch!");
        uspech.setDescription("Vytvořil/a sis ticket v " + "<#" + kanal.getId() + ">");

        e.replyEmbeds(uspech.build()).setEphemeral(true).queue();

        kanal.sendMessageEmbeds(info.build())
                .setActionRows(ActionRow.of(close, prevzit, prenechat))
                .queue();

        kanal.sendMessage(e.getInteraction().getMember().getAsMention()).queue(m -> m.delete().queueAfter(10, TimeUnit.MICROSECONDS));

    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent e) {

        Button anouzavri = Button.secondary("uzavrit-btn", "✅ Potvrdit");
        Button sduvodem = Button.secondary("sduvodem-btn", "\uD83D\uDCD1 S Důvodem");
        Button archivovat = Button.secondary("archiv-btn", "\uD83D\uDCEE Archivovat");



        if(e.getInteraction().getButton().getId().equalsIgnoreCase("uzavrit-btn")){

            if(!e.getInteraction().getMember().hasPermission(Permission.KICK_MEMBERS)){
                EmbedBuilder nopemrs = new EmbedBuilder();
                nopemrs.setColor(Color.RED);
                nopemrs.setTitle("Pouze členové A-Teamu mohou uzavírat tickety!");

                e.replyEmbeds(nopemrs.build()).setEphemeral(true).queue();
                return;
            }

            e.getInteraction().getChannel().delete().queue();
            return;
        }

        if(e.getInteraction().getButton().getId().equalsIgnoreCase("sduvodem-btn")){

            TextChannel channel = e.getGuild().getTextChannelById(e.getInteraction().getChannel().getId());



            TextInput duvod = TextInput.create("duvod", "Důvod", TextInputStyle.PARAGRAPH)
                    .setRequired(true)
                    .setMinLength(10)
                    .setMaxLength(120)
                    .build();

            Modal modal = Modal.create("modal", "Uveď Důvod Uzavření Ticketu")
                    .addActionRows(ActionRow.of(duvod))
                    .build();

            if(!e.getInteraction().getMember().hasPermission(Permission.KICK_MEMBERS)){
                EmbedBuilder nopemrs = new EmbedBuilder();
                nopemrs.setColor(Color.RED);
                nopemrs.setTitle("Pouze členové A-Teamu mohou uzavírat tickety!");

                e.replyEmbeds(nopemrs.build()).setEphemeral(true).queue();
                return;
            }

            nazevticketu = channel.getName();

            e.replyModal(modal).queue();
            return;
        }




        if(e.getInteraction().getButton().getId().equalsIgnoreCase("archiv-btn")){

            TextChannel channel = e.getGuild().getTextChannelById(e.getInteraction().getChannel().getId());


            if(Config.id_category_tickets_archiv == null){
                channel.sendMessage("Funkce archivování není řádně nastavena!");
                return;
            }

            if(!e.getInteraction().getMember().hasPermission(Permission.KICK_MEMBERS)){
                EmbedBuilder nopemrs = new EmbedBuilder();
                nopemrs.setColor(Color.RED);
                nopemrs.setTitle("Pouze členové A-Teamu mohou uzavírat tickety!");

                e.replyEmbeds(nopemrs.build()).setEphemeral(true).queue();
                return;
            }

            Category ctgr = e.getGuild().getCategoryById(Config.id_category_tickets_archiv);
            channel.getManager().setParent(ctgr).queue();
            channel.getManager().sync().queue();

            String member = e.getInteraction().getChannel().getName().substring(7);
            System.out.println(member);

            List<Member> members = e.getGuild().getMembersByNickname(member, true);

            for(Member player : members){

                channel.getManager().putMemberPermissionOverride(player.getIdLong(), Permission.MESSAGE_EXT_EMOJI.getRawValue(), Permission.VIEW_CHANNEL.getRawValue()).queue();

                System.out.println("Debug: " + player.getNickname());

            }

            e.getMessage().delete().queue();

            EmbedBuilder info = new EmbedBuilder();
            info.setColor(Color.YELLOW);
            info.setTitle("Ticket archivován! Pro smazání ticketu ho uzavři tak, jak jsi zvyklý!");


            e.replyEmbeds(info.build()).setEphemeral(true).queue();

            return;
        }




        if(e.getInteraction().getButton().getId().equalsIgnoreCase("prevzit-btn")){

            TextChannel channel = e.getGuild().getTextChannelById(e.getInteraction().getChannel().getId());

            if(!channel.getTopic().contains("Nikdo")){

                EmbedBuilder error = new EmbedBuilder();
                error.setColor(Color.RED);
                error.setTitle("O tento ticket se již někdo stará!");
                e.replyEmbeds(error.build()).setEphemeral(true).queue();
                return;
            }

            String jmeno = e.getInteraction().getMember().getEffectiveName().replaceAll(" ", "-").toLowerCase();

            if(e.getChannel().getName().contains(jmeno)){

                EmbedBuilder error = new EmbedBuilder();
                error.setColor(Color.RED);
                error.setTitle("Nemůžeš se starat o svůj vlastní ticket!");
                e.replyEmbeds(error.build()).setEphemeral(true).queue();
                return;
            }

            if(channel.getTopic().contains("Nikdo")){

                channel.getManager().setTopic("Řešitel » " + e.getInteraction().getMember().getEffectiveName()).queue();

                resitel = e.getInteraction().getMember().getAsMention();

                String hrac = channel.getTopic().substring(10);
                System.out.println(hrac);

                if(channel.getTopic() == null){
                    return;
                }


                EmbedBuilder info = new EmbedBuilder();

                info.setColor(Color.YELLOW);
                info.setTitle("Řešení Problému");
                info.setDescription("\nTvůj ticket je nyní v procesu řešení! Prosím, řiď se instrukcemi Teamu a věříme, že tvůj problém bude brzy vyřešen\n\n V žádném případě **neoznačuj** členy týmu za účelem zrychlení procesu řešení!" + "\n\n**Téma » **" + theme + "**\nZaloženo » **" + e.getInteraction().getTimeCreated().getDayOfMonth() + "/" + e.getInteraction().getTimeCreated().getMonthValue() + "/" + e.getInteraction().getTimeCreated().getYear() + " " + e.getInteraction().getTimeCreated().getHour() + ":" + e.getInteraction().getTimeCreated().getMinute() + ":" + e.getInteraction().getTimeCreated().getSecond() + "\n**Řešitel » **" + resitel);


                e.getMessage().editMessageEmbeds(info.build()).queue();

                EmbedBuilder supech = new EmbedBuilder();

                supech.setColor(Color.ORANGE);
                supech.setDescription("Člen " + e.getMember().getAsMention() + " se nyní bude starat o tento ticket!");
                supech.setTitle("Ticket Převzat!");

                e.replyEmbeds(supech.build()).setEphemeral(false).queue();
            }
        }

        if(e.getInteraction().getButton().getId().equals("close-btn")){

            if(!e.getInteraction().getMember().hasPermission(Permission.KICK_MEMBERS)){
                EmbedBuilder nopemrs = new EmbedBuilder();
                nopemrs.setColor(Color.RED);
                nopemrs.setTitle("Pouze členové A-Teamu mohou uzavřít ticket!!");

                e.replyEmbeds(nopemrs.build()).setEphemeral(true).queue();
                return;

            }else {

                EmbedBuilder confirm = new EmbedBuilder();
                confirm.setColor(Color.YELLOW);
                confirm.setTitle("Potvrzení");
                confirm.setDescription("Opravdu by jsi chtěl/a tento ticket uzavřít?");

                e.replyEmbeds(confirm.build()).setEphemeral(false)
                        .addActionRow(anouzavri, sduvodem, archivovat)
                        .queue();

            }
        }

        if(e.getInteraction().getButton().getId().equalsIgnoreCase("prenechat-btn")){

            TextChannel channel = e.getGuild().getTextChannelById(e.getInteraction().getChannel().getId());

            System.out.println(channel.getTopic());
            System.out.println(e.getInteraction().getMember().getEffectiveName().toLowerCase());

            if(!e.getInteraction().getMember().hasPermission(Permission.KICK_MEMBERS)){
                EmbedBuilder nopemrs = new EmbedBuilder();
                nopemrs.setColor(Color.RED);
                nopemrs.setTitle("Pouze členové A-Teamu mohou uzavřít ticket!!");

                e.replyEmbeds(nopemrs.build()).setEphemeral(true).queue();
                return;

            }

            EmbedBuilder nesplneno = new EmbedBuilder();
            nesplneno.setColor(Color.RED);
            nesplneno.setTitle("Nesplněné Požadavky!");
            nesplneno.setDescription("Neodpověděl/a jsi na všechny vyžádané otázky! Prosím, vypiš všechny odpovědi níže a vyčkej na odpověď někoho z Teamu! Pokud tak v brzké době neučiníš, tvůj ticket bude automaticky uzavřen!");

            e.replyEmbeds(nesplneno.build()).queue();
        }
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent e) {

        if(e.getModalId().equals("modal")){

            String trimmed = e.getInteraction().getChannel().getName().substring(7);

            String result = trimmed.replaceAll("-", " ");

            System.out.println(result);

            String duvod = e.getValue("duvod").getAsString();

            Member hrac = e.getGuild().getMembersByName(result, true).get(0);

            EmbedBuilder ebduvod = new EmbedBuilder();
            ebduvod.setColor(Color.YELLOW);
            ebduvod.setTitle("Tvůj Ticket byl Uzavřen!");
            ebduvod.setDescription("**Název Ticketu** » `" + nazevticketu + "`\n**Důvod** » *" + duvod + "*");

            hrac.getUser().openPrivateChannel().queue(channel -> channel.sendMessageEmbeds(ebduvod.build()).queue());
            e.getInteraction().getChannel().delete().queue();

            EmbedBuilder succ = new EmbedBuilder();
            succ.setColor(Color.GREEN);
            succ.setTitle("Ticket Úspěšně Uzavřen!");

            e.replyEmbeds(succ.build()).setEphemeral(true).queue();

            nazevticketu = null;

        }

    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent e) {


        TextChannel channel = e.getGuild().getTextChannelById(e.getChannel().getId());

        if(channel == null){
            return;
        }

        if(channel.getTopic() == null)
            return;

        if(channel.getName().contains(e.getMember().getEffectiveName().toLowerCase()) || e.getMember().getId().equalsIgnoreCase("980069184895598592"))
            return;

        if(channel.getTopic().contains("Nikdo")){

            e.getMessage().delete().queue();

            EmbedBuilder neni = new EmbedBuilder();
            neni.setColor(Color.RED);
            neni.setTitle("Tento ticket nikdo neřeší! Pokud ho chceš začít řešit, převezmi ho!");

            channel.sendMessageEmbeds(neni.build()).queue(m -> m.delete().queueAfter(3, TimeUnit.SECONDS));
        }else{
            System.out.println("Neni nikdo");
        }


    }

}
