package impl.BuyFlowerPots;

import curtains.Modules.Module;
import curtains.Modules.ModuleInfo;
import curtains.Resources.Areas;
import curtains.Resources.ItemIds;
import curtains.Resources.Loadstones;
import org.powerbot.script.rt6.ClientContext;

/**
 * Created by Swatarianess on 13/01/2015.
 * Package: impl
 * Project: Scripting Nexus
 */
@ModuleInfo(name = "TeleportToPortSarimToBuy")
public class TeleportToPortSarimToBuy extends Module<ClientContext> {

    public TeleportToPortSarimToBuy(ClientContext ctx) {
        super(ctx);
    }


    /**
     * Activate: What will activate this Module?
     */
    @Override
    public boolean activate() {
        return  Areas.BURTHORPE.contains(ctx.players.local())
                && !ctx.bank.opened()
                && ctx.backpack.select().id(ItemIds.POT_OF_FLOUR).count() == 0
                && Loadstones.PORT_SARIM.canUse(ctx);
    }



    /**
     * Execute: What to execute?
     */
    @Override
    public void execute() {
        Loadstones.PORT_SARIM.teleport(ctx);
        System.out.println("Teleporting To Port Sharim");
    }

}
