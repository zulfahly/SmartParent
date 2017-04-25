package pnj.ti.b2013.smartparent.util;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Fahly on 12/30/2016.
 */

public class ResourceUtil {

    private ResourceUtil() {
    }

    public static int dipToPixel(Context context, float dip) {
        /* http://stackoverflow.com/questions/4605527/converting-pixels-to-dp */
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                context.getResources().getDisplayMetrics());
    }

    public static Point getScreenDimension(Context context) {
        /* http://stackoverflow.com/questions/1016896/get-screen-dimensions-in-pixels */
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= 13) {
            display.getSize(size);
        } else {
            size.set(display.getWidth(), display.getHeight());
        }
        return size;
    }
}
