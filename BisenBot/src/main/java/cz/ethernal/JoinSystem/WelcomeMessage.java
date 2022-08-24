package cz.ethernal.JoinSystem;

import cz.ethernal.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Random;

public class WelcomeMessage extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent e) {

        if(Config.id_welcome_channel == null){
            return;
        }

        EmbedBuilder join = new EmbedBuilder();

        join.setTitle("__Nový člen!__ :wave:");
        join.setDescription("**Napojil/a se** » " + e.getMember().getAsMention());
        join.setThumbnail(e.getMember().getAvatarUrl());
        join.setColor(Color.YELLOW);

        e.getGuild().getTextChannelById(Config.id_welcome_channel).sendMessageEmbeds(join.build()).queue(message -> {

            Random rn = new Random();
            for(int i =0; i < 1; i++)
            {
                int answer = rn.nextInt(10) + 1;
                System.out.println(answer);

                if(answer == 1){
                    message.addReaction("⭐").queue();
                }else if(answer == 2){
                    message.addReaction("\uD83E\uDD73").queue();
                }else if(answer == 3){
                    message.addReaction("\uD83D\uDC4B").queue();
                }else if(answer == 4){
                    message.addReaction("\uD83D\uDC51").queue();
                }else if(answer == 5){
                    message.addReaction("\uD83D\uDD25").queue();
                }else if(answer == 6){
                    message.addReaction("\uD83C\uDFC5").queue();
                }else if(answer == 7){
                    message.addReaction("\uD83D\uDC8E").queue();
                }else if(answer == 8){
                    message.addReaction("\uD83E\uDE85").queue();
                }else if(answer == 9){
                    message.addReaction("\uD83C\uDF8A").queue();
                }else if(answer == 10){
                    message.addReaction("\uD83C\uDF89").queue();
                }


            }

        });





    }
}
