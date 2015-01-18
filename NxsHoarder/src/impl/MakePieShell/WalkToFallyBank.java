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

import java.util.concurrent.Callable;

/**
 * Created by Swatarianess on 14/01/2015.
 * Package: impl.MakePieShell
 * Project: Scripting Nexus
 */
@ModuleInfo(name = "WalkToFallyBank")
public class WalkToFallyBank extends Module<ClientContext> {

    public WalkToFallyBank(ClientContext ctx) {
        super(ctx);
    }


    /**
     * Activate: What will activate this Module?
     */
    @Override
    public boolean activate() {
        return Areas.FALADOR.contains(ctx.players.local().tile())
                && ctx.players.local().animation() == -1
                && (ctx.backpack.select().count() == 28
                || ctx.backpack.id(ItemIds.POT_OF_FLOUR).count() == 0)
                || (ctx.backpack.id(ItemIds.PASTRY_DOUGH).count() > 0)
                && !ctx.bank.opened();
    }


    /**
     * Execute: What to execute?
     */
    @Override
    public void execute() {
        //What is going to be executed?
        System.out.println("Walking to bank");

        GameObject bankBooth = ctx.objects.id(11758).poll();
        Component notice = ctx.widgets.component(220, 0);
        ctx.camera.turnTo(bankBooth.tile());
        Condition.sleep(1500);
        //ctx.movement.step(bankBooth.tile().derive(-1,1));

        System.out.println("Walking to bankBooth...");

        if (ctx.bank.inViewport()) {
            ctx.bank.open();
        } else {
            ctx.camera.turnTo(ctx.bank.nearest().tile());
            ctx.bank.open();
            System.out.println("Akownledged no pastry dough...");
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.players.local().animation() == -1;
                }
            }, 1000, 100);
        }

        if (ctx.backpack.id(ItemIds.PASTRY_DOUGH).count() > 0) {
            System.out.println("[DEBUG] Aknowledged pastry dough inside Inventory...");
            if (ctx.bank.inViewport()) {
                System.out.println("[DEBUG] Aknowledged can see bank...");
                ctx.bank.open();
            } else {
                ctx.camera.turnTo(ctx.bank.nearest().tile());
                ctx.camera.pitch(Random.nextInt(24, 34));
                ctx.bank.open();
                System.out.println("Found BankBooth...");
            }


        }
    }
}
