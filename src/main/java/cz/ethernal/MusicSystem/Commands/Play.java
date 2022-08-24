package cz.ethernal.MusicSystem.Commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import cz.ethernal.MusicSystem.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.thread.GenericThreadEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Play extends ListenerAdapter{
    public static String requested = null;
    public static String requested_url = null;

    public static TextChannel txt;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {

        if(e.getInteraction().getName().equalsIgnoreCase("play")){

            e.deferReply().queue();
            e.getHook().sendMessage("Hotovo!").queue(m -> m.delete().queueAfter(1, TimeUnit.MILLISECONDS));

            txt = e.getGuild().getTextChannelById(e.getInteraction().getChannel().getId());

            OptionMapping message = e.getOption("název");
            String song_name = message.getAsString();

            if(message == null){
                return;
            }

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

            if(botKanal == null){

                final AudioManager audioManager = e.getGuild().getAudioManager();
                final AudioChannel memberChannel = memberVoiceState.getChannel();

                audioManager.openAudioConnection(memberChannel);

                if(!(song_name.startsWith("https://") || song_name.startsWith("http://"))){
                    song_name = "ytsearch:" + song_name;
                    System.out.println(song_name);

                }


                requested = e.getInteraction().getMember().getAsMention();
                requested_url = e.getInteraction().getMember().getEffectiveAvatarUrl();



                PlayerManager.getInstance().loadAndPlay(channel, song_name.trim());

            }else{

                if(botKanal.equals(e.getMember().getVoiceState().getChannel())){

                    requested = e.getInteraction().getMember().getAsMention();
                    requested_url = e.getInteraction().getMember().getEffectiveAvatarUrl();


                    if(!(song_name.startsWith("https://") || song_name.startsWith("http://"))){
                        song_name = "ytsearch:" + song_name;
                        System.out.println(song_name);

                    }


                    PlayerManager.getInstance().loadAndPlay(channel, song_name.trim());

                }else{

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



