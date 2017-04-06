package com.builtbroken.radio.content.message;

import com.builtbroken.mc.api.map.radio.IRadioWaveSender;
import com.builtbroken.mc.api.tile.access.IGuiTile;
import com.builtbroken.mc.core.network.IPacketIDReceiver;
import com.builtbroken.mc.core.network.packet.PacketTile;
import com.builtbroken.mc.core.network.packet.PacketType;
import com.builtbroken.mc.imp.transform.vector.Pos;
import com.builtbroken.mc.prefab.gui.ContainerDummy;
import com.builtbroken.mc.prefab.tile.Tile;
import com.builtbroken.radio.RadioMod;
import com.builtbroken.radio.content.TileRadioBase;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.LinkedList;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/20/2016.
 */
public class TileMessage extends TileRadioBase implements IGuiTile, IPacketIDReceiver
{
    LinkedList<String> chatLog = new LinkedList();

    boolean updateClient = false;

    public TileMessage()
    {
        super("messageBox");
    }

    @Override
    public void update()
    {
        super.update();
    }

    @Override
    protected boolean onPlayerRightClick(EntityPlayer player, int side, Pos hit)
    {
        if (!world().isRemote)
        {
            openGui(player, RadioMod.INSTANCE);
        }
        return true;
    }

    @Override
    public void doUpdateGuiUsers()
    {
        if (updateClient || ticks % 20 == 0) //Its a large packets so delay by a second
        {
            //TODO find a better way to update this without sending the entire list
            PacketTile packet = new PacketTile(this, 2, chatLog.size());
            for (String s : chatLog)
            {
                ByteBufUtils.writeUTF8String(packet.data(), s);
            }
            sendPacketToGuiUsers(packet);
        }
    }

    @Override
    public void receiveExternalRadioWave(String messageHeader, Object[] data)
    {
        if ("chatMessage".equals(messageHeader) && data.length >= 2)
        {
            updateClient = true;
            chatLog.add("" + data[0] + ": " + data[1]);
            //Remove oldest entry from the list
            if (chatLog.size() > 30)
            {
                chatLog.poll();
            }
        }
    }


    public void sendChatMessage(EntityPlayer player, String message)
    {
        chatLog.add(username + ":" + message);
        //Client
        if (world().isRemote)
        {
            sendPacketToServer(new PacketTile(this, 3, message));
        }
        else
        {
            for (TileEntity tile : connections.values())
            {
                //Send data threw first sender
                if (tile instanceof IRadioWaveSender)
                {
                    ((IRadioWaveSender) tile).sendRadioMessage(hz, "chatMessage", player.getCommandSenderName(), message);
                    break;
                }
            }
        }
    }


    @Override
    public boolean read(ByteBuf buf, int id, EntityPlayer player, PacketType type)
    {
        if (world().isRemote && id == 2)
        {
            chatLog.clear();
            int size = buf.readInt();
            for (int i = 0; i < size; i++)
            {
                chatLog.add(ByteBufUtils.readUTF8String(buf));
            }
            return true;
        }
        else if (!world().isRemote && id == 3)
        {
            sendChatMessage(player, ByteBufUtils.readUTF8String(buf));
            return true;
        }
        return false;
    }


    @Override
    public Tile newTile()
    {
        return new TileMessage();
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player)
    {
        return new ContainerDummy(player, this);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player)
    {
        return new GuiMessageTile(player, this);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        if (nbt.hasKey("chatLog"))
        {
            chatLog.clear();
            NBTTagCompound log = nbt.getCompoundTag("chatLog");
            int size = log.getInteger("size");
            for (int i = 0; i < size; i++)
            {
                chatLog.add(log.getString("s" + i));
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        if (chatLog.size() > 0)
        {
            NBTTagCompound log = new NBTTagCompound();
            log.setInteger("size", chatLog.size());
            for (int i = 0; i < chatLog.size(); i++)
            {
                log.setString("s" + i, chatLog.get(i));
            }
            nbt.setTag("chatLog", log);
        }
    }
}
