package io.github.rushiranpise.gameunlocker;

import android.os.Build;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class GAMEUNLOCKER implements IXposedHookLoadPackage {

    private static final String TAG = "GAMEUNLOCKER";
    private static final Map<String, Map<String, String>> SPOOF_PROPS = new HashMap<>();
    private static volatile boolean moduleUnloaded = false;

    static {
        SPOOF_PROPS.put("com.mobilelegends.mi", new HashMap<String, String>() {{
            put("BRAND", "ZTE"); put("DEVICE", "NX769J"); put("MANUFACTURER", "ZTE"); put("MODEL", "NX769J");
        }});
        SPOOF_PROPS.put("com.supercell.brawlstars", new HashMap<String, String>() {{
            put("BRAND", "ZTE"); put("DEVICE", "NX769J"); put("MANUFACTURER", "ZTE"); put("MODEL", "NX769J");
        }});
        SPOOF_PROPS.put("com.blizzard.diablo.immortal", new HashMap<String, String>() {{
            put("BRAND", "ZTE"); put("DEVICE", "NX769J"); put("MANUFACTURER", "ZTE"); put("MODEL", "NX769J");
        }});
        SPOOF_PROPS.put("com.netease.newspike", new HashMap<String, String>() {{
            put("BRAND", "ZTE"); put("DEVICE", "NX769J"); put("MANUFACTURER", "ZTE"); put("MODEL", "NX769J");
        }});
        SPOOF_PROPS.put("com.activision.callofduty.warzone", new HashMap<String, String>() {{
            put("BRAND", "ZTE"); put("DEVICE", "NX769J"); put("MANUFACTURER", "ZTE"); put("MODEL", "NX769J");
        }});
        SPOOF_PROPS.put("com.pubg.newstate", new HashMap<String, String>() {{
            put("BRAND", "ZTE"); put("DEVICE", "NX769J"); put("MANUFACTURER", "ZTE"); put("MODEL", "NX769J");
        }});
        SPOOF_PROPS.put("com.gamedevltd.destinywarfare", new HashMap<String, String>() {{
            put("BRAND", "ZTE"); put("DEVICE", "NX769J"); put("MANUFACTURER", "ZTE"); put("MODEL", "NX769J");
        }});
        SPOOF_PROPS.put("com.pikpok.dr2.play", new HashMap<String, String>() {{
            put("BRAND", "ZTE"); put("DEVICE", "NX769J"); put("MANUFACTURER", "ZTE"); put("MODEL", "NX769J");
        }});
        SPOOF_PROPS.put("com.CarXTech.highWay", new HashMap<String, String>() {{
            put("BRAND", "ZTE"); put("DEVICE", "NX769J"); put("MANUFACTURER", "ZTE"); put("MODEL", "NX769J");
        }});
        SPOOF_PROPS.put("com.nekki.shadowfight3", new HashMap<String, String>() {{
            put("BRAND", "ZTE"); put("DEVICE", "NX769J"); put("MANUFACTURER", "ZTE"); put("MODEL", "NX769J");
        }});
        SPOOF_PROPS.put("com.nekki.shadowfightarena", new HashMap<String, String>() {{
            put("BRAND", "ZTE"); put("DEVICE", "NX769J"); put("MANUFACTURER", "ZTE"); put("MODEL", "NX769J");
        }});
        SPOOF_PROPS.put("com.gameloft.android.ANMP.GloftA8HM", new HashMap<String, String>() {{
            put("BRAND", "ZTE"); put("DEVICE", "NX769J"); put("MANUFACTURER", "ZTE"); put("MODEL", "NX769J");
        }});
        SPOOF_PROPS.put("com.nekki.shadowfight", new HashMap<String, String>() {{
            put("BRAND", "ZTE"); put("DEVICE", "NX769J"); put("MANUFACTURER", "ZTE"); put("MODEL", "NX769J");
        }});
        SPOOF_PROPS.put("com.ea.game.nfs14_row", new HashMap<String, String>() {{
            put("BRAND", "ZTE"); put("DEVICE", "NX769J"); put("MANUFACTURER", "ZTE"); put("MODEL", "NX769J");
        }});
        SPOOF_PROPS.put("com.ea.games.r3_row", new HashMap<String, String>() {{
            put("BRAND", "ZTE"); put("DEVICE", "NX769J"); put("MANUFACTURER", "ZTE"); put("MODEL", "NX769J");
        }});
        SPOOF_PROPS.put("com.supercell.squad", new HashMap<String, String>() {{
            put("BRAND", "ZTE"); put("DEVICE", "NX769J"); put("MANUFACTURER", "ZTE"); put("MODEL", "NX769J");
        }});
        SPOOF_PROPS.put("com.blitzteam.battleprime", new HashMap<String, String>() {{
            put("BRAND", "ZTE"); put("DEVICE", "NX769J"); put("MANUFACTURER", "ZTE"); put("MODEL", "NX769J");
        }});
        SPOOF_PROPS.put("com.tencent.tmgp.gnyx", new HashMap<String, String>() {{
            put("BRAND", "ZTE"); put("DEVICE", "NX769J"); put("MANUFACTURER", "ZTE"); put("MODEL", "NX769J");
        }});

        SPOOF_PROPS.put("com.proximabeta.mf.uamo", new HashMap<String, String>() {{
            put("BRAND", "Black Shark"); put("DEVICE", "Black Shark 4"); put("MANUFACTURER", "Xiaomi"); put("MODEL", "2SM-X706B");
        }});

        SPOOF_PROPS.put("com.ea.gp.apexlegendsmobilefps", new HashMap<String, String>() {{
            put("BRAND", "Xiaomi"); put("DEVICE", "Mi 11T PRO"); put("MANUFACTURER", "Xiaomi"); put("MODEL", "2107113SI");
        }});
        SPOOF_PROPS.put("com.levelinfinite.hotta.gp", new HashMap<String, String>() {{
            put("BRAND", "Xiaomi"); put("DEVICE", "Mi 11T PRO"); put("MANUFACTURER", "Xiaomi"); put("MODEL", "2107113SI");
        }});
        SPOOF_PROPS.put("com.supercell.clashofclans", new HashMap<String, String>() {{
            put("BRAND", "Xiaomi"); put("DEVICE", "Mi 11T PRO"); put("MANUFACTURER", "Xiaomi"); put("MODEL", "2107113SI");
        }});
        SPOOF_PROPS.put("com.vng.mlbbvn", new HashMap<String, String>() {{
            put("BRAND", "Xiaomi"); put("DEVICE", "Mi 11T PRO"); put("MANUFACTURER", "Xiaomi"); put("MODEL", "2107113SI");
        }});

        SPOOF_PROPS.put("com.levelinfinite.sgameGlobal", new HashMap<String, String>() {{
            put("BRAND", "Xiaomi"); put("DEVICE", "Mi 13 Pro"); put("MANUFACTURER", "Xiaomi"); put("MODEL", "2210132G");
        }});
        SPOOF_PROPS.put("com.tencent.tmgp.sgame", new HashMap<String, String>() {{
            put("BRAND", "Xiaomi"); put("DEVICE", "Mi 13 Pro"); put("MANUFACTURER", "Xiaomi"); put("MODEL", "2210132G");
        }});
        SPOOF_PROPS.put("com.pubg.krmobile", new HashMap<String, String>() {{
            put("BRAND", "Xiaomi"); put("DEVICE", "Mi 13 Pro"); put("MANUFACTURER", "Xiaomi"); put("MODEL", "2210132G");
        }});
        SPOOF_PROPS.put("com.rekoo.pubgm", new HashMap<String, String>() {{
            put("BRAND", "Xiaomi"); put("DEVICE", "Mi 13 Pro"); put("MANUFACTURER", "Xiaomi"); put("MODEL", "2210132G");
        }});
        SPOOF_PROPS.put("com.tencent.tmgp.pubgmhd", new HashMap<String, String>() {{
            put("BRAND", "Xiaomi"); put("DEVICE", "Mi 13 Pro"); put("MANUFACTURER", "Xiaomi"); put("MODEL", "2210132G");
        }});
        SPOOF_PROPS.put("com.vng.pubgmobile", new HashMap<String, String>() {{
            put("BRAND", "Xiaomi"); put("DEVICE", "Mi 13 Pro"); put("MANUFACTURER", "Xiaomi"); put("MODEL", "2210132G");
        }});
        SPOOF_PROPS.put("com.pubg.imobile", new HashMap<String, String>() {{
            put("BRAND", "Xiaomi"); put("DEVICE", "Mi 13 Pro"); put("MANUFACTURER", "Xiaomi"); put("MODEL", "2210132G");
        }});
        SPOOF_PROPS.put("com.tencent.ig", new HashMap<String, String>() {{
            put("BRAND", "Xiaomi"); put("DEVICE", "Mi 13 Pro"); put("MANUFACTURER", "Xiaomi"); put("MODEL", "2210132G");
        }});

        SPOOF_PROPS.put("com.netease.lztgglobal", new HashMap<String, String>() {{
            put("BRAND", "OnePlus"); put("DEVICE", "OnePlus 8 PRO"); put("MANUFACTURER", "OnePlus"); put("MODEL", "IN2020");
        }});
        SPOOF_PROPS.put("com.riotgames.league.wildrift", new HashMap<String, String>() {{
            put("BRAND", "OnePlus"); put("DEVICE", "OnePlus 8 PRO"); put("MANUFACTURER", "OnePlus"); put("MODEL", "IN2020");
        }});
        SPOOF_PROPS.put("com.riotgames.league.wildrifttw", new HashMap<String, String>() {{
            put("BRAND", "OnePlus"); put("DEVICE", "OnePlus 8 PRO"); put("MANUFACTURER", "OnePlus"); put("MODEL", "IN2020");
        }});
        SPOOF_PROPS.put("com.riotgames.league.wildriftvn", new HashMap<String, String>() {{
            put("BRAND", "OnePlus"); put("DEVICE", "OnePlus 8 PRO"); put("MANUFACTURER", "OnePlus"); put("MODEL", "IN2020");
        }});

        SPOOF_PROPS.put("com.epicgames.fortnite", new HashMap<String, String>() {{
            put("BRAND", "OnePlus"); put("DEVICE", "OnePlus 9 PRO"); put("MANUFACTURER", "OnePlus"); put("MODEL", "LE2101");
        }});
        SPOOF_PROPS.put("com.epicgames.portal", new HashMap<String, String>() {{
            put("BRAND", "OnePlus"); put("DEVICE", "OnePlus 9 PRO"); put("MANUFACTURER", "OnePlus"); put("MODEL", "LE2101");
        }});
        SPOOF_PROPS.put("com.tencent.lolm", new HashMap<String, String>() {{
            put("BRAND", "OnePlus"); put("DEVICE", "OnePlus 9 PRO"); put("MANUFACTURER", "OnePlus"); put("MODEL", "LE2101");
        }});

        SPOOF_PROPS.put("com.mobile.legends", new HashMap<String, String>() {{
            put("BRAND", "POCO"); put("DEVICE", "POCO F5"); put("MANUFACTURER", "Xiaomi"); put("MODEL", "23049PCD8G");
        }});

        SPOOF_PROPS.put("com.dts.freefireth", new HashMap<String, String>() {{
            put("BRAND", "Asus"); put("DEVICE", "ROG Phone"); put("MANUFACTURER", "Asus"); put("MODEL", "ASUS_Z01QD");
        }});
        SPOOF_PROPS.put("com.dts.freefirethmax", new HashMap<String, String>() {{
            put("BRAND", "Asus"); put("DEVICE", "ROG Phone"); put("MANUFACTURER", "Asus"); put("MODEL", "ASUS_Z01QD");
        }});

        SPOOF_PROPS.put("com.ea.gp.fifamobile", new HashMap<String, String>() {{
            put("BRAND", "Asus"); put("DEVICE", "ROG Phone 6"); put("MANUFACTURER", "Asus"); put("MODEL", "ASUS_AI2201");
        }});
        SPOOF_PROPS.put("com.gameloft.android.ANMP.GloftA9HM", new HashMap<String, String>() {{
            put("BRAND", "Asus"); put("DEVICE", "ROG Phone 6"); put("MANUFACTURER", "Asus"); put("MODEL", "ASUS_AI2201");
        }});
        SPOOF_PROPS.put("com.madfingergames.legends", new HashMap<String, String>() {{
            put("BRAND", "Asus"); put("DEVICE", "ROG Phone 6"); put("MANUFACTURER", "Asus"); put("MODEL", "ASUS_AI2201");
        }});
        SPOOF_PROPS.put("com.pearlabyss.blackdesertm", new HashMap<String, String>() {{
            put("BRAND", "Asus"); put("DEVICE", "ROG Phone 6"); put("MANUFACTURER", "Asus"); put("MODEL", "ASUS_AI2201");
        }});
        SPOOF_PROPS.put("com.pearlabyss.blackdesertm.gl", new HashMap<String, String>() {{
            put("BRAND", "Asus"); put("DEVICE", "ROG Phone 6"); put("MANUFACTURER", "Asus"); put("MODEL", "ASUS_AI2201");
        }});

        SPOOF_PROPS.put("com.activision.callofduty.shooter", new HashMap<String, String>() {{
            put("BRAND", "Lenovo"); put("DEVICE", "TB-9707F"); put("MANUFACTURER", "Lenovo"); put("MODEL", "Lenovo TB-9707F");
        }});
        SPOOF_PROPS.put("com.garena.game.codm", new HashMap<String, String>() {{
            put("BRAND", "Lenovo"); put("DEVICE", "TB-9707F"); put("MANUFACTURER", "Lenovo"); put("MODEL", "Lenovo TB-9707F");
        }});
        SPOOF_PROPS.put("com.tencent.tmgp.kr.codm", new HashMap<String, String>() {{
            put("BRAND", "Lenovo"); put("DEVICE", "TB-9707F"); put("MANUFACTURER", "Lenovo"); put("MODEL", "Lenovo TB-9707F");
        }});
        SPOOF_PROPS.put("com.vng.codmvn", new HashMap<String, String>() {{
            put("BRAND", "Lenovo"); put("DEVICE", "TB-9707F"); put("MANUFACTURER", "Lenovo"); put("MODEL", "Lenovo TB-9707F");
        }});

        SPOOF_PROPS.put("com.ea.gp.fifamobile", new HashMap<String, String>() {{
            put("BRAND", "samsung"); put("DEVICE", "e3q"); put("MANUFACTURER", "samsung"); put("MODEL", "SM-S928B");
        }});
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if (moduleUnloaded) {
            XposedBridge.log("GAMEUNLOCKER: Module already unloaded, skipping");
            return;
        }

        String packageName = lpparam.packageName;

        if (SPOOF_PROPS.containsKey(packageName)) {
            try {
                XposedBridge.log("GAMEUNLOCKER: Starting spoofing process for " + packageName);

                performEarlySpoofing(lpparam, packageName);
                performDeepHiding(lpparam);
                performAdvancedSpoofing(lpparam, packageName);
                initiateSelfDestruct(lpparam, packageName);
                
                XposedBridge.log("GAMEUNLOCKER: Spoofing pipeline completed for " + packageName);
                
            } catch (Exception e) {
                XposedBridge.log("GAMEUNLOCKER: Critical error in spoofing pipeline: " + e.getMessage());
                forceModuleUnload();
            }
        }
    }

    private void performEarlySpoofing(XC_LoadPackage.LoadPackageParam lpparam, String packageName) {
        Map<String, String> props = SPOOF_PROPS.get(packageName);
        
        for (Map.Entry<String, String> entry : props.entrySet()) {
            setBuildFieldDirectly(entry.getKey(), entry.getValue());
        }
        
        setBuildFieldDirectly("PRODUCT", props.get("DEVICE"));
        setBuildFieldDirectly("FINGERPRINT", generateFingerprint(props));
        
        XposedBridge.log("GAMEUNLOCKER: Early spoofing completed for " + packageName);
    }

    private void performDeepHiding(XC_LoadPackage.LoadPackageParam lpparam) {
        XposedHelpers.findAndHookMethod("java.lang.ClassLoader", lpparam.classLoader,
            "loadClass", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    if (moduleUnloaded) return;
                    
                    String className = (String) param.args[0];
                    if (className != null && isXposedRelatedClass(className)) {
                        param.setThrowable(new ClassNotFoundException("Security violation: Class access denied"));
                    }
                }
            });

        XposedHelpers.findAndHookMethod("java.lang.Thread", lpparam.classLoader,
            "getStackTrace", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    if (moduleUnloaded) return;
                    
                    StackTraceElement[] stack = (StackTraceElement[]) param.getResult();
                    StackTraceElement[] cleanedStack = Arrays.stream(stack)
                        .filter(e -> !isXposedClassName(e.getClassName()))
                        .toArray(StackTraceElement[]::new);
                    param.setResult(cleanedStack);
                }
            });

        XposedHelpers.findAndHookMethod("java.lang.Class", lpparam.classLoader,
            "forName", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    if (moduleUnloaded) return;
                    
                    String className = (String) param.args[0];
                    if (className != null && isXposedRelatedClass(className)) {
                        param.setThrowable(new ClassNotFoundException("Class not accessible"));
                    }
                }
            });

        XposedHelpers.findAndHookMethod("java.io.File", lpparam.classLoader,
            "exists", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    if (moduleUnloaded) return;
                    
                    String path = param.thisObject.toString();
                    if (isSuspiciousPath(path)) {
                        param.setResult(false);
                    }
                }
            });

        XposedBridge.log("GAMEUNLOCKER: Deep hiding mechanisms installed");
    }

    private void performAdvancedSpoofing(XC_LoadPackage.LoadPackageParam lpparam, String packageName) {
        final Map<String, String> props = SPOOF_PROPS.get(packageName);

        XposedHelpers.findAndHookMethod("android.os.Build", lpparam.classLoader,
            "getString", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    if (moduleUnloaded) return;
                    
                    String key = (String) param.args[0];
                    if (props.containsKey(key)) {
                        param.setResult(props.get(key));
                    }
                }
            });

        XposedHelpers.findAndHookMethod("android.os.SystemProperties", lpparam.classLoader,
            "get", String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    if (moduleUnloaded) return;
                    
                    String key = (String) param.args[0];
                    if ("ro.product.brand".equals(key)) param.setResult(props.get("BRAND"));
                    else if ("ro.product.manufacturer".equals(key)) param.setResult(props.get("MANUFACTURER"));
                    else if ("ro.product.device".equals(key)) param.setResult(props.get("DEVICE"));
                    else if ("ro.product.model".equals(key)) param.setResult(props.get("MODEL"));
                    else if ("ro.build.fingerprint".equals(key)) param.setResult(generateFingerprint(props));
                    else if ("ro.build.product".equals(key)) param.setResult(props.get("DEVICE"));
                    else if ("ro.product.name".equals(key)) param.setResult(props.get("PRODUCT"));
                }
            });

        String[] buildFields = {"BRAND", "MANUFACTURER", "DEVICE", "MODEL", "PRODUCT", "FINGERPRINT"};
        for (final String field : buildFields) {
            if (props.containsKey(field) || field.equals("FINGERPRINT") || field.equals("PRODUCT")) {
                try {
                    final String fieldValue = field.equals("FINGERPRINT") ? generateFingerprint(props) : 
                                            field.equals("PRODUCT") ? props.get("DEVICE") : props.get(field);
                    
                    XposedHelpers.findAndHookMethod(Build.class, field, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            if (moduleUnloaded) return;
                            param.setResult(fieldValue);
                        }
                    });
                } catch (Exception e) {
                }
            }
        }

        XposedHelpers.findAndHookMethod("android.os.Build$VERSION", lpparam.classLoader,
            "getRelease", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    if (moduleUnloaded) return;
                    param.setResult("13");
                }
            });

        XposedBridge.log("GAMEUNLOCKER: Advanced spoofing hooks installed for " + packageName);
    }

    private void initiateSelfDestruct(XC_LoadPackage.LoadPackageParam lpparam, String packageName) {
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                
                XposedBridge.log("GAMEUNLOCKER: Starting self-destruct sequence for " + packageName);
                
                moduleUnloaded = true;
                cleanupMemory();
                clearAllReferences();
                clearLogTraces();
                
                XposedBridge.log("GAMEUNLOCKER: Self-destruct completed for " + packageName);
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                forceModuleUnload();
            } catch (Exception e) {
                forceModuleUnload();
            }
        }, "SelfDestructThread-" + packageName).start();
    }

    private void cleanupMemory() {
        try {
            SPOOF_PROPS.clear();
            System.gc();
            System.runFinalization();
            
        } catch (Exception e) {
        }
    }

    private void clearAllReferences() {
        try {
            Field[] fields = getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.getName().equals("moduleUnloaded")) {
                    field.setAccessible(true);
                    if (!field.getType().isPrimitive()) {
                        field.set(null, null);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    private void clearLogTraces() {
        try {
        } catch (Exception e) {
        }
    }

    private void forceModuleUnload() {
        moduleUnloaded = true;
        try {
            SPOOF_PROPS.clear();
            System.gc();
        } catch (Exception e) {
        }
    }

    private boolean isXposedRelatedClass(String className) {
        return className.contains("Xposed") || 
               className.contains("de.robv") ||
               className.contains("xposed") ||
               className.contains("XposedBridge") ||
               className.contains("IXposedHook") ||
               (className.contains("gameunlocker") && !className.contains("GAMEUNLOCKER"));
    }

    private boolean isXposedClassName(String className) {
        return className.contains("Xposed") || 
               className.contains("de.robv") ||
               className.contains("xposed") ||
               className.contains("GAMEUNLOCKER");
    }

    private boolean isSuspiciousPath(String path) {
        return path.contains("xposed") ||
               path.contains("Xposed") ||
               path.contains("magisk") ||
               path.contains("su") ||
               path.contains("busybox");
    }

    private String generateFingerprint(Map<String, String> props) {
        String brand = props.get("BRAND");
        String device = props.get("DEVICE");
        return brand + "/" + device + "/" + device + ":13/TQ3A.230805.001/eng.root.20231201:user/release-keys";
    }

    private static void setBuildFieldDirectly(String fieldName, String value) {
        try {
            Field field = Build.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(null, value);
            field.setAccessible(false);
        } catch (NoSuchFieldException e) {
        } catch (Exception e) {
            XposedBridge.log("GAMEUNLOCKER: Failed to set " + fieldName + ": " + e.getMessage());
        }
    }

    public static boolean isModuleActive() {
        return !moduleUnloaded;
    }

    public static void resetModule() {
        moduleUnloaded = false;
    }
}
