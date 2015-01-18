package impl.MakePieShell;

import curtains.Modules.Module;
import curtains.Modules.ModuleInfo;
import curtains.Resources.Areas;
import curtains.Resources.ItemIds;
import curtains.Resources.Vars;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;

import java.util.concurrent.Callable;

/**
 * Created by Swatarianess on 14/01/2015.
 * Package: impl.MakePieShell
 * Project: Scripting Nexus
 */
@ModuleInfo(name = "MakePastry")
public class MakePastry extends Module<ClientContext> {

    public MakePastry(ClientContext ctx) {
        super(ctx);
    }


    /**
     * Activate: What will activate this Module?
     */
    @Override
    public boolean activate() {
        //When will this activate?
        return Areas.FALADOR.contains(ctx.players.local().tile())
                || ctx.widgets.component(1371,0).visible()
                || !ctx.objects.select().name("Waterpump").within(6.0).isEmpty();
    }


    /**
     * Execute: What to execute?
     */
    @Override
    public void execute() {
        //"Making dough"
        Component pastryDough = ctx.widgets.component(1371,44).component(5);
        Component mix = ctx.widgets.component(1370, 38);

   /*     if(pastryDough.textureId() != 15201) {
            ctx.widgets.component(1371, 44).component(4).click();
        }
  */
        Condition.sleep(Random.nextInt(300,600));
        ctx.input.send("{VK_SPACE}");

        ctx.backpack.select().id(ItemIds.PASTRY_DOUGH);
        int doughCountBefore = ctx.backpack.count();

        Condition.sleep(Random.nextInt(500,1500));
        Condition.wait(new Callable<Boolean>() {
                public Boolean call() throws Exception {
                    System.out.println("Within Loop...");
                    return !ctx.widgets.component(1251, 0).component(0).visible();
                }
            }, 200, 10);

            ctx.backpack.select().id(ItemIds.PASTRY_DOUGH);
            Vars.PASTRY_DOUGH_MIXED += ctx.backpack.count() - doughCountBefore;
        System.out.println("We have made it, created pastry dough.");

    }

}
