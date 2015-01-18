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

        if (ctx.movement.reachable(ctx.players.local().tile(), wydin.tile())) {
            ctx.movement.step(wydin);
            ctx.camera.angle(90 + Random.nextInt(-5, 5));
            wydin.interact(false, "Trade", "Wydin");
            Condition.wait(new Callable<Boolean>() {

                public Boolean call() throws Exception {
                    return ctx.widgets.component(1265, 5).visible();
                }
            }, 100, 20);
        } else {

            Condition.wait(new Callable<Boolean>() {

                public Boolean call() throws Exception {
                    return ctx.players.local().animation() == -1;
                }
            }, 200, 50);

            ctx.movement.step(Vars.DOORSTEP);

//			Helps with opening the door.
            ctx.camera.angle(90 + Random.nextInt(-5, 5));
            GameObject door = ctx.objects.select().id(40108).nearest().poll();
            if (!ctx.movement.reachable(ctx.players.local().tile(), wydin.tile())
                    && Vars.DOORSTEP.compareTo(door.tile()) == 0) {
                door.interact(false, "Open", "Door");
            }
        }
    }

}
