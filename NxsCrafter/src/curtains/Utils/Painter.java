package curtains.Utils;

import org.powerbot.script.rt6.ClientContext;

import java.awt.*;
import java.text.DecimalFormat;

/**
 * Original
 * @author Coma
 * Date: 8/22/13
 * Time: 10:41 PM
 */

public class Painter {

    private String name, version;
    private PaintProperty[] properties = new PaintProperty[0];

    private final AlphaComposite background = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .75f);
    private final AlphaComposite foreground = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
    private final Font title = new Font("Tahoma", Font.PLAIN, 16);
    private final Font stats = new Font("Tahoma", Font.PLAIN, 12);
    private final Color backgroundColor;
    private final Color textColor;

    //We use this to format large amounts
    private final DecimalFormat format = new DecimalFormat("###,###,###");

    /**
     *
     * @param name Name of the script.
     * @param version Current version of the script.
     */
    public Painter(String name, String version, Color backgroundColor, Color textColor) {
        this.name = name;
        this.version = version;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    /**
     *
     * @param properties Each property represents item in the paint.
     * @return this.
     */
    public Painter properties(PaintProperty... properties) {
        this.properties = properties;
        return this;
    }

    /**
     *
     * @param i The amount you want to be formatted.
     * @return String of formatted double.
     */
    public String format(double i) {
        return this.format.format(i);
    }

    /**
     *
     * @param time Current time in ms.
     * @return String of formatted time in HH:MM:SS
     */
    public String formatTime(final long time) {
        final int sec = (int) (time / 1000), h = sec / 3600, m = sec / 60 % 60, s = sec % 60;
        return (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
    }

    /**
     *
     * @param ctx Script context.
     * @param skillIndex Which skill would like to track.
     * @param level Level you wish to reach
     * @return int of experience left till desired level.
     */
    public int getExperienceToLevel(ClientContext ctx, int skillIndex, int level) {
        return getXPForLevel(level) - ctx.skills.experience(skillIndex);
    }

    /**
     *
     * @param ctx Script context.
     * @param skillIndex Which skill would like to track.
     * @param goal Level you wish to reach.
     * @param xpPerHour Current Xp per hour.
     * @return String of formatted time till to desired level.
     */
    public String getTimeToLevel(ClientContext ctx, int skillIndex, int goal, final int xpPerHour) {
        return xpPerHour > 0 ? formatTime((long)(getExperienceToLevel(ctx, skillIndex, goal) * 3600000D / xpPerHour)) : formatTime(0L);
    }

    /**
     *
     * @param level The level you wish to know experience for.
     * @return int of experience needed to level.
     */
    public int getXPForLevel(int level) {
        int points = 0,
                output = 0;
        for (int lvl = 1; lvl <= level; lvl++) {
            points += Math.floor((double) lvl + 300.0 *
                    Math.pow(2.0, (double) lvl / 7.0));
            if (lvl >= level)
                return output;
            output = (int) Math.floor(points / 4);
        }
        return 0;
    }

    /**
     *
     * @param count Amount you wish to get hourly rate for.
     * @param runTime The current time in ms.
     * @return double of current hourly rate of inputted count.
     */
    public double getHourlyRate(int count, long runTime) {
        return runTime > 0 ? (count * 3600000D) / (runTime) : 0;
    }

    /**
     *
     * @param render The current graphic instance.
     */
    public void draw(Graphics render) {
        final Graphics2D g = (Graphics2D) render;
        final FontMetrics fm = g.getFontMetrics(title);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        g.setFont(title);
        //Background color
        g.setColor(backgroundColor);
        g.setComposite(background);
        g.fillRect(5, 5, 180, 50 + (properties.length * 20));
        //Text color
        g.setComposite(foreground);
        g.setColor(textColor);
        g.drawRect(5, 5, 180, 50 + (properties.length * 20));

        final int w1 = fm.stringWidth(version);
        final int w2 = fm.stringWidth(name);

        g.drawString(version, (180 / 2) + 5 - ((w1 / 2)), 20);
        g.drawString(name, (180 / 2) + 5 - ((w2 / 2)), 40);
        g.drawLine(15, 45, 175, 45);

        g.setFont(stats);

        for (int i = 0; i < properties.length; i++) {
            final PaintProperty p = properties[i];
            g.drawString(p.getValue(), 15 + p.getOffset(), 70 + (i * 20));
        }
    }

    /**
     * The paint objects we use inside of the Painter.class
     */
    public static class PaintProperty {
        private String value;
        private int offset;

        public PaintProperty(String value, int offset) {
            this.value = value;
            this.offset = offset;
        }

        public PaintProperty(String value) {
            this(value, 0);
        }

        public PaintProperty() {
            this("");
        }

        public PaintProperty value(String value) {
            this.value = value;
            return this;
        }

        public PaintProperty offset(int offset) {
            this.offset = offset;
            return this;
        }

        public String getValue() {
            return this.value;
        }

        public int getOffset() {
            return this.offset;
        }
    }

}

