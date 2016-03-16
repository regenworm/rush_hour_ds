package rush.hour;

import java.util.Random;

public class Util {

    // Source: http://codereview.stackexchange.com/questions/108541/generate-valid-random-rgb-color-strings
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
