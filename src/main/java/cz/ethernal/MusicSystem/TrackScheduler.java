package cz.ethernal.MusicSystem;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import cz.ethernal.MusicSystem.Commands.Play;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class TrackScheduler extends AudioEventAdapter {


    public final AudioPlayer player;
    public final BlockingQueue<AudioTrack> queue;
    public boolean repeating = false;
    private final List<Consumer<AudioPlayer>> endTrack = new ArrayList<>();

    public TrackScheduler(final AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(final AudioTrack track) {
        if (!this.player.startTrack(track, true)) {
            this.queue.offer(track);
        }
    }

    public void nextTrack() {
        this.player.startTrack(this.queue.poll(), false);


        /*TextChannel channel = Play.txt;

        EmbedBuilder playerko = new EmbedBuilder();
        playerko.setColor(Color.MAGENTA);
        playerko.setAuthor("MOMENTÁLNĚ HRAJE", "https://discord.gg/UTRGCJcczM", Play.requested_url);
        playerko.addField("Název", "`" + track.getInfo().title + "` - **" + String.valueOf(formatTime(track.getDuration())) + "**", false);
        playerko.addField("\uD83D\uDC68 Umělec", track.getInfo().author, true);
        playerko.addField("\uD83D\uDCEE Požádal/a", Play.requested, true);

        channel.sendMessageEmbeds(playerko.build()).queue();*/

    }

    @Override
    public void onTrackEnd(final AudioPlayer player, final AudioTrack track, final AudioTrackEndReason endReason) {
        endTrack.forEach(consumer -> consumer.accept(player));

        if (endReason.mayStartNext) {

            if(this.repeating){
                this.player.startTrack(track.makeClone(), false);
                return;
            }

            nextTrack();
        }
    }

    private String formatTime(long timeInMillis) {

        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d", minutes, seconds);

    }

}
