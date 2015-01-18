package impl.BuyFlowerPots;

import curtains.Modules.Module;
import curtains.Modules.ModuleInfo;
import curtains.Resources.Areas;
import curtains.Resources.ItemIds;
import curtains.Resources.Loadstones;
import curtains.Resources.Vars;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;

/**
 * Created by Swatarianess on 13/01/2015.
 * Package: impl
 * Project: Scripting Nexus
 */
@ModuleInfo(name = "TeleportToBurthorpeToBank")
public class TeleportToBurthorpeToBank extends Module<ClientContext> {

    public TeleportToBurthorpeToBank(ClientContext ctx) {
        super(ctx);
    }


    /**
     * Activate: What will activate this Module?
     */
    @Override
    public boolean activate() {
        Vars.MY_POSITION = ctx.players.local().tile();
        return (Areas.PORT_SARIM.contains(Vars.MY_POSITION)
                || Areas.TAVERLY.contains(Vars.MY_POSITION))
                && ctx.backpack.select().id(ItemIds.POT_OF_FLOUR).count() > 0
                && Loadstones.BURTHORPE.canUse(ctx);
    }


    /**
     * Execute: What to execute?
     */
    @Override
    public void execute() {
        int randomNumber = Random.nextInt(1,10);
        System.out.println("Teleporting to Burthorpe...");
        if(randomNumber > 5) {
            Loadstones.BURTHORPE.teleport(ctx);
        } else{
            Loadstones.BURTHORPE.teleport(ctx,true);
        }

    }

}
