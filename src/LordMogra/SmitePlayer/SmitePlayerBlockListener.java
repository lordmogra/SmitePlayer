package LordMogra.SmitePlayer;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.Material;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * SmitePlayer block listener
 * @author LordMogra
 */
public class SmitePlayerBlockListener extends BlockListener {
    private final SmitePlayer plugin;

    public SmitePlayerBlockListener(final SmitePlayer plugin) {
        this.plugin = plugin;
    }

    //put all Block related code here
}
