package cz.ethernal;

import cz.ethernal.Commands.Fun.CoinFlip;
import cz.ethernal.Commands.Moderation.*;
import cz.ethernal.Commands.Others.DirectMsg;
import cz.ethernal.Commands.Others.Ip;
import cz.ethernal.Commands.Others.CekarnaInfo;
import cz.ethernal.JoinSystem.WelcomeMessage;
import cz.ethernal.Moderation.AutoMod;
import cz.ethernal.MusicSystem.Commands.*;
import cz.ethernal.SuggestSystem.SuggestCmd;
import cz.ethernal.TicketSystem.Tickets;
import cz.ethernal.VCSystem.Commands.*;
import cz.ethernal.VCSystem.VCHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Main {

    private static JDA bot;
    public static void main(String[] args) throws LoginException, InterruptedException {

        bot = JDABuilder.createDefault(Config.token)
                .addEventListeners(new Ban())
                .setActivity(Activity.playing("play.ethernal.cz"))
                .addEventListeners(new Slowmode())
                .addEventListeners(new Kick())
                .addEventListeners(new Clear())
                .addEventListeners(new DirectMsg())
                .addEventListeners(new CoinFlip())
                .addEventListeners(new VCHandler())
                .addEventListeners(new Say())
                .addEventListeners(new AutoMod())
                .addEventListeners(new CekarnaInfo())
                .addEventListeners(new Tickets())
                //.addEventListeners(new SuggestCmd())
                .addEventListeners(new Help())
                .addEventListeners(new Rename())
                .addEventListeners(new Ip())
                .addEventListeners(new Lock())
                .addEventListeners(new Add())
                .addEventListeners(new Unlock())
                .addEventListeners(new Remove())
                .addEventListeners(new Limit())
                .addEventListeners(new Show())
                .addEventListeners(new Hide())
                .addEventListeners(new Play())
                .addEventListeners(new Stop())
                .addEventListeners(new Skip())
                .addEventListeners(new NowPlaying())
                .addEventListeners(new Queue())
                .addEventListeners(new Loop())
                .addEventListeners(new Resume())
                .addEventListeners(new Pause())
                .addEventListeners(new Volume())
                .addEventListeners(new Private())
                .addEventListeners(new ClearQueue())
                .addEventListeners(new WelcomeMessage())
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableCache(CacheFlag.VOICE_STATE)
                .build().awaitReady();

        Guild server = bot.getGuildById(Config.guild_id);
        if (server != null) {

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("kick", "Vyhodíš hráče ze serveru")
                    .addOption(OptionType.MENTIONABLE, "hráč", "Jméno hráče, kterého chceš vyhodit", true)
                    .addOption(OptionType.STRING, "důvod", "Důvod, proč byl hráč vyhozen", true)
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("slowmode", "Nastavíš cooldown psaní v daném kanálu")
                    .addOption(OptionType.INTEGER, "sekundy", "Číslo v sekundách", true)
                    .addOption(OptionType.CHANNEL, "kanál", "Označ kanál, který chceš upravit", true)
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("cooldown", "Nastavíš cooldown psaní v daném kanálu")
                    .addOption(OptionType.INTEGER, "sekundy", "Číslo v sekundách", true)
                    .addOption(OptionType.CHANNEL, "kanál", "Označ kanál, který chceš upravit", true)
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("ban", "Zabanuješ hráče na serveru")
                    .addOption(OptionType.MENTIONABLE, "hráč", "Jméno hráče, kterého chceš vyhodit", true)
                    .addOption(OptionType.STRING, "důvod", "Důvod, proč byl hráč vyhozen", true)
                    .addOption(OptionType.STRING, "čas", "Na kolik dní chceš hráče zabanovat, napiš 0 pro permanentní", true)
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("clear", "Smažeš daný počet zpráv v textovém kanále")
                    .addOption(OptionType.INTEGER, "počet", "Počet zpráv, které chceš smazat", true)
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("dm", "Pošleš hráči, skrz bota, zprávu do soukromých zpráv")
                    .addOption(OptionType.MENTIONABLE, "hráč", "Hráč, kterému chceš zprávu poslat", true)
                    .addOption(OptionType.STRING, "zpráva", "Zpráva, kterou chceš hráči poslat", true)
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("pm", "Pošleš hráči, skrz bota, zprávu do soukromých zpráv")
                    .addOption(OptionType.MENTIONABLE, "hráč", "Hráč, kterému chceš zprávu poslat", true)
                    .addOption(OptionType.STRING, "zpráva", "Zpráva, kterou chceš hráči poslat", true)
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("coinflip", "Hodíš mincí! Panna nebo Orel!")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("say", "Napiš zprávu, kterou chceš aby bot napsal")
                    .addOption(OptionType.STRING, "zpráva", "Zpráva, kterou chceš poslat", true)
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("tsetup", "Pošleš základní zprávu pro vytvoření ticketů")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            //server.upsertCommand("suggest", "Navrhni svůj nápad a nech ostatní hráče hlasovat o tvém nápadu!")
            //        .addOption(OptionType.STRING, "zpráva", "Tvůj nápad", true)
            //        .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("nápad", "Navrhni svůj nápad a nech ostatní hráče hlasovat o tvém nápadu!")
                    .addOption(OptionType.STRING, "zpráva", "Tvůj nápad", true)
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("ip", "Zjisti IP našeho server!")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("voice", "Vypsané veškeré informace pro ovládání hlasových kanálů!")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("tinfo", "Vypsané veškeré informace ohledně čekárny!")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("voice-lock", "Uzamkneš svůj dočasný hlasový kanál!")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("voice-unlock", "Odemkneš svůj dočasný hlasový kanál!")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("voice-add", "Přidáš hráčovi přístup do tvého dočasného kanálu!!")
                    .addOption(OptionType.MENTIONABLE, "hráč", "Hráče kterého chceš přidat", true)
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("voice-remove", "Odebereš hráčovi přístup z tvého dočasného kanálu!!")
                    .addOption(OptionType.MENTIONABLE, "hráč", "Hráče kterého chceš odebrat", true)
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("voice-rename", "Přejmenuješ svůj dočasný hlasový kanál!")
                    .addOption(OptionType.STRING, "název", "Nové jméno kanálu", true)
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("voice-limit", "Nastavíš maximální počet uživatelů v tvém dočasném kanále!")
                    .addOption(OptionType.INTEGER, "počet", "Maximální počet hráčů", true)
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("voice-show", "Zobrazíš všem hráčům svůj dočasný kanál!")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("voice-hide", "Skryješ všem hráčům svůj dočasný kanál!")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("play", "Spustíš písničku!")
                    .addOption(OptionType.STRING, "název", "Název písničky či playlistu, který chceš pustit!", true)
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("stop", "Vyhodíš bota a smažeš jeho queue!")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("skip", "Přeskočíš písničku!")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("np", "Zobrazíš informace o aktuální písničce!")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("nowplaying", "Zobrazíš informace o aktuální písničce!")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("queue", "Zobrazíš informace o aktuální frontě!")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("loop", "Nastavíš režim opakování písničky!")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("pause", "Pozastavíš písničku v botovi!")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("resume", "Necháš pokračovat aktuální písničku v botovi!")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            //server.upsertCommand("volume", "Nastavíš hlasitost bota!")
                    //.addOption(OptionType.INTEGER, "hlasitost", "Číslo od 0-100", true)
                    //.queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("cq", "Vymažeš botovi queue!")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            server.upsertCommand("clearqueue", "Vymažeš botovi queue!")
                    .queue();

            //------------------------------------------------------------------------------------------------------------------------------------

            //server.upsertCommand("private", "Pošleš každému členovi na serveru zprávu!")
            //        .queue();

            //------------------------------------------------------------------------------------------------------------------------------------


        }else {
            System.out.println("Server nenalezen!");
        }

    }
}
