package com.builtbroken.radio.content;

import com.builtbroken.mc.api.map.radio.IRadioWaveExternalReceiver;
import com.builtbroken.mc.api.map.radio.IRadioWaveReceiver;
import com.builtbroken.mc.api.map.radio.IRadioWaveSender;
import com.builtbroken.mc.prefab.tile.Tile;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.HashMap;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/20/2016.
 */
public abstract class TileRadioBase extends Tile implements IRadioWaveExternalReceiver
{
    protected final HashMap<ForgeDirection, TileEntity> connections = new HashMap();

    @Override
    public void update()
    {
        super.update();
        if (ticks % 80 == 0)
        {

        }
    }

    @Override
    public void receiveExternalRadioWave(float hz, IRadioWaveSender sender, IRadioWaveReceiver receiver, String messageHeader, Object[] data)
    {

    }
}
