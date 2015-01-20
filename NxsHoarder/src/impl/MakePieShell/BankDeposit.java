package impl.MakePieShell;

import curtains.Modules.Module;
import curtains.Modules.ModuleInfo;
import curtains.Resources.Areas;
import curtains.Resources.ItemIds;
import org.powerbot.script.rt6.ClientContext;

/**
 * Created by Swataria`ness on 14/01/2015.
 * Package: impl.MakePieShell
 * Project: Scripting Nexus
 */
@ModuleInfo(name = "BankDeposit")
public class BankDeposit extends Module<ClientContext> {

    public BankDeposit(ClientContext ctx) {
        super(ctx);
    }


    /**
     * Activate: What will activate this Module.?
     */
    @Override
    public boolean activate() {
        //When will this activate?
        return Areas.FALADOR.contains(ctx.players.local().tile())
                && (ctx.backpack.select().count() == 28
                || ctx.backpack.id(ItemIds.POT_OF_FLOUR).count() == 0)
                && ctx.bank.opened();
    }


    /**
     * Execute: What to execute?
     */
    @Override
    public void execute() {
        //Depositing stuff
        ctx.bank.depositInventory();
    }

}
