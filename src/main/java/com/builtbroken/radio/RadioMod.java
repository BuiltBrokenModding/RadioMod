package com.builtbroken.radio;

import com.builtbroken.mc.core.registry.ModManager;
import com.builtbroken.mc.lib.mod.AbstractMod;
import com.builtbroken.mc.lib.mod.AbstractProxy;
import com.builtbroken.mc.lib.mod.ModCreativeTab;
import com.builtbroken.radio.content.message.TileMessage;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

/**
 * Simple mod to test the radio system in voltz engine
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 4/20/2016.
 */
@Mod(modid = "veradiomod", name = "VE Radio Mod", version = "@MAJOR@.@MINOR@.@REVIS@.@BUILD@", dependencies = "required-after:VoltzEngine;after:OpenComputers")
public class RadioMod extends AbstractMod
{
    @Mod.Instance("veradiomod")
    public static RadioMod INSTANCE;

    @SidedProxy(clientSide = "com.builtbroken.radio.ClientProxy", serverSide = "com.builtbroken.radio.ServerProxy")
    public static CommonProxy proxy;

    public Block blockAntenna; //Antenna for sending and receivering data
    public Block blockMessage; //Chat box
    public Block blockRedstoneReceiver; //Receives message data, acts on data to produce redstone, replaced by OC version
    public Block blockRedstoneSender; //Sends data based on redstone, replaced by OC version

    public RadioMod()
    {
        super("veradiomod");
        manager.defaultTab = new ModCreativeTab("radiomod");
    }

    @Override
    protected void loadBlocks(ModManager manager)
    {
        blockMessage = manager.newBlock(TileMessage.class);
        ((ModCreativeTab)manager.defaultTab).itemStack = new ItemStack(blockMessage);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }

    @Override
    public AbstractProxy getProxy()
    {
        return proxy;
    }
}
