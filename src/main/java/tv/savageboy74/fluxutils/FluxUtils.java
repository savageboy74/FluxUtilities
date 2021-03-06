package tv.savageboy74.fluxutils;

/*
 * FluxUtils.java
 * Copyright (C) 2015 Savage - github.com/savageboy74
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.MinecraftForge;
import tv.savageboy74.fluxutils.common.block.FluxBlocks;
import tv.savageboy74.fluxutils.common.handler.GuiHandler;
import tv.savageboy74.fluxutils.common.inventory.FluxRecipes;
import tv.savageboy74.fluxutils.common.creativetab.FluxCreativeTab;
import tv.savageboy74.fluxutils.common.handler.ConfigHandler;
import tv.savageboy74.fluxutils.common.item.FluxItems;
import tv.savageboy74.fluxutils.common.network.packet.PacketHandler;
import tv.savageboy74.fluxutils.common.proxy.IProxy;
import tv.savageboy74.fluxutils.common.tileentity.FluxTileEntities;
import tv.savageboy74.fluxutils.util.LogHelper;
import tv.savageboy74.fluxutils.util.Reference;
import tv.savageboy74.fluxutils.util.StringHelper;
import tv.savageboy74.fluxutils.util.UpdateChecker;

import java.io.IOException;

@Mod(modid = Reference.mod_id, name = Reference.mod_name, version = Reference.mc_version, dependencies = Reference.dependencies)
public class FluxUtils
{
    @Mod.Instance(Reference.mod_id)
    public static FluxUtils instance;

    @SidedProxy(clientSide = Reference.clientProxy, serverSide = Reference.serverProxy)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        FluxCreativeTab.init();
        FluxBlocks.register();
        FluxItems.register();

        ConfigHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigHandler());



        //Check For Updates
        //TODO Config option
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
        PacketHandler.registerPackets();
        try {
            LogHelper.info("Checking for updates...");
            UpdateChecker.checkForUpdates();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LogHelper.info("Pre-Initialization Completed.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        FluxTileEntities.register();
        FluxRecipes.register();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        proxy.initRendering();
        LogHelper.info("Initialization Completed.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if (Loader.isModLoaded("ThermalExpansion"))
        {
            FluxRecipes.registerThermalExpansionRecipes();
            LogHelper.info(StringHelper.localize("mod.thermalexpansion.found"));
        }

        else
        {
            LogHelper.error(StringHelper.localize("mod.thermalexpansion.missing"));
        }

        LogHelper.info("Post-Initialization Completed.");
    }

    @SubscribeEvent
    public void checkForUpdate(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (Reference.isOutdated)
        {
            String text = EnumChatFormatting.GOLD + "[" + Reference.mod_name + "] " + EnumChatFormatting.WHITE + "This version of " + EnumChatFormatting.GOLD  + Reference.mod_name + EnumChatFormatting.WHITE + " is" + EnumChatFormatting.DARK_RED + " outdated!" + EnumChatFormatting.WHITE + "     " + "Newest Version: " + EnumChatFormatting.GREEN + Reference.new_version;
            String downloadText = "Download";
            String downloadURL = "https://savageboy74.tv/mods/downloads/FluxUtilities-" + Reference.new_version + ".jar";
            event.player.addChatComponentMessage(new IChatComponent.Serializer().func_150699_a("[{\"text\":\"" + text + "\"}," + "{\"text\":\" " + EnumChatFormatting.WHITE + "[" + EnumChatFormatting.GREEN + downloadText + EnumChatFormatting.WHITE + "]\"," + "\"color\":\"green\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":" + "{\"text\":\"Click to download the latest version\",\"color\":\"yellow\"}}," + "\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + downloadURL + "\"}}]"));

        }
    }
}
