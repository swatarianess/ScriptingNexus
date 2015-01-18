package impl;

import curtains.Modules.Module;
import curtains.Modules.ModuleInfo;
import curtains.Resources.Areas;
import curtains.Resources.ItemIds;
import curtains.Resources.Vars;
import org.powerbot.script.rt6.ClientContext;

/**
 * Created by Swatarianess on 16/01/2015.
 * Package: impl
 * Project: Scripting Nexus
 */
@ModuleInfo(name = "MouldClay")
public class MouldClay extends Module<ClientContext> {

    public MouldClay(ClientContext ctx) {
        super(ctx);
    }


    /**
     * Activate: What will activate this Module?
     */
    @Override
    public boolean activate() {
        //When will this activate?
        Vars.MY_POSITION = ctx.players.local().tile();
        return Areas.DRAYNOR.contains(Vars.MY_POSITION)
               && ctx.backpack.select().id(ItemIds.SOFT_CLAY).count() > 0
               && ctx.backpack.select().id(ItemIds.PIE_DISH).count() <= 0;
    }


    /**
     * Execute: What to execute?
     */
    @Override
    public void execute() {
        //What is going to be executed?
        //TODO add method to mould the clay
    }

}
