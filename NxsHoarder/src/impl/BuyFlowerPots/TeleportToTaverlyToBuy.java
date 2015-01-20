package impl.BuyFlowerPots;

import curtains.Modules.Module;
import curtains.Modules.ModuleInfo;
import curtains.Resources.*;
import org.powerbot.script.rt6.ClientContext;

/**
 * Created by Swatarianess on 13/01/2015.
 * Package: impl
 * Project: Scripting Nexus
 */
@ModuleInfo(name = "TeleportToTaverlyToBuy")
public class TeleportToTaverlyToBuy extends Module<ClientContext> {

    public TeleportToTaverlyToBuy(ClientContext ctx) {
        super(ctx);
    }


    /**
     * Activate: What will activate this Module?
     */
    @Override
    public boolean activate() {
        return Vars.CURRENT_SHOP_BUYING_FROM == ShopIds.RAMSEY
                && Areas.BURTHORPE.contains(ctx.players.local())
                && !ctx.bank.opened()
                && ctx.backpack.select().id(ItemIds.POT_OF_FLOUR).count() == 0
                && Loadstones.TAVERLEY.canUse(ctx);
    }


    /**
     * Execute: What to execute?
     */
    @Override
    public void execute() {
        Loadstones.TAVERLEY.teleport(ctx,true);
        System.out.println("Teleporting To Taverly");
    }

}
