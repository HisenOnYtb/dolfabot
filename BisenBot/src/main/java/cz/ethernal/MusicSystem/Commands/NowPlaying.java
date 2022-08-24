package cz.ethernal.MusicSystem.Commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import cz.ethernal.MusicSystem.GuildMusicManager;
import cz.ethernal.MusicSystem.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Channel;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class NowPlaying extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {

        if(e.getInteraction().getName().equalsIgnoreCase("np") || e.getInteraction().getName().equalsIgnoreCase("nowplaying")) {

            final TextChannel channel = e.getTextChannel();
            final Member self = e.getGuild().getSelfMember();
            final GuildVoiceState selfVoiceState = self.getVoiceState();
            final Channel botKanal = selfVoiceState.getChannel();

            final Member member = e.getMember();
            final GuildVoiceState memberVoiceState = member.getVoiceState();

            if (!e.getInteraction().getMember().getVoiceState().inAudioChannel()) {
                EmbedBuilder error = new EmbedBuilder();
                error.setTitle("Nenacházíš se v žádném hlasovém kanále!");
                error.setColor(Color.RED);

                e.replyEmbeds(error.build()).queue();
                return;
            }

            if (botKanal == null) { //pokud bot nikde NENÍ

                EmbedBuilder error = new EmbedBuilder();
                error.setTitle("Nenacházím se v žádném hlasovém kanále!");
                error.setColor(Color.RED);

                e.replyEmbeds(error.build()).queue();
                return;

            } else { //pokud bot nekde JE

                if (botKanal.equals(e.getMember().getVoiceState().getChannel())) { //pokud jsou spole ve VC


                    GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(e.getGuild());
                    AudioPlayer audioPlayer = musicManager.audioPlayer;
                    AudioTrack track = audioPlayer.getPlayingTrack();

                    if (track == null) {
                        EmbedBuilder error = new EmbedBuilder();
                        error.setTitle("V Botovi momentálně nic nehraje!");
                        error.setColor(Color.RED);

                        e.replyEmbeds(error.build()).queue();
                        return;
                    }

                    AudioTrackInfo info = track.getInfo();

                    String url = "[`" + track.getInfo().title + "`](" + track.getInfo().uri + ")";

                    EmbedBuilder np = new EmbedBuilder();
                    np.setColor(Color.MAGENTA);
                    np.setTitle("MOMENTÁLNĚ HRAJE");
                    np.addField("\uD83C\uDFB6 Název", url, false);
                    np.addField("\uD83D\uDC68 Umělec", info.author, false);
                    np.addField("⏰ Status", "`" + formatTime(track.getPosition()) + "` z `" + formatTime(info.length) + "`", false);

                    e.replyEmbeds(np.build()).queue();

                } else { //nejsou spolu ve VC

                    EmbedBuilder error = new EmbedBuilder();
                    error.setTitle("Nenacházíš se ve stejném kanále jako Bot!");
                    error.setColor(Color.RED);

                    e.replyEmbeds(error.build()).queue();
                    return;

                }


            }
        }

    }

    private String formatTime(long timeInMillis) {

        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d", minutes, seconds);

    }

}
