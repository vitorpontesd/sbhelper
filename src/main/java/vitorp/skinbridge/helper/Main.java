/*******************************************************************************
 * Copyright © 2024 - Vitor Pontes
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 ******************************************************************************/

package vitorp.skinbridge.helper;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vitorp.skinbridge.helper.ModVars;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@Mod(modid = ModVars.MODID, version = ModVars.MODVERSION, name = ModVars.MODNAME, acceptedMinecraftVersions = "[1.7.10]")
public class Main {

    public static final Logger LOG = LogManager.getLogger(ModVars.MODID);
    public static final String USERNAME = Minecraft.getMinecraft().getSession().getUsername();
    public static final String skinServerURL = "https://raw.githubusercontent.com/vitorpontesd/classicsmp/data/skinserver/";
    public static final String SkinBridgeURL = "http://152.70.214.65/";


    public static void downloadSkin(String playername, File mcfolder) {

        String PNGPlayerName = playername + ".png";
        Main.LOG.info(PNGPlayerName);

        File targetSkinDir = new File(mcfolder + File.separator + "cachedImages" + File.separator  +"skins" + File.separator);
        File targetSkinFile = new File(targetSkinDir, PNGPlayerName);
        targetSkinFile.getParentFile().mkdirs();

        File targetCapeDir = new File(mcfolder + File.separator + "cachedImages" + File.separator + "capes" + File.separator);
        File targetCapeFile = new File(targetCapeDir, PNGPlayerName);
        targetCapeFile.getParentFile().mkdirs();

        // Skin via MCHeads
        try {
            Main.LOG.info("Baixando skin do jogador" + playername + "via MCHeads");
            URL skinURL = new URL("https://mc-heads.net/skin/" + PNGPlayerName);
            ReadableByteChannel rbc = Channels.newChannel(skinURL.openStream());
            FileOutputStream fos1 = new FileOutputStream(targetSkinFile);
            fos1.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            Main.LOG.info("Download da skin via MCHeads completo");

        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            Main.LOG.info("Download da skin via MCHeads falhou!");
        }

        // Capa via OF
        try {
            Main.LOG.info("Baixando a capa do jogador " + playername + "via OptiFine");
            URL skinURL = new URL("https://optifine.net/capes/" + PNGPlayerName);
            ReadableByteChannel rbc = Channels.newChannel(skinURL.openStream());
            FileOutputStream fos2 = new FileOutputStream(targetCapeFile);
            fos2.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            Main.LOG.info("Download da capa via Optifine completo");

        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            Main.LOG.info("Download da capa via Optifine falhou!");
        }
        // Skin via SB
        try {
            Main.LOG.info("Baixando a skin do jogador" + playername + "via SkinBridge");
            URL skinURL = new URL(SkinBridgeURL + "skin/" + PNGPlayerName);
            ReadableByteChannel rbc = Channels.newChannel(skinURL.openStream());
            FileOutputStream fos3 = new FileOutputStream(targetSkinFile);
            fos3.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            Main.LOG.info("Download da capa via SkinBridge completo");

        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            Main.LOG.info("Download da skin via SkinBridge falhou!");
        }

        // Capa via SB
        try {
            File targetDir = new File(mcfolder + File.separator + "skins" + File.separator);
            File targetFile = new File(targetDir, PNGPlayerName);

            Main.LOG.info("Baixando a capa do jogador " + playername + "via SkinBridge");
            URL skinURL = new URL(SkinBridgeURL + "cape/" + PNGPlayerName);
            ReadableByteChannel rbc = Channels.newChannel(skinURL.openStream());
            FileOutputStream fos4 = new FileOutputStream(targetCapeFile);
            fos4.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            Main.LOG.info("Download da capa via SkinBridge completo");

        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            Main.LOG.info("Download da capa via SkinBridge falhou!");
        }

    }

    @Mod.EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        File MCFolder = event.getModConfigurationDirectory().getParentFile();
        Main.LOG.info("Running ClassicSMP SkinHelper version " + ModVars.MODVERSION);
        Main.downloadSkin(Main.USERNAME, MCFolder);
    }
}
