/**
 * Copyright (c) 2013-2014
 * Paul Thompson <captbunzo@gmail.com> / Nyvaria <geeks@nyvaria.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *
 */
package net.nyvaria.openanalytics.bukkit.client;

import net.nyvaria.openanalytics.bukkit.OpenAnalytics;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

/**
 * @author Paul Thompson
 */
public class ClientConfig {
    private static final String ANONYMIZED_ID = "anonymized-id";
    private static final String OPT_OUT = "opt-out";

    private final OfflinePlayer offlinePlayer;
    private final UUID anonymizedID;
    private final String clientId;
    private boolean optout;

    private FileConfiguration playerConfig;
    private File playerConfigFile;

    public ClientConfig(Player player) {
        this((OfflinePlayer) player);
    }

    public ClientConfig(OfflinePlayer offlinePlayer) {
        // Set the player
        this.offlinePlayer = offlinePlayer;

        // Load the config
        playerConfig = loadPlayerConfig();
        boolean changed = false;

        // Create attributes in the player config if they are missing
        if (!playerConfig.contains(ANONYMIZED_ID)) {
            playerConfig.set(ANONYMIZED_ID, UUID.randomUUID().toString());
            changed = true;
        }

        if (!playerConfig.contains(OPT_OUT)) {
            playerConfig.set(OPT_OUT, false);
            changed = true;
        }

        if (changed) {
            savePlayerConfig();
        }

        // And set the attributes
        this.anonymizedID = UUID.fromString(playerConfig.getString(ANONYMIZED_ID));
        this.clientId = anonymizedID.toString();
        this.optout = playerConfig.getBoolean(OPT_OUT);
    }

    public UUID getAnonymizedID() {
        return anonymizedID;
    }

    public String getClientID() {
        return clientId;
    }

    public boolean isOptedIn() {
        return !optout;
    }

    public void setOptOut(boolean optout) {
        this.optout = optout;
        playerConfig.set(OPT_OUT, optout);
        savePlayerConfig();
    }

    // Private methods

    private String getPlayerConfigPath() {
        return OpenAnalytics.getInstance().getDataFolder().getPath()
                + File.separator + "players"
                + File.separator + offlinePlayer.getName() + ".yml";
    }

    private FileConfiguration loadPlayerConfig() {
        FileConfiguration config = new YamlConfiguration();
        playerConfigFile = new File(getPlayerConfigPath());

        if (playerConfigFile.isFile()) {
            // Attempt to load the player configuration file
            try {
                OpenAnalytics.getInstance().log(Level.FINE, "Loading player configuration file - %1$s", playerConfigFile.getName());
                config.load(playerConfigFile);

            } catch (IOException e) {
                OpenAnalytics.getInstance().log(Level.WARNING, "Cannot read player configuration file - %1$s", playerConfigFile.getName());
                e.printStackTrace();

            } catch (InvalidConfigurationException e) {
                OpenAnalytics.getInstance().log(Level.WARNING, "Invalid player configuration file - %1$s", playerConfigFile.getName());
                e.printStackTrace();
            }

        } else {
            // Attempt to create a new player configuration file
            try {
                OpenAnalytics.getInstance().log(Level.INFO, "Player configuration file not found");
                OpenAnalytics.getInstance().log(Level.INFO, "Creating new player configuration file - %1$s", playerConfigFile.getName());
                config.save(playerConfigFile);

            } catch (IOException e) {
                OpenAnalytics.getInstance().log(Level.WARNING, "Cannot create new player configuration file - %1$s", playerConfigFile.getName());
                e.printStackTrace();
            }
        }

        return config;
    }

    public void savePlayerConfig() {
        try {
            playerConfig.save(this.playerConfigFile);
        } catch (IOException e) {
            OpenAnalytics.getInstance().log(Level.WARNING, "Cannot save player configuration file - %1$s", playerConfigFile.getName());
            e.printStackTrace();
        }
    }
}
