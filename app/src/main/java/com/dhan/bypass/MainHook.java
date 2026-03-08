package com.dhan.bypass;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class MainHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        // Target hanya aplikasi ArduinoDroid
        if (!lpparam.packageName.equals("name.antonsmirnov.android.arduinodroid2")) return;

        // Bypass Status Pembelian (1 = PURCHASED)
        XposedHelpers.findAndHookMethod("com.android.billingclient.api.Purchase", 
            lpparam.classLoader, "getPurchaseState", new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) {
                    return 1; 
                }
        });

        // Bypass Respon Billing (0 = OK)
        XposedHelpers.findAndHookMethod("com.android.billingclient.api.BillingResult", 
            lpparam.classLoader, "getResponseCode", new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) {
                    return 0;
                }
        });
    }
}
