package cz.ethernal.MusicSystem.Commands;

import cz.ethernal.MusicSystem.GuildMusicManager;
import cz.ethernal.MusicSystem.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Stop extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {

        if(e.getInteraction().getName().equalsIgnoreCase("stop")){

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

                    musicManager.scheduler.player.stopTrack();
                    musicManager.scheduler.queue.clear();
                    e.getGuild().kickVoiceMember(self).queue();

                    EmbedBuilder stoppped = new EmbedBuilder();
                    stoppped.setColor(Color.RED);
                    stoppped.setDescription("⛔ Bot byl vyhozen a jeho queue byla vymazána! (" + e.getInteraction().getMember().getAsMention() + ")");

                    e.replyEmbeds(stoppped.build()).queue();


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
