package cz.ethernal.Commands.Moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class Private extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {

        if(e.getInteraction().getName().equalsIgnoreCase("private")){

            ArrayList<String> done = new ArrayList<String>();

            done.clear();

            if(e.getInteraction().getMember().getIdLong() != 415136736717438976L){
                return;
            }

            EmbedBuilder all = new EmbedBuilder();
            all.setColor(Color.YELLOW);
            all.setTitle(":evergreen_tree: OBŘÍ AKTUALIZACE SURVIVALU | 21.08 2022");
            all.setDescription("Zdravím,\n\ndne **26.08 18:00** proběhne **údržba celého serveru**, důvodem je **obří\n Survival update**!\n" +
                    "Více informací můžeš nalézt na našem **novém** TikTok **videu**\n https://vm.tiktok.com/ZMNnEdKCL/\n\nHezký zbytek dne,\n" +
                    "tým Ethernal.cz");
            all.setImage("https://media.discordapp.net/attachments/965606454885253180/1010979245306155008/unknown.png?width=1708&height=897");

            e.getInteraction().getChannel().sendMessageEmbeds(all.build()).queue();

            for(Member members : e.getGuild().getMembers()) {
                done.add(members.getNickname());

                if(done.contains(members.getNickname())){
                    continue;
                }

                members.getUser().openPrivateChannel().queue(channel -> channel.sendMessageEmbeds(all.build()).queue());

            }

        }


    }
}
