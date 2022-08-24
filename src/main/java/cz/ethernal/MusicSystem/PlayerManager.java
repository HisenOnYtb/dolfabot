package cz.ethernal.MusicSystem;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import cz.ethernal.MusicSystem.Commands.Play;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PlayerManager {


    private static PlayerManager INSTANCE;

    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    public PlayerManager(){
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);

    }


    public GuildMusicManager getMusicManager(Guild guild){
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);

            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

            return guildMusicManager;
        });

    }

    public void loadAndPlay(TextChannel channel, String trackUrl){

        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());
        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {

            @Override
            public void trackLoaded(AudioTrack audioTrack) {


                String url = "[`" + audioTrack.getInfo().title + "`](" + audioTrack.getInfo().uri + ")";

                EmbedBuilder player = new EmbedBuilder();
                //player.setColor(Color.MAGENTA);
                player.setAuthor("PŘIDÁNO DO QUEUE", "https://discord.gg/UTRGCJcczM", Play.requested_url);
                player.addField("Název", url, false);
                player.addField("\uD83D\uDC68 Umělec", audioTrack.getInfo().author, true);
                player.addField("⏰ Délka", String.valueOf(formatTime(audioTrack.getDuration())), true);
                player.addField("\uD83D\uDCEE Požádal/a", Play.requested, true);

                channel.sendMessageEmbeds(player.build()).queue();

                musicManager.scheduler.queue(audioTrack);
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {

                final List<AudioTrack> tracks = audioPlaylist.getTracks();

                /*EmbedBuilder player = new EmbedBuilder();
                player.setColor(Color.YELLOW);
                player.setTitle("PŘIDÁNO DO FRONTY", "https://discord.gg/UTRGCJcczM");
                player.setDescription("\uD83C\uDFB5 `" + audioPlaylist.getName() + "`");
                player.addField("Umělec", null, true); */

                audioPlaylist.getTracks().stream().limit(trackUrl.startsWith("ytsearch:") ? 1 : audioPlaylist.getTracks().size()).forEach(this::trackLoaded);
            }

            @Override
            public void noMatches() {

                EmbedBuilder nenalezena = new EmbedBuilder();
                nenalezena.setColor(Color.RED);
                nenalezena.setTitle("Skladba nenalezena!");

                channel.sendMessageEmbeds(nenalezena.build()).queue();

            }

            @Override
            public void loadFailed(FriendlyException e) {

                e.getStackTrace();

                EmbedBuilder nenalezena = new EmbedBuilder();
                nenalezena.setColor(Color.RED);
                nenalezena.setTitle("Nastala chyba při načítání písničky!");

                channel.sendMessageEmbeds(nenalezena.build()).queue();
            }
        });

    }

    public static PlayerManager getInstance(){

        if(INSTANCE == null){
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }

    private String formatTime(long timeInMillis) {

        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d", minutes, seconds);

    }

}
