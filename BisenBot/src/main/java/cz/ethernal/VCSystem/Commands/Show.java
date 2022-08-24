package cz.ethernal.VCSystem.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.utils.PermissionUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Show extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {

        if (e.getInteraction().getName().equalsIgnoreCase("voice-show")) {

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

            if(!(e.getInteraction().getMember().getRoles().contains(e.getGuild().getRoleById("892476153540005929")) || e.getInteraction().getMember().getRoles().contains(e.getGuild().getRoleById("983795087853125732")) || e.getInteraction().getMember().getRoles().contains(e.getGuild().getRoleById("890675653073059850")) || e.getInteraction().getMember().getRoles().contains(e.getGuild().getRoleById("890675653073059854")) || e.getInteraction().getMember().getRoles().contains(e.getGuild().getRoleById("890675653073059856")) || e.getInteraction().getMember().getRoles().contains(e.getGuild().getRoleById("890675653073059855")) || e.getInteraction().getMember().getRoles().contains(e.getGuild().getRoleById("890675653056294988")) || e.getInteraction().getMember().getRoles().contains(e.getGuild().getRoleById("9453192944373187079")) || e.getInteraction().getMember().getRoles().contains(e.getGuild().getRoleById("8890675653073059851")) || e.getInteraction().getMember().getRoles().contains(e.getGuild().getRoleById("890675653102403634")) || e.getInteraction().getMember().getRoles().contains(e.getGuild().getRoleById("979327269686349855")) || e.getInteraction().getMember().getRoles().contains(e.getGuild().getRoleById("890675653102403635")) || e.getInteraction().getMember().getRoles().contains(e.getGuild().getRoleById("932327601048010762")) || e.getInteraction().getMember().getRoles().contains(e.getGuild().getRoleById("890675653102403641")) || e.getInteraction().getMember().getRoles().contains(e.getGuild().getRoleById("890675653102403642")) || e.getInteraction().getMember().getRoles().contains(e.getGuild().getRoleById("896381563397349437")))){

                EmbedBuilder prava = new EmbedBuilder();
                prava.setColor(Color.RED);
                prava.setTitle("Pro použití tohoto příkazu nemáš dostatečná oprávnění!");
                e.replyEmbeds(prava.build()).setEphemeral(true).queue();
                return;

            }

            chnl.getManager().putRolePermissionOverride(e.getGuild().getPublicRole().getIdLong(), Permission.VIEW_CHANNEL.getRawValue(), Permission.MESSAGE_ADD_REACTION.getRawValue()).queue();

            EmbedBuilder hotovo = new EmbedBuilder();
            hotovo.setColor(Color.GREEN);
            hotovo.setTitle("Úspěšně jsi všem zobrazil/a svůj kanál!");

            e.replyEmbeds(hotovo.build()).setEphemeral(true).queue();
        }
    }

}
