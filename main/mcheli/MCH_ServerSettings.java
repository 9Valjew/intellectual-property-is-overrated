package mcheli;

public class MCH_ServerSettings
{
    public static boolean enableCamDistChange;
    public static boolean enableEntityMarker;
    public static boolean enablePVP;
    public static double stingerLockRange;
    public static boolean enableDebugBoundingBox;
    
    static {
        MCH_ServerSettings.enableCamDistChange = true;
        MCH_ServerSettings.enableEntityMarker = true;
        MCH_ServerSettings.enablePVP = true;
        MCH_ServerSettings.stingerLockRange = 120.0;
        MCH_ServerSettings.enableDebugBoundingBox = true;
    }
}
