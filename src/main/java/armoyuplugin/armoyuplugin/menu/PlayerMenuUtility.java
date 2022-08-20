package armoyuplugin.armoyuplugin.menu;


import armoyuplugin.armoyuplugin.models.Note;
import me.kodysimpson.simpapi.menu.AbstractPlayerMenuUtility;
import org.bukkit.entity.Player;

public class PlayerMenuUtility extends AbstractPlayerMenuUtility {

    private Note noteToDelete;

    public PlayerMenuUtility(Player p) {
        super(p);
    }

    public Note getNoteToDelete() {
        return noteToDelete;
    }

    public void setNoteToDelete(Note noteToDelete) {
        this.noteToDelete = noteToDelete;
    }
}