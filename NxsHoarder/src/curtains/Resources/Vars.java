package curtains.Resources;

import org.powerbot.script.Tile;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.GeItem;

/**
 * Created by Reall_blue on 13/01/2015.
 */
public class Vars {

        public static final String SCRIPT_NAME = "NxsHoarder";
        public static final int POT_OF_FLOUR_GE_PRICE = GeItem.price(ItemIds.POT_OF_FLOUR);
        public static final int PASTRY_DOUGH_GE_PRICE = GeItem.price(ItemIds.PASTRY_DOUGH);


        public static int POTS_OF_FLOUR_PURCHASED;
        public static int POTS_OF_FLOUR_SHOP_COUNT;
        public static int MY_ANIMATION;

        public static Tile SHOP_ENTRANCE;
        public static Tile MY_POSITION;
        public static final Tile DOORSTEP = new Tile(3017, 3206);

        public static ShopIds CURRENT_SHOP_BUYING_FROM = ShopIds.WYDIN;
        public static Component PURCHASE_WINDOW;
        public static Component VISIBLE_WINDOW;

        public static Boolean COMPLETED = false;
        public static Boolean MAKE_PIE_SHELL;

        //Calculating Profit
        public static int POT_OF_FLOUR_IN_BANK_AMOUNT;
        public static int PASTRY_DOUGH_MIXED;

}
