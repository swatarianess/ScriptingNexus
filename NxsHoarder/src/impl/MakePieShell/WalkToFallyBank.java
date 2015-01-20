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
                && !ctx.bank.opened()
                && (ctx.backpack.select().count() == 28
                || ctx.backpack.id(ItemIds.POT_OF_FLOUR).count() == 0)
                || (ctx.backpack.id(ItemIds.PASTRY_DOUGH).count() > 0);
    }


    /**
     * Execute: What to execute?
     */
    @Override
    public void execute() {
        //What is going to be executed?
        GameObject bankBooth = ctx.objects.id(11758).poll();
        Component notice = ctx.widgets.component(220, 0);

        System.out.println("Walking to bank");

        ctx.camera.turnTo(bankBooth.tile());
        Condition.sleep(Random.getDelay());
        ctx.camera.pitch(Random.nextInt(24, 34));

        if (ctx.bank.inViewport() && ctx.backpack.id(ItemIds.PASTRY_DOUGH).count() == 0) {
            ctx.bank.open();
            System.out.println("[DEBUG] Acknowledged: No dough, can see bank...");
        } else {
            ctx.bank.open();
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.players.local().inMotion();
                }
            }, 200, 30);
        }

        if (ctx.backpack.id(ItemIds.PASTRY_DOUGH).count() > 0) {
            if (ctx.bank.inViewport()) {
                ctx.bank.open();
                System.out.println("[DEBUG] Acknowledged:Have dough and can see bank ");
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return !ctx.players.local().inMotion();
                    }
                },200,30);
            } else {
                ctx.camera.turnTo(ctx.bank.nearest().tile());
                ctx.camera.pitch(Random.nextInt(24, 34));
                ctx.bank.open();
                System.out.println("Found BankBooth...");
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return !ctx.players.local().inMotion();
                    }
                },200,30);
            }


        }
    }
}
