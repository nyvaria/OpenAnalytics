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
package net.nyvaria.openanalytics.bungee.client;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.nyvaria.openanalytics.bungee.OpenAnalyticsBungee;

import java.io.*;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Created by Paul Thompson on 23/02/2014.
 * @author Paul Thompson
 */
public class ClientConfig {
    private static final String DEFAULT_PLAYER_CONFIG_RESOURCE = "player.yml";
    private static final String ANONYMIZED_ID = "anonymized-id";
    private static final String OPT_OUT = "opt-out";

    private ProxiedPlayer player = null;
    private UUID anonymizedID = null;
    private String clientID = null;
    private Boolean optout = null;

    private Configuration playerConfig;

    //private FileConfiguration playerConfig;
    private File playerConfigFile;

    public ClientConfig(ProxiedPlayer player) {
        // Set the player
        this.player = player;

        // Load the config
        playerConfig = loadPlayerConfig();

        // Create attributes in the player config if they are missing
        if (playerConfig.getString(ANONYMIZED_ID).isEmpty() || (playerConfig.getString(ANONYMIZED_ID) == "0")) {
            playerConfig.set(ANONYMIZED_ID, UUID.randomUUID().toString());
            savePlayerConfig();
        }

        // And set the attributes
        this.anonymizedID = UUID.fromString(playerConfig.getString(ANONYMIZED_ID));
        this.clientID = anonymizedID.toString();
        this.optout = playerConfig.getBoolean(OPT_OUT);
    }

    public UUID getAnonymizedID() {
        return anonymizedID;
    }

    public String getClientID() {
        return clientID;
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
        return OpenAnalyticsBungee.getInstance().getDataFolder().getPath()
                + File.separator + "players"
                + File.separator + player.getName() + ".yml";
    }

    private Configuration loadPlayerConfig() {
        Configuration config = null;
        playerConfigFile = new File(getPlayerConfigPath());

        if (!playerConfigFile.getParentFile().exists()) {
            playerConfigFile.getParentFile().mkdirs();
        }

        if (playerConfigFile.isFile()) {
            // Attempt to load the player configuration file
            try {
                OpenAnalyticsBungee.getInstance().log(Level.FINE, "Loading player configuration file - %1$s", playerConfigFile.getName());
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(playerConfigFile);
            } catch (IOException e) {
                OpenAnalyticsBungee.getInstance().log(Level.WARNING, "Cannot read player configuration file - %1$s", playerConfigFile.getName());
                e.printStackTrace();
            }

        } else if (!playerConfigFile.exists()) {
            // Attempt to create a new player configuration file
            try {
                OpenAnalyticsBungee.getInstance().log(Level.INFO, "Player configuration file not found");
                OpenAnalyticsBungee.getInstance().log(Level.INFO, "Creating new player configuration file - %1$s", playerConfigFile.getName());

                // Create a default player config file
                playerConfigFile.createNewFile();
                InputStream in = OpenAnalyticsBungee.getInstance().getResourceAsStream(ClientConfig.DEFAULT_PLAYER_CONFIG_RESOURCE);
                OutputStream out = new FileOutputStream(playerConfigFile);
                ByteStreams.copy(in, out);

                // And then load it
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(playerConfigFile);

            } catch (IOException e) {
                OpenAnalyticsBungee.getInstance().log(Level.WARNING, "Cannot create new player configuration file - %1$s", playerConfigFile.getName());
                e.printStackTrace();
            }
        }

        return config;
    }

    public void savePlayerConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(playerConfig, playerConfigFile);
        } catch (IOException e) {
            OpenAnalyticsBungee.getInstance().log(Level.WARNING, "Cannot save player configuration file - %1$s", playerConfigFile.getName());
            e.printStackTrace();
        }
    }
}
