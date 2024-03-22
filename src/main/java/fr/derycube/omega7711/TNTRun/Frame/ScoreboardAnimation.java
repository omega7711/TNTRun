package fr.derycube.omega7711.TNTRun.Frame;

import lombok.*;
import org.bukkit.*;

public class ScoreboardAnimation {
    public static class AddressAnimation {
        private final String ip;
        private int ipCharIndex;
        private int cooldown;

        public AddressAnimation(String ip) {
            this.ip = ip;
        }

        public String getColorAddress() {
            if (cooldown > 0) {
                cooldown--;
                return ChatColor.GOLD + ip;
            }

            StringBuilder formattedIp = new StringBuilder();

            if (ipCharIndex > 0) {
                formattedIp.append(ip, 0, ipCharIndex - 1);
                formattedIp.append(ChatColor.YELLOW).append(ip.charAt(ipCharIndex - 1));
            } else {
                formattedIp.append(ip, 0, ipCharIndex);
            }

            formattedIp.append(ChatColor.WHITE).append(ip.charAt(ipCharIndex));

            if (ipCharIndex + 1 < ip.length()) {
                formattedIp.append(ip.charAt(ipCharIndex + 1));

                if (ipCharIndex + 2 < ip.length())
                    formattedIp.append(ChatColor.GOLD).append(ip.substring(ipCharIndex + 2));

                ipCharIndex++;
            } else {
                ipCharIndex = 0;
                cooldown = 30;
            }

            return ChatColor.GOLD + formattedIp.toString();
        }
    }

    @Setter
    @Getter
    @RequiredArgsConstructor
    public static class TitleAnimation {
        private int titleCharIndex;
        private int cooldown;
        private boolean pause;
        private final String title;

        public String getColorTitle() {
            if(pause) {
                pause = false;
                return ChatColor.DARK_RED + ChatColor.BOLD.toString() + title;
            }
            if (cooldown > 0) {
                cooldown--;
                return ChatColor.DARK_RED + ChatColor.BOLD.toString() + title;
            }

            StringBuilder formattedTitle = new StringBuilder();

            if (titleCharIndex > 0) {
                formattedTitle.append(title, 0, titleCharIndex - 1);
                formattedTitle.append(ChatColor.RED).append(ChatColor.BOLD).append(title.charAt(titleCharIndex - 1));
            } else {
                formattedTitle.append(title, 0, titleCharIndex);
            }

            formattedTitle.append(ChatColor.WHITE).append(ChatColor.BOLD).append(title.charAt(titleCharIndex));

            if (titleCharIndex + 1 < title.length()) {
                formattedTitle.append(title.charAt(titleCharIndex + 1));

                if (titleCharIndex + 2 < title.length())
                    formattedTitle.append(ChatColor.DARK_RED).append(ChatColor.BOLD).append(title.substring(titleCharIndex + 2));

                titleCharIndex++;
            } else {
                titleCharIndex = 0;
                cooldown = 50;
            }

            pause = true;

            return ChatColor.DARK_RED + ChatColor.BOLD.toString() + formattedTitle.toString();
        }
    }
}