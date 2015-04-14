package tv.savageboy74.fluxutils.common.handler;

/*
 * GuiHandler.java
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

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import tv.savageboy74.fluxutils.client.blocks.machines.solar.ContainerSolarPanel;
import tv.savageboy74.fluxutils.client.blocks.machines.solar.TileEntitySolarPanel;
import tv.savageboy74.fluxutils.common.inventory.gui.GuiSolarPanel;
import tv.savageboy74.fluxutils.util.GUI;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID == GUI.IDs.SOLAR_PANEL.ordinal())
        {
            TileEntitySolarPanel tileSolarPanel = (TileEntitySolarPanel) world.getTileEntity(x, y, z);
            return new ContainerSolarPanel(player, tileSolarPanel);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID == GUI.IDs.SOLAR_PANEL.ordinal())
        {
            TileEntitySolarPanel tileSolarPanel = (TileEntitySolarPanel) world.getTileEntity(x, y, z);
            return new GuiSolarPanel(player, tileSolarPanel);
        }

        return null;
    }
}