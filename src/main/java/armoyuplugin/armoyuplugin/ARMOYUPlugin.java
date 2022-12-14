package armoyuplugin.armoyuplugin;

import armoyuplugin.armoyuplugin.commands.CreateNoteCommand;
import armoyuplugin.armoyuplugin.commands.NoteMenuCommand;
import armoyuplugin.armoyuplugin.menu.PlayerMenuUtility;
import armoyuplugin.armoyuplugin.utils.JsonUtility;
import armoyuplugin.armoyuplugin.utils.NoteStorageUtility;
import me.kodysimpson.simpapi.command.CommandList;
import me.kodysimpson.simpapi.command.CommandManager;
import me.kodysimpson.simpapi.command.SubCommand;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;

public final class ARMOYUPlugin extends JavaPlugin {

        private static ARMOYUPlugin plugin;
    @Override
    public void onEnable() {
        System.out.println("[ARMOYU] ----Aktif----");
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
        plugin = this;
        Komutlar komutlar = new Komutlar();
        getCommand("giris").setExecutor(komutlar);

        try {
            CommandManager.createCoreCommand(this, "note", "Create and list notes", "/note", new CommandList() {
                @Override
                public void displayCommandList(Player p, List<SubCommand> subCommandList) {
                    p.sendMessage("-----------------ARMOYU BOT-----------------");
                    for (SubCommand subcommand : subCommandList){
                        p.sendMessage(subcommand.getSyntax() + " - " + subcommand.getDescription());
                    }
                    p.sendMessage("-----------------ARMOYU BOT-----------------");
                }
            }, CreateNoteCommand.class, NoteMenuCommand.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        MenuManager.setup(getServer(), this, PlayerMenuUtility.class);

//        try {NoteStorageUtility.loadNotes();} catch (IOException e) {e.printStackTrace(); }
//        try { JsonUtility.loadNotes(); } catch (IOException e) {    e.printStackTrace(); }

        try { JsonUtility.loadNotesxyz(); } catch (IOException e) {    e.printStackTrace(); }
            
            JsonUtility.updatereload();
            try {
                JsonUtility.saveNotesxyz();
            }catch (Exception ERR){
                System.out.println("[ARMOYU] Json Dosyas??ndaki Verileri G??ncellenmedi");
            }

    }
    @Override
    public void onDisable() {
        System.out.println("[ARMOYU] ----Devre D??????----");
    }

    public static ARMOYUPlugin getPlugin(){
        return plugin;
    }
}
