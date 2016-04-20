package com.builtbroken.radio.content;

import com.builtbroken.mc.api.map.radio.IRadioWaveExternalReceiver;
import com.builtbroken.mc.api.map.radio.IRadioWaveReceiver;
import com.builtbroken.mc.api.map.radio.IRadioWaveSender;
import com.builtbroken.mc.prefab.tile.Tile;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.HashMap;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/20/2016.
 */
public abstract class TileRadioBase extends Tile implements IRadioWaveExternalReceiver
{
    /** Map of connections per side */
    protected final HashMap<ForgeDirection, TileEntity> connections = new HashMap();

    /** Pass key used to confirm hidden messages */
    protected short passKey = 0;
    /** Frequency to send messages on, or receive */
    protected float hz = 0;

    /** Is the tile sending hidden messages */
    boolean inSecureMode = false;


    public TileRadioBase(String name)
    {
        super(name, Material.iron);
        this.hardness = 5f;
        this.resistance = 5f;
        this.firstTick();
    }

    @Override
    public void firstTick()
    {
        super.firstTick();
        updateConnections();
    }

    @Override
    public void update()
    {
        super.update();
        if (ticks % 80 == 0)
        {
            updateConnections();
        }
    }

    /**
     * Refreshes connection map and related data
     */
    protected void updateConnections()
    {
        connections.clear();
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
        {
            TileEntity tile = toPos().add(dir).getTileEntity(world());
            if (tile != null && !tile.isInvalid())
            {
                connections.put(dir, tile);
            }
        }
    }

    @Override
    public void receiveExternalRadioWave(float hz, IRadioWaveSender sender, IRadioWaveReceiver receiver, String messageHeader, Object[] data)
    {
        if (!world().isRemote && Math.abs(this.hz - hz) < .001 && (!inSecureMode || data.length >= 3 && data[2] instanceof Short && ((Short) data[2]) == passKey))
        {

        }
    }

    public void receiveExternalRadioWave(String messageHeader, Object[] data)
    {

    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        if (nbt.hasKey("secureMode"))
        {
            inSecureMode = nbt.getBoolean("secureMode");
        }
        if (nbt.hasKey("passCode"))
        {
            passKey = nbt.getShort("passCode");
        }
        if (nbt.hasKey("hz"))
        {
            hz = nbt.getFloat("hz");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        if (passKey != 0)
        {
            nbt.setShort("passCode", passKey);
        }
        nbt.setFloat("hz", hz);
        nbt.setBoolean("secureMode", inSecureMode);
    }
}
