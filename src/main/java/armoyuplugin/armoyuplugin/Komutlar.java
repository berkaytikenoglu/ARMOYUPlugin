package armoyuplugin.armoyuplugin;

import armoyuplugin.armoyuplugin.models.Players;
import armoyuplugin.armoyuplugin.utils.JsonUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Komutlar  implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player oyuncu = (Player) sender;


        if (cmd.getName().equalsIgnoreCase("heal")) {
            double maxHealth = oyuncu.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
            oyuncu.setHealth(maxHealth);
        }

        if (cmd.getName().equalsIgnoreCase("feed")) {
            oyuncu.setFoodLevel(20);
        }

        if (cmd.getName().equalsIgnoreCase("giris")) {

            if (args.length != 1) {
                oyuncu.sendMessage(ChatColor.RED +"[ARMOYU] " +ChatColor.YELLOW + "Hatalı Kullanım Yaptınız");
            }else{

                try {
                    String url = "https://aramizdakioyuncu.com/botlar/c99e178d83cdfea3c167bc1d15f9b47ff8f80145/"+oyuncu.getDisplayName()+"/"+args[0]+"/0/0/0/";
                    URL obj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                    int responseCode = con.getResponseCode();

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null){
                        response.append(inputLine);
                    }
                    in.close();
                    // System.out.println(response);
                    int kontrol = response.charAt(12);
                    if (kontrol == 49){
                        JsonUtility.updateNote(oyuncu.getDisplayName(),true);
                        try {
                            JsonUtility.saveNotes();
                            System.out.println("[ARMOYU] "+"Kaydettik");
                            try { JsonUtility.loadNotes(); } catch (IOException err) {    err.printStackTrace();   }
                            List<Players> findAllNotes = JsonUtility.findAllNotes();
                            for (int i = 0; i < findAllNotes.size(); i++) {
                                Players oyuncucek = findAllNotes.get(i);
                                oyuncu.teleport(new Location(Bukkit.getWorld(oyuncucek.getLocation()),oyuncucek.getX(),oyuncucek.getY(),oyuncucek.getZ()));
                                    }
                        } catch (IOException ERR) {
                            System.out.println("[ARMOYU] "+"SAVING NOTES FAILED AAAAAAH!!!!");
                            ERR.printStackTrace();
                        }
                        oyuncu.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.GREEN + "Giriş Başarılı");

                    }else {
                        oyuncu.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.YELLOW + "Hatalı GİRİŞ");

                    }
                }catch (Exception e){
                    System.out.println(ChatColor.RED +"[ARMOYU] " +"Sunucu Bağlanısı Kurulamadı");
                    oyuncu.sendMessage(ChatColor.RED +"[ARMOYU] " + ChatColor.YELLOW + "Sunucu ile Bağlantı Kurulamadı");
                }
            }
            return true;
        }
        return true;

    }
}