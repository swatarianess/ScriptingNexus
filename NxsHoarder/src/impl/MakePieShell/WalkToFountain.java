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


    /**
     * Activate: What will activate this Module?
     */
    @Override
    public boolean activate() {
        //When will this activate?
        Component mixingWindow = ctx.widgets.component(1371,0);
        Component mixingProgress = ctx.widgets.component(1251,0).component(0);

        return Areas.FALADOR.contains(ctx.players.local().tile())
                && ctx.players.local().animation() == -1
                && ctx.backpack.select().count() != 28
                && ctx.backpack.id(ItemIds.POT_OF_FLOUR).count() > 0
                && !mixingWindow.visible()
                && !mixingProgress.visible();

    }


    /**
     * Execute: What to execute?
     */
    @Override
    public void execute() {
        //Walking to Fountain

        final GameObject Waterpump = ctx.objects.select().id(11661).poll();
        Item potOfFlour = ctx.backpack.id(ItemIds.POT_OF_FLOUR).shuffle().poll();

        if(!Waterpump.inViewport()){
            ctx.camera.turnTo(Waterpump);
            ctx.camera.pitch(Random.nextInt(30, 55));
        }
        ctx.movement.step(Waterpump.tile().derive(-1,1));
        Condition.sleep(3000);
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.players.local().animation() == -1;
            }
        },1000,100);

        potOfFlour.interact("Use");

        //If the mixing window is not already pen

       // if(!ctx.objects.select().name("Waterpump").within(6.0).isEmpty()){
        while(!ctx.widgets.component(1371,0).visible()) {
            if (Waterpump.inViewport()) {
                Waterpump.interact(false, "Use", "Waterpump");
                Condition.sleep(2000);
            } else {
                ctx.camera.turnTo(Waterpump.tile());
                //  ctx.movement.step(Waterpump.tile());
                Waterpump.interact(false, "Use", "Waterpump");
                Condition.sleep(2000);
            }
        }
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.widgets.component(1371,0).visible();
                }
            },100,40);
      //  }
        System.out.println("Walked to waterpump");

    }

}
