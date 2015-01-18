package Bathtub.modules;

import curtains.Modules.Module;
import curtains.Modules.ModuleInfo;
import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

/**
 * Created by Swatarianess on 03/01/2015.
 * Package: Bathtub.modules
 * Project: Scripting Nexus
 */

@ModuleInfo(name="Chopping")
public class Chop extends Module<ClientContext> {
    private int[] treeIDs = {38785, 38760, 47600};

    public Chop(ClientContext ctx) {
        super(ctx);
    }

    /**
     * If inventory is less than 28 & ? & avatar is idle, execute
     * @return
     */
    @Override
    public boolean activate() {
        return ctx.backpack.select().count() < 28
            && !ctx.objects.select().id(treeIDs).isEmpty()
            && ctx.players.local().animation() == -1;
    }

    /**
     * Initialize GameObject called tree,
     * Set Camera pitch
     * If GameObject is within view, chop tree
     * Otherwise, ?? , turn camera towards GameObject
     */
    @Override
    public void execute() {
        GameObject tree = ctx.objects.nearest().poll();
        ctx.camera.pitch(true);
        if(tree.inViewport())
        {
            tree.interact("Chop");
            System.out.printf("[Debug] GameObject interacted with, sleeping...\n");
            Condition.sleep(1000);

            int chopAnime = 21191;
            while(ctx.players.local().animation() == chopAnime && ctx.players.local().animation() != -1){
                    Condition.sleep(1000);
                    System.out.printf("[Debug] Within Sleep loop...\n");
            }
        }else{
            ctx.movement.step(tree);
            ctx.camera.turnTo(tree);
            System.out.printf("[Debug] Moving Camera, now sleeping...\n");
            Condition.sleep(1000);

        }
    }

}

