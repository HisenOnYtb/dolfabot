package cz.ethernal.VCSystem.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.internal.utils.PermissionUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Remove extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {

        if (e.getInteraction().getName().equalsIgnoreCase("voice-remove")) {

            OptionMapping member = e.getOption("hráč");
            Member hrac = member.getAsMember();

            if(hrac == null){
                return;
            }

            VoiceChannel chnl = e.getGuild().getVoiceChannelById(e.getMember().getVoiceState().getChannel().getId());

            if (!e.getMember().getVoiceState().inAudioChannel()) {

                EmbedBuilder neni = new EmbedBuilder();
                neni.setColor(Color.RED);
                neni.setTitle("Musíš být napojený v hlasovém kanále!");
                e.replyEmbeds(neni.build()).setEphemeral(true).queue();
                return;
            }

            if (!(PermissionUtil.checkPermission(chnl, e.getMember(), Permission.PRIORITY_SPEAKER))) {

                EmbedBuilder prava = new EmbedBuilder();
                prava.setColor(Color.RED);
                prava.setTitle("Nenacházíš se v kanále který vlastníš!");
                e.replyEmbeds(prava.build()).setEphemeral(true).queue();
                return;

            }

            chnl.getManager().putMemberPermissionOverride(hrac.getIdLong(), Permission.MESSAGE_ADD_REACTION.getRawValue() | Permission.VIEW_CHANNEL.getRawValue(), Permission.VOICE_CONNECT.getRawValue()).queue();

            EmbedBuilder hotovo = new EmbedBuilder();
            hotovo.setColor(Color.GREEN);
            hotovo.setTitle("Úspěšně jsi odebral/a přístup hráči " + hrac.getEffectiveName() + "!");

            if(hrac.getVoiceState().getChannel().equals(e.getMember().getVoiceState().getChannel())){
                e.getGuild().kickVoiceMember(hrac).queue();
            }

            e.replyEmbeds(hotovo.build()).setEphemeral(true).queue();
        }
    }

}
