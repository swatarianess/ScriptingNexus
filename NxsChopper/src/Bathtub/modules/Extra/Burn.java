package Bathtub.modules.Extra;

import curtains.Modules.Module;
import curtains.Modules.ModuleInfo;
import org.powerbot.script.rt6.ClientContext;

/**
 * Created by Swatarianess on 03/01/2015.
 * Package: Bathtub.modules
 * Project: Scripting Nexus
 */

@ModuleInfo(name = "Burning")
public class Burn extends Module<ClientContext> {

    public Burn(ClientContext ctx) {
        super(ctx);
    }


    /**
     * Activate: What will activate this Module?
     */
    @Override
    public boolean activate() {
        //When will this activate?
        return false;
    }


    /**
     * Execute: What to execute?
     */
    @Override
    public void execute() {

    }


}
