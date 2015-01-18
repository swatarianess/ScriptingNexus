package impl.BuyFlowerPots;

import curtains.Modules.Module;
import curtains.Modules.ModuleInfo;
import curtains.Resources.Areas;
import curtains.Resources.ItemIds;
import curtains.Resources.NpcIds;
import curtains.Resources.Vars;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;

import java.util.concurrent.Callable;

/**
 * Created by Swatarianess on 13/01/2015.
 * Package: impl
 * Project: Scripting Nexus
 */
@ModuleInfo(name = "MoveToRamseyShop")
public class MoveToRamseyShop extends Module<ClientContext> {

    public MoveToRamseyShop(ClientContext ctx) {
        super(ctx);
    }


    /**
     * Activate: What will activate this Module?
     */

    @Override
    public boolean activate() {
        return Areas.TAVERLY.contains(ctx.players.local().tile())
                && ctx.players.local().animation() == -1
                && ctx.backpack.select().id(ItemIds.POT_OF_FLOUR).count() == 0
                && !ctx.widgets.component(1265, 5).visible(); // Shop window
    }


    /**
     * Execute: What to execute?
     */
    @Override
    public void execute() {
        Vars.SHOP_ENTRANCE = new Tile(2886, 3443);
        System.out.println("Moving To Ramsey's Shop");

        Npc ramsey = ctx.npcs.select().id(NpcIds.RAMSEY).poll();
        ctx.camera.turnTo(ramsey.tile());
        ctx.camera.pitch(Random.nextInt(28,70));

        if (ctx.movement.reachable(ctx.players.local().tile(), ramsey.tile())) {
            ctx.movement.step(ramsey);
            ramsey.interact(false, "Trade", "Mess Sergeant Ramsey");
            Condition.wait(new Callable<Boolean>() {

                public Boolean call() throws Exception {
                    return ctx.widgets.component(1265, 5).visible();
                }
            }, 250, 20);
        } else {
            ctx.movement.step(Vars.SHOP_ENTRANCE);
        }
    }

}
