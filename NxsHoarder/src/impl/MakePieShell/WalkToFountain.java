package impl.MakePieShell;

import curtains.Modules.Module;
import curtains.Modules.ModuleInfo;
import curtains.Resources.Areas;
import curtains.Resources.ItemIds;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Item;

import java.util.concurrent.Callable;

/**
 * Created by Swatarianess on 14/01/2015.
 * Package: impl.MakePieShell
 * Project: Scripting Nexus
 */
@ModuleInfo(name = "WalkToFountain")
public class WalkToFountain extends Module<ClientContext> {

    public WalkToFountain(ClientContext ctx) {
        super(ctx);
    }
    final GameObject Waterpump = ctx.objects.select().id(11661).poll();



    /**
     * Activate: What will activate this Module?
     */
    @Override
    public boolean activate() {
        //When will this activate?
        Component mixingWindow = ctx.widgets.component(1371, 0);
        Component mixingProgress = ctx.widgets.component(1251, 0).component(0);


        return Areas.FALADOR.contains(ctx.players.local().tile())
                && ctx.players.local().animation() == -1
                && ctx.backpack.select().count() == 14
                && ctx.backpack.id(ItemIds.POT_OF_FLOUR).count() > 0
                && Waterpump.tile().distanceTo(ctx.players.local().tile()) > 7
                && !mixingWindow.visible()
                && !mixingProgress.visible();

    }


    /**
     * Execute: What to execute?
     */
    @Override
    public void execute() {
        //Walking to Fountain

        Item potOfFlour = ctx.backpack.id(ItemIds.POT_OF_FLOUR).shuffle().poll();

        if(!Waterpump.inViewport()){
            ctx.camera.turnTo(Waterpump);
            ctx.camera.pitch(Random.nextInt(30, 55));
        }

        ctx.movement.step(Waterpump.tile().derive(-1, 1));
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().inMotion();
            }
        },200,30);

        potOfFlour.interact("Use");
        Condition.sleep(Random.nextInt(2000,3500));
        //If the mixing window is not already open

       // if(!ctx.objects.select().name("Waterpump").within(6.0).isEmpty()){
        while(!ctx.widgets.component(1371,0).visible()) {
            if (!ctx.players.local().inMotion()) {
                if (Waterpump.inViewport()) {
                    Waterpump.interact(false, "Use", "Waterpump");
                    Condition.sleep(Random.nextInt(2500, 4000));
                    break;
                } else {
                    ctx.camera.turnTo(Waterpump.tile());
                    //  ctx.movement.step(Waterpump.tile());
                    Waterpump.interact(false, "Use", "Waterpump");
                    Condition.sleep(Random.nextInt(2500, 4000));
                    break;
                }
            } else {
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return !ctx.players.local().inMotion();
                    }
                }, 150, 40);
            }
        }
    }

}
