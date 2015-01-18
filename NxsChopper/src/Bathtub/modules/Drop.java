package Bathtub.modules;

import curtains.Modules.Module;
import curtains.Modules.ModuleInfo;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Hud;
import org.powerbot.script.rt6.Item;

/**
 * Created by Swatarianess on 03/01/2015.
 * Package: Bathtub.modules
 * Project: Scripting Nexus
 */
@ModuleInfo(name="Dropping the bass")
public class Drop extends Module<ClientContext> {

    private int logId = 1511;

    public Drop(ClientContext ctx){
        super(ctx);
    }


    /**
     * If the backpack contains 28 items, i.e is full, Module will execute.
     */
    @Override
    public boolean activate() {
        return ctx.backpack.select().count() == 28;
    }

    /**
     * If Backpack is not open, will be opened.
     * All items in inventory from top to bottom, select and drop them.
     */
    @Override
    public void execute() {
        if(!ctx.hud.opened(Hud.Window.BACKPACK))
            ctx.hud.open(Hud.Window.BACKPACK);
        for(Item i : ctx.backpack.id(logId))
        {
            i.interact("drop");
        }
    }
}
