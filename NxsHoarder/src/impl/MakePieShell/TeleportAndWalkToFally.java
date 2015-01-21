package impl.MakePieShell;

import curtains.Modules.Module;
import curtains.Modules.ModuleInfo;
import curtains.Resources.Areas;
import curtains.Resources.Loadstones;
import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

import java.util.concurrent.Callable;

/**
 * Created by Swatarianess on 20/01/2015.
 * Package: impl.MakePieShell
 * Project: Scripting Nexus
 */
@ModuleInfo(name = "TeleportToFally")
public class TeleportAndWalkToFally extends Module<ClientContext> {

    public TeleportAndWalkToFally(ClientContext ctx) {
        super(ctx);
    }


    /**
     * Activate: When nowhere near the Falador
     */
    @Override
    public boolean activate() {
        return false;
    }


    /**
     * Execute: Execute
     */
    @Override
    public void execute() {
        //What is going to be executed?

        Loadstones.FALADOR.teleport(ctx,true);
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !ctx.players.local().inMotion()
                        && Areas.FALADOR_CITY.contains(ctx.players.local().tile())
                        && ctx.players.local().animation() == -1;
            }
        },200,65);
    }

}
