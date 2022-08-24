package cz.ethernal.VCSystem;

import cz.ethernal.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.utils.PermissionUtil;
import org.jetbrains.annotations.NotNull;

public class VCHandler extends ListenerAdapter {

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent e) {

        if (Config.id_category_voice == null) {
            return;
        }

        if(Config.id_channel_voice == null){
            return;
        }

        Category category = e.getGuild().getCategoryById(Config.id_category_voice);
        VoiceChannel kanal = e.getGuild().getVoiceChannelById(Config.id_channel_voice);

        if(e.getChannelJoined().equals(kanal)){

            VoiceChannel canal = category.createVoiceChannel(e.getMember().getEffectiveName() + "'s kanál")
                    .addMemberPermissionOverride(e.getMember().getIdLong(), Permission.PRIORITY_SPEAKER.getRawValue(), Permission.VOICE_MOVE_OTHERS.getRawValue())
                    .complete();
            e.getGuild().moveVoiceMember(e.getMember(), canal).queue();

        }

    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent e) {

        if (Config.id_category_voice == null) {
            return;
        }

        if(Config.id_channel_voice == null){
            return;
        }

        VoiceChannel kanal = e.getGuild().getVoiceChannelById(Config.id_channel_voice);
        Category category = e.getGuild().getCategoryById(Config.id_category_voice);

        VoiceChannel vc = e.getGuild().getVoiceChannelById(e.getChannelLeft().getId());

        if(category == null){
            return;
        }

        if(e.getMember().getUser() == null){
            return;
        }

        if(!(e.getGuild().getId().equals(Config.guild_id)) || !(vc.getParentCategory().getId().equals(Config.id_category_voice))){
            return;
        }

        if(e.getChannelLeft().equals(kanal)){
            return;
        }

        VoiceChannel chnl = e.getGuild().getVoiceChannelById(e.getChannelLeft().getId());

        if(PermissionUtil.checkPermission(chnl, e.getMember(), Permission.PRIORITY_SPEAKER) && chnl.getMembers().size() == 0){
            e.getChannelLeft().delete().queue();
            return;
        }

        if(chnl.getMembers().size() == 0){
            e.getChannelLeft().delete().queue();
        }


    }

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent e) {

        if (Config.id_category_voice == null) {
            return;
        }

        if(Config.id_channel_voice == null){
            return;
        }

        VoiceChannel kanal = e.getGuild().getVoiceChannelById(Config.id_channel_voice);
        Category category = e.getGuild().getCategoryById(Config.id_category_voice);
        VoiceChannel chnl = e.getGuild().getVoiceChannelById(e.getChannelLeft().getId());


        if(chnl == null)
            return;


        if(!(e.getGuild().getId().equals(Config.guild_id))){
            return;
        }

        if(e.getChannelLeft().equals(kanal)){
            return;
        }

        //------------------------------------------------------------------------------------------------------------------

        if(e.getChannelJoined().equals(kanal) && e.getChannelLeft().getMembers().size() == 0 && (chnl.getParentCategoryId().equals(Config.id_category_voice))){
            e.getChannelLeft().delete().queue();
            return;
        }

        if(e.getChannelJoined().equals(kanal)){
            VoiceChannel canal = category.createVoiceChannel(e.getMember().getEffectiveName() + "'s kanál")
                    .addMemberPermissionOverride(e.getMember().getIdLong(), Permission.PRIORITY_SPEAKER.getRawValue(), Permission.VOICE_MOVE_OTHERS.getRawValue())
                    .complete();
            e.getGuild().moveVoiceMember(e.getMember(), canal).queue();
            return;
        }

        //------------------------------------------------------------------------------------------------------------------

        if(category == null){
            return;
        }

        if(e.getMember().getUser() == null){
            return;
        }

        if(PermissionUtil.checkPermission(chnl, e.getMember(), Permission.PRIORITY_SPEAKER) && chnl.getMembers().size() == 0 && (chnl.getParentCategoryId().equals(Config.id_category_voice))){
            e.getChannelLeft().delete().queue();
            return;
        }

        if(chnl.getMembers().size() == 0 && (chnl.getParentCategoryId().equals(Config.id_category_voice))){
            e.getChannelLeft().delete().queue();
            return;
        }

    }
}
