package com.builtbroken.radio.content.message;

import com.builtbroken.mc.prefab.gui.ContainerDummy;
import com.builtbroken.mc.prefab.gui.GuiContainerBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/20/2016.
 */
public class GuiMessageTile extends GuiContainerBase
{
    protected final EntityPlayer player;
    protected final TileMessage tile;

    GuiTextField inputField;

    public GuiMessageTile(EntityPlayer player, TileMessage tile)
    {
        super(new ContainerDummy(player, tile));
        this.tile = tile;
        this.player = player;
        initGui();
    }

    @Override
    public void initGui()
    {
        super.initGui();
        inputField = new GuiTextField(Minecraft.getMinecraft().fontRenderer, guiLeft, guiTop, 100, 20);
        buttonList.add(new GuiButton(0, guiLeft + 105, guiTop, 50, 20, "Send"));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        //TODO render chat messages above input field
    }
}
