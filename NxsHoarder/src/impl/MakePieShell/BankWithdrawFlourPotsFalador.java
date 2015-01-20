package impl.MakePieShell;

import curtains.Modules.Module;
import curtains.Modules.ModuleInfo;
import curtains.Resources.Areas;
import curtains.Resources.ItemIds;
import curtains.Resources.Vars;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;

/**
 * Created by Swatarianess on 14/01/2015.
 * Package: impl.MakePieShell
 * Project: Scripting Nexus
 */
@ModuleInfo(name = "BankWithdrawFlourPots")
public class BankWithdrawFlourPotsFalador extends Module<ClientContext> {

    public BankWithdrawFlourPotsFalador(ClientContext ctx) {
        super(ctx);
    }


    /**
     * Activate: What will activate this Module?
     */
    @Override
    public boolean activate() {
        return Areas.FALADOR.contains(ctx.players.local().tile())
                && ctx.backpack.select().id(ItemIds.POT_OF_FLOUR).count() == 0
                && ctx.backpack.select().id(ItemIds.PASTRY_DOUGH).count() == 0
                && ctx.backpack.select().id(ItemIds.EMPTY_POT).count() == 0
                && ctx.bank.opened();
    }


    /**
     * Execute: What to execute?
     */
    @Override
    public void execute() {
        Component bankQuickLoad = ctx.widgets.component(762,48);
        // "Withdrawing pots of flour"
        int index = ctx.bank.indexOf(ItemIds.POT_OF_FLOUR);

        if(index == -1){
            //Not Found
            ctx.controller.stop();
        }else{
            bankQuickLoad.click();
            Vars.POT_OF_FLOUR_IN_BANK_AMOUNT = ctx.bank.itemAt(index).stackSize();
        }
    }

}
