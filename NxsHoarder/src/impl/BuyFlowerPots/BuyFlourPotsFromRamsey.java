package impl.BuyFlowerPots;

import curtains.Modules.Module;
import curtains.Modules.ModuleInfo;
import curtains.Resources.ItemIds;
import curtains.Resources.ShopIds;
import curtains.Resources.Vars;
import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;

import java.util.concurrent.Callable;

/**
 * Created by Swatarianess on 13/01/2015.
 * Package: impl
 * Project: Scripting Nexus
 */
@ModuleInfo(name = "BuyFlourPotsFromRamsey")
public class BuyFlourPotsFromRamsey extends Module<ClientContext> {


    public BuyFlourPotsFromRamsey(ClientContext ctx) {
        super(ctx);
    }


    /**
     * Activate: What will activate this Module?
     */
    @Override
    public boolean activate() {
        Vars.PURCHASE_WINDOW = ctx.widgets.component(1265, 5);
        return Vars.CURRENT_SHOP_BUYING_FROM == ShopIds.RAMSEY
                && Vars.PURCHASE_WINDOW.visible();
    }


    /**
     * Execute: What to execute?
     */
    @Override
    public void execute() {
        System.out.println("Buying flour pots from Ramsey");

        Vars.VISIBLE_WINDOW = ctx.widgets.component(1265,56);
        Component shopItemCounts = ctx.widgets.component(1265, 26);

        if(!Vars.VISIBLE_WINDOW.contains(shopItemCounts.centerPoint())){
            ctx.widgets.component(1265, 50).click();
            Condition.sleep(Random.getDelay());
        }

        Component potOfFlour = ctx.widgets.component(1265, 20).component(9);
        potOfFlour.interact(false, "Buy All", "Pot of flour");

        ctx.backpack.select().id(ItemIds.POT_OF_FLOUR);
        final int potOfFlourCountBefore = ctx.backpack.count();

        Condition.wait(new Callable<Boolean>() {

            public Boolean call() throws Exception {
                ctx.backpack.select().id(ItemIds.POT_OF_FLOUR);
                return ctx.backpack.count() > potOfFlourCountBefore;
            }
        }, 500, 10);

        //		Tracking number of flours purchased.
        int purchased = Vars.POTS_OF_FLOUR_PURCHASED;
        purchased += ctx.backpack.count() - potOfFlourCountBefore;
        Vars.POTS_OF_FLOUR_PURCHASED = purchased;

//		Ramsey's shop window is still open.
        if (ctx.widgets.component(1265, 5).visible()) {
            Component potsOfFlour = shopItemCounts.component(9);

            //Checks the number of pot of flours left.
            if (potsOfFlour.itemStackSize() == 0) {
                System.out.println("No more flour pots to buy. Move to GE and resume to make pastry dough.");
               // ctx.controller.stop();
                Vars.COMPLETED = true;
            }

            //Used for ETC
            Vars.POTS_OF_FLOUR_SHOP_COUNT = potsOfFlour.itemStackSize();
        }

        Component exit = ctx.widgets.component(1265, 88);
        exit.click();
    }

}
