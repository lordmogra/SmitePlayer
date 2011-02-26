package com.chrissquared.awesomepants.SmitePlayer;

// Java imports
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

// org.bukkit imports
import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.Server;

/**
 * SmitePlayer for Bukkit
 *
 * @author LordMogra
 */
public class SmitePlayer extends JavaPlugin {
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    public final PluginDescriptionFile pdfFile = this.getDescription();
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
       

        log.info(String.format("%s version %s is enabled!", pdfFile.getName(), pdfFile.getVersion()));
    }

    public void onDisable() {
        // TODO: Place any custom disable code here

        // NOTE: All registered events are automatically unregistered when a plugin is disabled

        log.info(String.format("%s version %s is disabled!", pdfFile.getName(), pdfFile.getVersion()));
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
            this.hasPermission((Player) sender))
        {
            //probably not necessary, but for the sake of cleanliness, we will sanitize our input.
            String commandString = command.getName().toLowerCase();
            String userName = ((Player)sender).getName();

            log.info(String.format("Command sent is '%s' by %s", commandString, userName));

            //pick the function to run based on the command used.
            if (commandString.equals( "smite"))
            {
                //validate args, first argument should be <player> and not an empty string.
                String targetName = "";

                if (args.length > 0)
                {
                    targetName = args[0];
                }

                if (targetName == "")
                {
                    return false;
                }

                log.info(String.format("%s is targeting %s with '%s'", userName, targetName, commandString)); 

                boolean smited = this.killTarget(this.getServer().getPlayer(targetName));
                if (smited)
                {
                    String message = String.format("%s has been smited by the almighty Zom!", targetName);
                    getServer().broadcastMessage(message);
                }
                return smited;
            }
            else if (commandString.equals("hurt"))
            {
                //validate args, first argument should be <player> and not an empty string.
                String targetName = "";

                if (args.length > 0)
                {
                    targetName = args[0];
                }

                if (targetName == "")
                {
                    return false;
                }

                //second argument should be amount of damage and defaults to 2.
                int amount = 2;

                if (args.length > 1)
                {
                    amount = Integer.parseInt(args[1]);
                }

                if (amount < 1)
                {
                    sender.sendMessage("[amount] should be more than 0!");
                }

                log.info(String.format("%s is targeting %s with '%s' for %d", userName, targetName, commandString, amount)); 

                boolean wasHurt = this.hurtTarget(getServer().getPlayer(targetName), amount);
                if (wasHurt)
                {
                    String message = String.format("Zom does not find favor with %s and has cursed them!", targetName);
                    getServer().broadcastMessage(message);
                }
                return wasHurt;
            }
            else if (commandString.equals("smiteall"))
            {
                log.info(String.format("%s is targeting everyone but themself with '%s'", userName, commandString)); 
                for (Player player: getServer().getOnlinePlayers())
                {
                    if (player.getName() != userName)
                    {
                        this.killTarget(player);
                    }
                }
                String message = String.format("Zom is angry at the heathens! All but %s have felt his wrath.", userName);
                getServer().broadcastMessage(message);
                return true;
            }
        }
        //else
        log.warning(String.format("Nothing happened for some reason, command was %s, args were %s", command.getName(), args));
        return false;
    }


    private boolean hurtTarget(LivingEntity target, int amount)
    {
        if (target != null)
        {
            target.setHealth(target.getHealth() - amount);
            return true;
        }
        else
        {
            log.warning("In method 'hurtTarget', argument 'target' cannot be null.");
            return false;
        }

    }

    private boolean killTarget(LivingEntity target)
    {
        if (target != null)
        {
            return this.hurtTarget(target, target.getHealth());
        }
        else
        {
            log.warning("In method 'killTarget', argument 'target' cannot be null.");
            return false; 
        }
    }

    private boolean hasPermission(Player player)
    {
        //TODO: This'll become more complicated later.
        boolean allowed = player.isOp();
        String logMsg = String.format("%s %s allowed to use %s.",
                                        player.getName(),
                                        (allowed) ? "is" : "is not",
                                        pdfFile.getName());
        log.info(logMsg);
        return allowed;
    }
}

