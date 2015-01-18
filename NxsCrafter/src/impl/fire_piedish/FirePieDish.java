package impl.fire_piedish;

import curtains.Modules.Module;
import curtains.Modules.ModuleInfo;
import org.powerbot.script.rt6.ClientContext;

/**
 * Created by Swatarianess on 16/01/2015.
 * Package: impl.fire_piedish
 * Project: Scripting Nexus
 */
@ModuleInfo(name = "FirePieDish")
public class FirePieDish extends Module<ClientContext> {

    public FirePieDish(ClientContext ctx) {
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
        //What is going to be executed?
    }

}
