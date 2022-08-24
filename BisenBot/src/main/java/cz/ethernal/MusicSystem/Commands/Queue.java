package cz.ethernal.MusicSystem.Commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import cz.ethernal.MusicSystem.GuildMusicManager;
import cz.ethernal.MusicSystem.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Queue extends ListenerAdapter {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {

        if(e.getInteraction().getName().equalsIgnoreCase("queue")){

            final TextChannel channel = e.getTextChannel();
            GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(e.getGuild());
            BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

            if(queue.isEmpty()){
                EmbedBuilder error = new EmbedBuilder();
                error.setTitle("Queue je momentálně prázdná!");
                error.setColor(Color.RED);

                e.replyEmbeds(error.build()).queue();
                return;
            }

            int trackCount = Math.min(queue.size(), 20);
            List<AudioTrack> trackList = new ArrayList<>(queue);

            EmbedBuilder queueeb = new EmbedBuilder();
            queueeb.setTitle("\uD83D\uDCDC NADCHÁZEJÍCÍ PÍSNIČKY - QUEUE");
            queueeb.setColor(Color.cyan);

            for(int i = 0; i < trackCount; i++){

                final AudioTrack track = trackList.get(i);
                AudioTrackInfo info = track.getInfo();

                queueeb.setDescription("**#" + String.valueOf(i + 1) + "** `" + info.title + "` - `[" + formatTime(track.getDuration()) + "]`");

            }

            ReplyCallbackAction messageAction = e.replyEmbeds(queueeb.build());

            messageAction.queue();


        }

    }

    private String formatTime(long timeInMillis) {

        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d", minutes, seconds);

    }
}
