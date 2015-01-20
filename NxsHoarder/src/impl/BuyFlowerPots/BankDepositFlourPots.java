package impl.BuyFlowerPots;

import curtains.Modules.Module;
import curtains.Modules.ModuleInfo;
import curtains.Resources.Areas;
import curtains.Resources.ItemIds;
import org.powerbot.script.rt6.ClientContext;

/**
 * Created by Swatarianess on 13/01/2015.
 * Package: impl
 * Project: Scripting Nexus
 */
@ModuleInfo(name = "BankDepositFlourPots")
public class BankDepositFlourPots extends Module<ClientContext> {

    public BankDepositFlourPots(ClientContext ctx) {
        super(ctx);
    }


    /**
     * Activate: What will activate this Module?
     */
    @Override
    public boolean activate() {
        //When will this activate?
        return Areas.BURTHORPE.contains(ctx.players.local().tile()
        ) && ctx.bank.opened();
    }


    /**
     * Execute: What to execute?
     */
    @Override
    public void execute() {
        System.out.println("Depositing Flower Pots");

        if(ctx.backpack.select().id(ItemIds.POT_OF_FLOUR).count() > 0){
            ctx.bank.depositInventory();
        } else{
            //ctx.bank.close();
            ctx.input.send("{VK_ESCAPE}");
        }
    }

}
