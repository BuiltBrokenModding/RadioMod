package com.builtbroken.radio.content.message;

import com.builtbroken.mc.core.References;
import com.builtbroken.mc.prefab.gui.ContainerDummy;
import com.builtbroken.mc.prefab.gui.GuiContainerBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

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
        this.baseTexture = References.GUI__MC_EMPTY_FILE;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        inputField = new GuiTextField(Minecraft.getMinecraft().fontRenderer, guiLeft + 10, guiTop + 138, 100, 20);
        fields.add(inputField);
        buttonList.add(new GuiButton(0, guiLeft + 115, guiTop + 138, 50, 20, "Send"));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        //TODO render chat messages above input field
        if (tile.chatLog.size() > 0)
        {
            //Can only fit 8 messages on the GUI at a time
            int y = 0;
            for (int i = Math.max(tile.chatLog.size() - 9, 0); i < tile.chatLog.size(); i++)
            {
                drawString(tile.chatLog.get(i), 10, y * 15 + 6, Color.BLACK);
                y++;
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        super.actionPerformed(button);
        if (button.id == 0 && inputField.getText() != null && !inputField.getText().isEmpty())
        {
            tile.sendChatMessage(player, "" + inputField.getText());
        }
    }
}
