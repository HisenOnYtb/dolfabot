package cz.ethernal.MusicSystem.Commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import cz.ethernal.MusicSystem.GuildMusicManager;
import cz.ethernal.MusicSystem.PlayerManager;
import cz.ethernal.MusicSystem.TrackScheduler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Channel;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Skip extends ListenerAdapter {


    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {

        if(e.getInteraction().getName().equalsIgnoreCase("skip")){

            final TextChannel channel = e.getTextChannel();
            final Member self = e.getGuild().getSelfMember();
            final GuildVoiceState selfVoiceState = self.getVoiceState();
            final Channel botKanal = selfVoiceState.getChannel();

            final Member member = e.getMember();
            final GuildVoiceState memberVoiceState = member.getVoiceState();

            if(!e.getInteraction().getMember().getVoiceState().inAudioChannel()){
                EmbedBuilder error = new EmbedBuilder();
                error.setTitle("Nenacházíš se v žádném hlasovém kanále!");
                error.setColor(Color.RED);

                e.replyEmbeds(error.build()).queue();
                return;
            }

            if(botKanal == null){ //pokud bot nikde NENÍ

                EmbedBuilder error = new EmbedBuilder();
                error.setTitle("Nenacházím se v žádném hlasovém kanále!");
                error.setColor(Color.RED);

                e.replyEmbeds(error.build()).queue();
                return;

            }else{ //pokud bot nekde JE

                if(botKanal.equals(e.getMember().getVoiceState().getChannel())){ //pokud jsou spole ve VC


                    GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(e.getGuild());
                    AudioPlayer audioPlayer = musicManager.audioPlayer;

                    if(audioPlayer.getPlayingTrack() == null){
                        channel.sendMessage("momentalne nic nehraje").queue();
                        return;
                    }

                    musicManager.scheduler.nextTrack();

                    EmbedBuilder skipped = new EmbedBuilder();
                    skipped.setColor(Color.CYAN);
                    skipped.setDescription("⏩ Hráč/ka " + e.getInteraction().getMember().getAsMention() + " přeskočil/a písničku!");

                    e.replyEmbeds(skipped.build()).queue();


                }else{ //nejsou spolu ve VC

                    EmbedBuilder error = new EmbedBuilder();
                    error.setTitle("Nenacházíš se ve stejném kanále jako Bot!");
                    error.setColor(Color.RED);

                    e.replyEmbeds(error.build()).queue();
                    return;

                }


            }

        }

    }
}
