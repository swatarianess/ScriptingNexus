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
@ModuleInfo(name = "BuyFlourPotsFromWydin")
public class BuyFlourPotsFromWydin extends Module<ClientContext> {

    public BuyFlourPotsFromWydin(ClientContext ctx) {
        super(ctx);
    }


    /**
     * Activate: What will activate this Module?
     */
    @Override
    public boolean activate() {
        //When will this activate?
        return Vars.CURRENT_SHOP_BUYING_FROM == ShopIds.WYDIN
              && ctx.widgets.component(1265,5).visible();
    }


    /**
     * Execute: What to execute?
     */
    @Override
    public void execute() {
        Component potOfFlour = ctx.widgets.component(1265,20).component(0);
        Vars.VISIBLE_WINDOW = ctx.widgets.component(1265,56);
        Component shopItemCounts = ctx.widgets.component(1265, 26);

        if(!Vars.VISIBLE_WINDOW.contains(shopItemCounts.centerPoint())){
            ctx.widgets.component(1265, 50).click();
            Condition.sleep(Random.getDelay());
        }
        potOfFlour.interact(false,"Buy All","Pot of flour");

        //Track pot numbers
        ctx.backpack.select().id(ItemIds.POT_OF_FLOUR);
        final int pofBefore = ctx.backpack.count();

        //Wait to complete buy
        Condition.wait(new Callable<Boolean>(){
                           public Boolean call() throws Exception{
                               ctx.backpack.select().id(ItemIds.POT_OF_FLOUR);
                               return ctx.backpack.count() > pofBefore;
                           }
                       }, 500, 10);
        //Track number of pots
        int purchased = Vars.POTS_OF_FLOUR_PURCHASED;
        purchased += ctx.backpack.count() - pofBefore;
        Vars.POTS_OF_FLOUR_PURCHASED = purchased;

        //Windows open
        if(ctx.widgets.component(1265, 5).visible()){
            Component potsOfFlour = shopItemCounts.component(0);

            //Check remaining
            if(potsOfFlour.itemStackSize() == 0){
                System.out.println("Going to Taverly");
                Vars.CURRENT_SHOP_BUYING_FROM = ShopIds.RAMSEY;
            }
            //Calculate ETC
                Vars.POTS_OF_FLOUR_SHOP_COUNT = potsOfFlour.itemStackSize();
        }
        //Exit Interface

          Component exit = ctx.widgets.component(1265,88);
          exit.click();

       // ctx.input.send("{VK_ESC}");
    }

}
