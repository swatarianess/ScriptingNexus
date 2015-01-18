package curtains.Modules;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.rt6.ClientContext;

/**
 * Created by Swatarianess on 03/01/2015.
 * Package: curtains.Modules
 * Project: Scripting Nexus
 */

public abstract class Module<C extends ClientContext> extends ClientAccessor<C> {
    protected boolean canceled;
    protected final String name = null;
    protected final String[] authors = new String[0];

    @Override
    public String toString(){
        return this.getName();
    }

    public Module(C ctx) {
        super(ctx);
    }

    /**
     * Checks if the task is not canceled and if the generic checks are correct
     * @return returns if not cancelled and is activated
     */
    public boolean check(){
        return !this.canceled && this.activate();
    }

    //Generic check run
    public abstract boolean activate();
    public abstract void execute();

    public boolean isCanceled(){
        return canceled;
    }

    public void setCanceled(boolean canceled){
        this.canceled = canceled;
    }

    public String getName(){
        return name;
    }

    public String[] getAuthors(){
        return authors;
    }
}
