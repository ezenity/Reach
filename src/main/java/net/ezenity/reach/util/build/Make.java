package net.ezenity.reach.util.build;

import net.ezenity.reach.Main;
import net.ezenity.reach.configuration.Config;
import net.ezenity.reach.configuration.Lang;
import net.ezenity.reach.Fx.providers.Tools;
import net.ezenity.reach.Fx.providers.TreeSpawner;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * An abstract class for creating an item. This abstract class will gather information
 * from the configuration file and input the in its appropriate places to create the
 * desired outcome.
 *
 * @author Ezenity
 * @version 1.2.0
 * @since 1.0.0
 */
public abstract class Make extends Item {
    /**
     * Initialize a plugin instance. This is used for gather plugin configuration settings so that we may
     * generate a custom item.
     */
    private final Main plugin = Main.getInstance();

    /**
     * Get a custom item from the configuration file.
     * <p>
     * Just add in the item location exactly how it is inside the config.yml ({@link Config})
     * without the suffix declaration, when calling the getConfigItem() method. Usage can
     * be viewed in our "Provider" classes:
     * <ul>
     *     <li>{@link Main}</li>
     *     <li>{@link Tools}</li>
     *     <li>{@link TreeSpawner}</li>
     * </ul>
     *
     * @param configItemLocation gets the string location from the configuration file.
     * @return custom tool with prerequisites from the config file
     */
    @Override
    public ItemStack create(String configItemLocation) {
        String itemType = (String) plugin.getConfig().get("portal." + configItemLocation + ".type");
        org.bukkit.inventory.ItemStack itemStack = new org.bukkit.inventory.ItemStack(Objects.requireNonNull(Material.getMaterial(Objects.requireNonNull(itemType))));
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(Main.getReachLang().colorize(plugin.getConfig().getString("portal." + configItemLocation + ".title"))); // TODO: Revamp from static
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        List<String> itemLore = new ArrayList<>();
        for (String newLines : plugin.getConfig().getStringList("portal." + configItemLocation + ".lore")) {
            itemLore.add(ChatColor.translateAlternateColorCodes('&', newLines));
        }
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    /**
     * Get a boolean value from the configuration file and set a string value based
     * on boolean value. Once the boolean value is obtained the string value will
     * be set via parameter values 'trueString', 'falseString'.
     *
     * @param configItemLocation Gets a string. can be used to get a boolean value from a config file.
     * @param trueString Gets the string value for a boolean value if true
     * @param falseString  Gets the string value for a boolean value if false
     * @return string value based on the boolean value
     */
    @Override
    public String setConfigString(boolean configItemLocation, String trueString, String falseString) {
        boolean itemStatus = plugin.getConfig().getBoolean(String.valueOf(configItemLocation));
        return itemStatus ? trueString : falseString;
    }

    /**
     * This method will get the boolean value from the given configuration file.
     *
     * @param configItemLocation get the boolean from a config file.
     * @return boolean value from config file.
     */
    @Override
    public boolean getConfigBoolean(boolean configItemLocation) {
        return plugin.getConfig().getBoolean(String.valueOf(configItemLocation));
    }
}
