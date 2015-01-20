package impl.BuyFlowerPots;

import curtains.Modules.Module;
import curtains.Modules.ModuleInfo;
import curtains.Resources.Areas;
import curtains.Resources.ItemIds;
import curtains.Resources.NpcIds;
import curtains.Resources.Vars;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Npc;

import java.util.concurrent.Callable;

/**
 * Created by Swatarianess on 13/01/2015.
 * Package: impl
 * Project: Scripting Nexus
 */
@ModuleInfo(name = "MoveToWydinShop")
public class MoveToWydinShop extends Module<ClientContext> {


    public MoveToWydinShop(ClientContext ctx) {
        super(ctx);
    }


    /**
     * Activate: What will activate this Module?
     */
    @Override
    public boolean activate() {
        return Areas.PORT_SARIM.contains(ctx.players.local().tile())
                && ctx.players.local().animation() == -1
                && ctx.backpack.select().id(ItemIds.POT_OF_FLOUR).count() == 0
                && !ctx.widgets.component(1265, 5).visible();
    }


    /**
     * Execute: What to execute?
     */
    @Override
    public void execute() {
        System.out.println("Moving To Wydin's Shop");

        Npc wydin = ctx.npcs.select().id(NpcIds.WYDIN).poll();
        GameObject door = ctx.objects.select().id(40108).nearest().poll();

        if (!ctx.movement.reachable(ctx.players.local().tile(), wydin.tile())
                && Vars.DOORSTEP.compareTo(door.tile()) == 0) {
            ctx.camera.turnTo(door.tile());
            door.interact(false, "Open", "Door");
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.players.local().animation() == -1;
                }
            },500,25);
        } else {

            if(Vars.DOORSTEP.compareTo(door.tile()) != 0){
                ctx.camera.turnTo(wydin.tile());
                ctx.camera.pitch(Random.nextInt(27, 43));
                wydin.interact(false, "Trade", "Wydin");
                Condition.sleep(500);
                ctx.camera.turnTo(wydin.tile());
                Condition.wait(new Callable<Boolean>() {
                    public Boolean call() throws Exception {
                        return ctx.widgets.component(1265, 5).visible();
                    }
                }, 200, 50);
            }

        }

        /* else {
            Condition.wait(new Callable<Boolean>() {

                public Boolean call() throws Exception {
                    return ctx.players.local().animation() == -1;
                }
            }, 200, 50);
            ctx.movement.step(Vars.DOORSTEP);
        }
        */

    }

}
