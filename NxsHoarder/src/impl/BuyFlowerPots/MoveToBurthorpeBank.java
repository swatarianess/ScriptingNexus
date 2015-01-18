package impl.BuyFlowerPots;

import curtains.Modules.Module;
import curtains.Modules.ModuleInfo;
import curtains.Resources.Areas;
import curtains.Resources.ItemIds;
import curtains.Resources.Vars;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;

import java.util.concurrent.Callable;

/**
 * Created by Swatarianess on 13/01/2015.
 * Package: impl
 * Project: Scripting Nexus
 */
@ModuleInfo(name = "MoveToBurthorpeBank")
public class MoveToBurthorpeBank extends Module<ClientContext> {

    public MoveToBurthorpeBank(ClientContext ctx) {
        super(ctx);
    }


    /**
     * Activate: What will activate this Module?
     */
    @Override
    public boolean activate() {
        return Areas.BURTHORPE.contains(ctx.players.local().tile())
                && ctx.players.local().animation() == -1
                && ctx.backpack.select().id(ItemIds.POT_OF_FLOUR).count() > 0
                && !ctx.bank.opened();
    }


    /**
     * Execute: What to execute?
     */
    @Override
    public void execute() {
        System.out.println("Moving to Burthorpe Bank");
        Vars.MY_ANIMATION = -1;

        if (ctx.bank.inViewport()) {
         //   Condition.sleep(Random.nextInt(3000,4000));
            ctx.camera.turnTo(ctx.bank.nearest());
            ctx.camera.pitch(Random.nextInt(24, 40));

            Condition.wait(new Callable<Boolean>() {
                    public Boolean call() throws Exception {
                        return ctx.players.local().animation()==-1;}
                }, 500, 50);

            ctx.bank.open();
        } else {
            ctx.camera.turnTo(ctx.bank.nearest());
            ctx.camera.pitch(Random.nextInt(24, 44));
            ctx.movement.step(ctx.bank.nearest());

        }
    }

}
