package cz.ethernal.Moderation;

import cz.ethernal.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.TimeUnit;


public class AutoMod extends ListenerAdapter {

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent e) {

        EmbedBuilder joined = new EmbedBuilder();
        joined.setColor(Color.YELLOW);
        joined.setTitle("Někdo se napojil do čekárny!");

        if(Config.id_tracking_channel == null){
            return;
        }

        if(Config.id_info_channel == null){
            return;
        }

        if(e.getChannelJoined().getId().equalsIgnoreCase(Config.id_tracking_channel)){
            e.getGuild().getTextChannelById(Config.id_info_channel).sendMessageEmbeds(joined.build()).queue((m -> m.delete().queueAfter(5, TimeUnit.MINUTES)));
        }

    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent e) {

        if(e.getMessage().getContentRaw().contains("https://discord.gg/")){
            e.getMessage().delete().queue();
        }

    }

}
