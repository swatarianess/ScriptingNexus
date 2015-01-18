package curtains;

import Bathtub.modules.Chop;
import Bathtub.modules.Drop;
import curtains.Modules.Module;
import curtains.Utils.Painter;
import org.powerbot.script.*;
import org.powerbot.script.rt6.ClientContext;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reall_blue on 02/01/2015.
 */

@Script.Manifest(name = "NxsPowerChopper", description = "Cuts down normal trees and drops them.", properties="client=6")
public class Main extends PollingScript<ClientContext> implements BotMenuListener, PaintListener{

    private List<Module> modules = new ArrayList<Module>();
    private static Main instance;
    private Logger scriptLogger;
    private static PrintStream stdout = System.out;
    private Module lastExecutedModule;
    private boolean weRunning;

    private Painter painter;
    private Painter.PaintProperty time;

    public Main(){
        Color backgroundColour = new Color(158, 63, 150);
        Color textColour = new Color(253, 107, 104);
        this.painter = new Painter(Constants.SCRIPT_NAME,"0.1",backgroundColour,textColour);
        this.time = new Painter.PaintProperty();
    }

    @Override
    public void stop(){
        //Logger stuff
        scriptLogger.dispose();
        System.setOut(stdout);
    }

    @Override
    public void start(){
        instance = this;
        scriptLogger = new Logger();
        System.setOut(new PrintStream(scriptLogger));
        this.startupModules();
        System.out.println("Initializing script...");


        //Another method
        /**
         * Module.add(new Bathtub.modules.chop(ctx)));
         * module.addAll(Arrays.asList(new Bathtub.modules.Chop(ctx), new Bathtub.modules.Drop(ctx)));
         */

    }

    @Override
    public void poll() {
        //executeModules();
        int hour = 60*60*1000;
        System.out.println("Script Active.");
        while(ctx.game.loggedIn()){
            Condition.sleep(hour);
        }
    }

    private void executeModules()
    {
        //Basic Modular execution
        for(Module<ClientContext> module : modules){
            if(module.check()) {
                if(lastExecutedModule != module){
                    System.out.printf("Executing: %s\n", module.getName());

                    lastExecutedModule = module;
                }
                module.execute();
            }
        }
    }

    private void startupModules()
    {
        modules.add(new Chop(ctx));
        modules.add(new Drop(ctx));
        System.out.println("Modules Setup.");
    }

    public static Main instance(){
        return instance;
    }

    @Override
    public void menuSelected(MenuEvent e) {
        //Logger stuff
        final JMenu menu = (JMenu) e.getSource();
        final JMenuItem showLogger = new JMenuItem("Runtime log") {{
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scriptLogger.setVisible(true);
            }
        });
        }};
        menu.add(showLogger);
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }

    @Override
    public void repaint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

        painter.properties(
                time.value("Runtime: " + painter.formatTime(getRuntime()))
        ).draw(graphics);

    }

    public static class Constants{
        public static final String SCRIPT_NAME = "NxTest";
    }

}
