import curtains.Logger;
import curtains.Modules.Module;
import curtains.Resources.Vars;
import curtains.Utils.Painter;
import impl.BuyFlowerPots.*;
import org.powerbot.script.*;
import org.powerbot.script.rt6.ClientContext;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;

import static curtains.Resources.Vars.PASTRY_DOUGH_GE_PRICE;
import static curtains.Resources.Vars.POT_OF_FLOUR_GE_PRICE;


/**
 * Created by Swatarianess on 13/01/2015.
 * Package: PACKAGE_NAME
 * Project: Scripting Nexus
 */
@Script.Manifest(name = "Hoarder", description = "A script that buys items and hoards them.")
public class Hoarder extends PollingScript<ClientContext> implements BotMenuListener, PaintListener {

    private java.util.List<Module> modules = new ArrayList<Module>();
    private static Hoarder instance;
    private Logger scriptLogger;
    private static PrintStream stdout = System.out;
    private Module lastExecutedModule;

    private Painter painter;
    private Painter.PaintProperty time;
    private Painter.PaintProperty items;
    private Painter.PaintProperty itemsPerHour;
    private Painter.PaintProperty Objective;

    private Painter.PaintProperty pastryph;
    private Painter.PaintProperty doughMade;
    private Painter.PaintProperty eta;
    private Painter.PaintProperty profitHr;

    private Painter.PaintProperty etaPots;




    public Hoarder(){
        Color backgroundColour = new Color(158, 63, 150);
        Color textColour = new Color(253, 107, 104);
        this.painter = new Painter(Vars.SCRIPT_NAME, "0.1", backgroundColour, textColour);
        this.time = new Painter.PaintProperty();
        this.items = new Painter.PaintProperty();
        this.itemsPerHour = new Painter.PaintProperty();
        this.Objective = new Painter.PaintProperty();
        this.pastryph = new Painter.PaintProperty();
        this.doughMade = new Painter.PaintProperty();
        this.eta = new Painter.PaintProperty();
        this.profitHr = new Painter.PaintProperty();
        this.etaPots = new Painter.PaintProperty();

    }

    @Override
    public void stop(){
        //Logger stuff
        scriptLogger.dispose();
        System.setOut(stdout);
        ctx.controller.stop();

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
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.game.loggedIn();
                }
            },5000,1000);

        executeModules();
    }

    private void executeModules()
    {
        //Basic Modular execution
        for(Module module : modules){
            if(module.check()) {
                if(lastExecutedModule != module){
                    System.out.printf("Executing: %s\n",module.toString()); // module.getName());

                    lastExecutedModule = module;
                }
                module.execute();
                if(Vars.COMPLETED){
                    stop();
                }

            }
        }
    }

    private void startupModules()
    {
    // /*
       modules.addAll(Arrays.asList(
               new MoveToBurthorpeBank(ctx),
               new BankDepositFlourPots(ctx),

               new TeleportToPortSarimToBuy(ctx),
               new MoveToWydinShop(ctx),
               new BuyFlourPotsFromWydin(ctx),

               new TeleportToBurthorpeToBank(ctx),
               new BuyFlourPotsFromRamsey(ctx),
               new MoveToRamseyShop(ctx),
               new TeleportToTaverlyToBuy(ctx)));
     // */

       /*
        if (ctx.skills.level(Constants.SKILLS_COOKING) >= 10) {
            modules.addAll(Arrays.asList(
            new WalkToFallyBank(ctx),
            new BankWithdrawFlourPots(ctx),
            new WalkToFountain(ctx),
            new MakePastry(ctx),
            new BankDeposit(ctx)
            ));
        }
        */
            System.out.println("Modules setup.");
    }

    public static Hoarder instance(){
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
        long runtime = ctx.controller.script().getRuntime();
        int itemsPerHourVar = (int) Math.round(painter.getHourlyRate(Vars.POTS_OF_FLOUR_PURCHASED, runtime));

        double secondRuntime = ctx.controller.script().getTotalRuntime() / 1000;
        double minuteRuntime = secondRuntime / 60;
        double hourRuntime = minuteRuntime / 60;

        int potsProfit = Vars.POTS_OF_FLOUR_PURCHASED * POT_OF_FLOUR_GE_PRICE;
        int potsPerMinute = (int) (Vars.POTS_OF_FLOUR_PURCHASED / minuteRuntime);
        int potPerHour = (int) (Vars.POTS_OF_FLOUR_PURCHASED / hourRuntime);
        int potProfitPerHour = (int) ((potsProfit / hourRuntime)/1000);


        int doughProfit = Vars.PASTRY_DOUGH_MIXED * PASTRY_DOUGH_GE_PRICE;
        int doughPerMinute = (int) (Vars.PASTRY_DOUGH_MIXED / minuteRuntime);
        int doughPerHour = (int) (Vars.PASTRY_DOUGH_MIXED / hourRuntime);
        int doughProfitPerHour = (int) ((doughProfit / hourRuntime) / 1000);

        int doughETC = 0;
        if (doughPerMinute > 0) {
            doughETC = Vars.POT_OF_FLOUR_IN_BANK_AMOUNT / doughPerMinute;
        }

        int potsETC = 0;
        if(potsPerMinute > 0){
            potsETC = Vars.POTS_OF_FLOUR_SHOP_COUNT / potsPerMinute;
        }


            painter.properties(
                    Objective.value("~Buying Flour Pots~"),
                    time.value("Runtime: " + painter.formatTime(runtime)),
                    items.value("Flour Pots bought: " + Vars.POTS_OF_FLOUR_PURCHASED),
                    itemsPerHour.value("FlourPots/hr: " + itemsPerHourVar)
            ).draw(graphics);


       // if (Vars.PASTRY_DOUGH_MIXED > 0) {
     /*       painter.properties(
                    Objective.value("~Making Dough~"),
                    time.value("Runtime: " + painter.formatTime(runtime)),
                    doughMade.value("Dough: " + Vars.PASTRY_DOUGH_MIXED + "/" + Vars.POT_OF_FLOUR_IN_BANK_AMOUNT),
                    pastryph.value("Dough/hr: " + doughPerHour),
                    profitHr.value("Profit/hr: " + doughProfitPerHour + "k"),
                    eta.value("ETA: " + doughETC + " mins")
            ).draw(graphics);
      //  }
      */
    }
}
