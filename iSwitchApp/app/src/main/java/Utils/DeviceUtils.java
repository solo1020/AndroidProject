package Utils;

import android.content.Context;
import android.net.wifi.WifiManager;


public class DeviceUtils {
    public static String getLocalIpAddress(Context context){
        int paramInt = ((WifiManager) context.getSystemService(context.WIFI_SERVICE)).getConnectionInfo().getIpAddress();
        return (paramInt & 0xFF) + "." + (0xFF & paramInt >> 8) +
                "." + (0xFF & paramInt >> 16) + "." + (0xFF & paramInt >> 24);
    }

}
