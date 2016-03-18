package rush.hour;

import java.util.Random;

public class Util {

    /**
     * Random color generator
     * Source: Source: http://codereview.stackexchange.com/questions/108541/generate-valid-random-rgb-color-strings
     *
     * @param r Random object
     * @return A color string in hex format, includes the hashtag
     */
    public static String generateColor(Random r) {
        final char [] hex = "0123456789abcdef".toCharArray();
        char [] s = new char[7];
        int     n = r.nextInt(0x1000000);

        s[0] = '#';
        for (int i=1;i<7;i++) {
            s[i] = hex[n & 0xf];
            n >>= 4;
        }
        return new String(s);
    }
}
