package curtains.Resources;

import org.powerbot.script.Area;
import org.powerbot.script.Tile;

/**
 * Created by Reall_blue on 13/01/2015.
 */
public final class Areas {

    public static final Area BURTHORPE =
            new Area(new Tile(2879, 3551), new Tile(2905, 3524));

    public static final Area PORT_SARIM =
            new Area(new Tile(2980, 3225), new Tile(3030, 3180));

    public static final Area TAVERLY =
            new Area(new Tile(2877, 3456), new Tile(2903, 3435));

    public static final Area GRAND_EXCHANGE =
            new Area(new Tile(3139, 3515), new Tile(3197, 3470));

    public static final Area FALADOR = new Area(
            new Tile(2939, 3354),
            new Tile(2939, 3377),
            new Tile(2946, 3392),
            new Tile(2957, 3382),
            new Tile(2956, 3361),
            new Tile(2951, 3355)
    );

    public static final Area FALADOR_CITY = new Area();

    private Areas() {

    }

}
