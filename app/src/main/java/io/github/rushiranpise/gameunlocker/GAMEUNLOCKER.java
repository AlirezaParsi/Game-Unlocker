package io.github.rushiranpise.gameunlocker;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

@SuppressLint("DiscouragedPrivateApi")
public class GAMEUNLOCKER implements IXposedHookLoadPackage {

    private static final String TAG = GAMEUNLOCKER.class.getSimpleName();

    // Map to store package names and their corresponding device profiles
    private static final Map<String, DeviceProfile> PACKAGE_TO_DEVICE_MAP = new HashMap<>();

    static {
        // Asus ROG 6
        addPackagesToMap(DeviceProfile.ASUS_ROG_6,
            "com.ea.gp.fifamobile",
            "com.gameloft.android.ANMP.GloftA9HM",
            "com.madfingergames.legends",
            "com.pearlabyss.blackdesertm",
            "com.pearlabyss.blackdesertm.gl"
        );

        // Black Shark 4
        addPackagesToMap(DeviceProfile.BLACK_SHARK_4,
            "com.proximabeta.mf.uamo"
        );

        // iQOO 11 Pro
        addPackagesToMap(DeviceProfile.IQOO_11_PRO,
            "com.tencent.KiHan",
            "com.tencent.tmgp.cf",
            "com.tencent.tmgp.cod",
            "com.tencent.tmgp.gnyx"
        );

        // Xiaomi 13 Pro
        addPackagesToMap(DeviceProfile.XIAOMI_13_PRO,
            "com.netease.lztgglobal",
            "com.pubg.imobile",
            "com.pubg.krmobile",
            "com.rekoo.pubgm",
            "com.riotgames.league.wildrift",
            "com.riotgames.league.wildrifttw",
            "com.riotgames.league.wildriftvn",
            "com.riotgames.league.teamfighttactics",
            "com.riotgames.league.teamfighttacticstw",
            "com.riotgames.league.teamfighttacticsvn",
            "com.tencent.ig",
            "com.tencent.tmgp.pubgmhd",
            "com.vng.pubgmobile",
            "vng.games.revelation.mobile",
            "com.ngame.allstar.eu",
            "com.mojang.minecraftpe",
            "com.YoStar.AetherGazer",
            "com.miHoYo.GenshinImpact",
            "com.garena.game.lmjx"
        );

        // OnePlus 9 Pro
        addPackagesToMap(DeviceProfile.ONEPLUS_9_PRO,
            "com.epicgames.fortnite",
            "com.epicgames.portal",
            "com.tencent.lolm",
            "jp.konami.pesam"
        );

        // Xiaomi Mi 11T Pro
        addPackagesToMap(DeviceProfile.XIAOMI_MI_11T_PRO,
            "com.ea.gp.apexlegendsmobilefps",
            "com.mobilelegends.mi",
            "com.levelinfinite.hotta.gp",
            "com.supercell.clashofclans",
            "com.vng.mlbbvn"
        );

        // Poco F5
        addPackagesToMap(DeviceProfile.POCO_F5,
            "com.dts.freefiremax",
            "com.dts.freefireth",
            "com.mobile.legends"
        );

        // Lenovo Tablet
        addPackagesToMap(DeviceProfile.LENOVO_TABLET,
            "com.garena.game.codm",
            "com.tencent.tmgp.kr.codm",
            "com.vng.codmvn",
            "com.activision.callofduty.shooter",
            "com.garena.game.kgvn"
        );
    }

    // Helper method to add multiple packages to the map
    private static void addPackagesToMap(DeviceProfile profile, String... packages) {
        for (String packageName : packages) {
            PACKAGE_TO_DEVICE_MAP.put(packageName, profile);
        }
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        String packageName = loadPackageParam.packageName;
        DeviceProfile profile = PACKAGE_TO_DEVICE_MAP.get(packageName);
        if (profile != null) {
            profile.applyProfile();
            XposedBridge.log("Spoofed " + packageName + " as " + profile.getDeviceName());
        }
    }

    // Method to set system properties using reflection
    private static void setPropValue(String key, Object value) {
        try {
            Log.d(TAG, "Defining prop " + key + " to " + value.toString());
            Field field = Build.class.getDeclaredField(key);
            field.setAccessible(true);
            field.set(null, value);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            XposedBridge.log("Failed to set prop: " + key + " - " + e.getMessage());
        }
    }

    // Enum to manage device profiles
    public enum DeviceProfile {
        ASUS_ROG_6("asus", "AI2201", "ASUS_AI2201", "Asus ROG 6"),
        BLACK_SHARK_4("blackshark", "2SM-X706B", "2SM-X706B", "Black Shark 4"),
        IQOO_11_PRO("vivo", "V2243A", "V2243A", "iQOO 11 Pro"),
        XIAOMI_13_PRO("Xiaomi", "2210132G", "2210132G", "Xiaomi 13 Pro"),
        ONEPLUS_9_PRO("OnePlus", "LE2123", "LE2123", "OnePlus 9 Pro"),
        XIAOMI_MI_11T_PRO("Xiaomi", "2107113SI", "2107113SI", "Xiaomi Mi 11T Pro"),
        POCO_F5("Xiaomi", "23049PCD8G", "23049PCD8G", "Poco F5"),
        LENOVO_TABLET("Lenovo", "TB-9707F", "Lenovo TB-9707F", "Lenovo Tablet");

        private final String manufacturer;
        private final String device;
        private final String model;
        private final String deviceName;

        DeviceProfile(String manufacturer, String device, String model, String deviceName) {
            this.manufacturer = manufacturer;
            this.device = device;
            this.model = model;
            this.deviceName = deviceName;
        }

        public void applyProfile() {
            setPropValue("MANUFACTURER", manufacturer);
            setPropValue("DEVICE", device);
            setPropValue("MODEL", model);
        }

        public String getDeviceName() {
            return deviceName;
        }
    }
}
