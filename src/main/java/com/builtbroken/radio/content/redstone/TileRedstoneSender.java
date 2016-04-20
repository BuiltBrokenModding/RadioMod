package com.builtbroken.radio.content.redstone;

import com.builtbroken.mc.prefab.tile.Tile;
import com.builtbroken.radio.content.TileRadioBase;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/20/2016.
 */
public class TileRedstoneSender extends TileRadioBase
{
    public TileRedstoneSender()
    {
        super("redstoneSender");
    }

    @Override
    public Tile newTile()
    {
        return new TileRedstoneSender();
    }
}
