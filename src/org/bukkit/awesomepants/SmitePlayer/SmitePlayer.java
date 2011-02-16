package org.bukkit.awesomepants.SmitePlayer;

import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

/**
 * SmitePlayer for Bukkit
 *
 * @author LordMogra
 */
public class SmitePlayer extends JavaPlugin {
    private final SmitePlayerPlayerListener playerListener = new SmitePlayerPlayerListener(this);
    private final SmitePlayerBlockListener blockListener = new SmitePlayerBlockListener(this);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    public static final Logger log = Logger.getLogger("Minecraft"); // Get the Minecraft logger for, er, logging purposes.

    public SmitePlayer(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
        // TODO: Place any custom initialisation code here

        // NOTE: Event registration should be done in onEnable not here as all events are unregistered when a plugin is disabled
    }


    public void onEnable() {
        // TODO: Place any custom enable code here including the registration of any events

        // Register our events
        PluginManager pm = getServer().getPluginManager();
       

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }

    public void onDisable() {
        // TODO: Place any custom disable code here

        // NOTE: All registered events are automatically unregistered when a plugin is disabled

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!" );
    }


    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
    {
        //first make sure sender is a player, so far, console won't be allowed to smite
        //then check if sender has permissions
        if (sender instanceof Player &&
            this.hasPermissions((Player) sender))
        {
            //probably not necessary, but for the sake of cleanliness, we will sanitize our input.
            String commandString = command.getName().toLowerCase();

            //pick the function to run based on the command used.
            switch(commandString)
            {
                case "smite":
                    return this.killTargetPlayer(this.getServer().getPlayer(args[1]));
                    break;
                case "hurt":
                    return this.hurtTargetPlayer(this.getServer().getPlayer(args[1]), Integer.parseInt(args[2]));
                    break;
                case "smiteall":
                    for (Player player: getServer().getOnlinePlayers())
                    {
                        if (player.getName() != sender.getName())
                        {
                            this.killTargetPlayer(player);
                        }
                    }
                    break;
            }
        }
        else
        {
            return false;
        }
        
    }


    private boolean hurtTargetPlayer(Player targetPlayer, int amount)
    {
        if (targetPlayer != null)
        {
            targetPlayer.setHealth(targetPlayer.getHealth - amount);
            return true;
        }
        else
        {
            return false;
        }

    }

    private boolean killTargetPlayer(Player targetPlayer)
    {
        if (targetPlayer != null)
        {
            return this.hurtTargetPlayer(targetPlayer, targetPlayer.getHealth());
        }
        else
        {
            return false; 
        }
    }

    private boolean hasPermission(Player player)
    {
        //TODO: This'll become more complicated later.
        return player.isOp();
    }
}
