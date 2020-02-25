package com.example.unityplugin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PluginClass {
    Context context;
    public static PackageManager manager;
    public  static  String TAG="android jar Plugin";
    public List<AppDetail> apps;
    // public static  Context context;

    public String GetTextFromPLugin() {
        return "sadhana";
    }

    public void showLogCat() {
        Log.d("unity", "showLogCat:  unity  plugin  logcat function");
    }

    public static String GetTextFromPLuginnum() {
        Log.d(TAG, "GetTextFromPLuginnum: ");
        return "number is";
    }

    public static void LogCatMessage() {
        Log.d(TAG, "LogCatMessage: ");
    }

    public static String[] GetArrayData(int num) {
        // Creating an ArrayList
        String Data[] = {"a", "b", "c"};
        // AppDetail app = new AppDetail("appname","whatsApp");

        return Data;
    }

    public static ArrayList GetArrayElement(Context context) {
        // Creating an ArrayList
        ArrayList num = new ArrayList();
        // AppDetail app = new AppDetail("appname","whatsApp");
        num.add(1);
        num.add(2);
        return num;
    }

    public int loadApps(Context context) {
        Log.d(TAG, "loadApps:  function");
        manager = context.getPackageManager();
        apps = new ArrayList<AppDetail>();
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for (ResolveInfo ri : availableActivities) {
            AppDetail app = new AppDetail();
            app.label = ri.loadLabel(manager);
            app.name = ri.activityInfo.packageName;
            //app.icon = ri.activityInfo.loadIcon(manager);
            Log.d(TAG, "loadApps: " + app.label + app.name);
            apps.add(app);
        }
        return apps.size();

    }

    // function to get package name
    public static String[] loadAppsPackage(Context context) {
        Log.d(TAG, "load package:  function");

        manager = context.getPackageManager();
        List<String> apps_String = new ArrayList<String>();
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for (ResolveInfo ri : availableActivities) {
            //apps_String.add(ri.loadLabel(manager));
            apps_String.add(ri.activityInfo.packageName);
            Log.d("unity ", " package name: " + ri.activityInfo.packageName + ri.loadLabel(manager).toString());
            //apps.add(app);
        }
        // converting Arraylist to array  to
        String[] arr = new String[apps_String.size()];
        arr = apps_String.toArray(arr);
        return arr;

    }

    // function to get apps Name
    public static String[] loadAppsName(Context context) {
        Log.d(TAG, "loadApps:  function");
        //Context context = null;
        manager = context.getPackageManager();
        Log.d("unity", "loadAppsString: " + manager);
        List<String> apps_String = new ArrayList<String>();
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);

        for (ResolveInfo ri : availableActivities) {
            //apps_String.add(ri.loadLabel(manager));
            apps_String.add(ri.loadLabel(manager).toString());
            Log.d("unity ", "apps name: " + ri.loadLabel(manager).toString());
            //apps.add(app);
        }
        // converting Arraylist to array  to
        String[] arr = new String[apps_String.size()];
        arr = apps_String.toArray(arr);
        return arr;

    }

    public static int count_list(Context context) {
        // int listCount=loadAppsString(context).length;
        int listCount = loadAppsPackage(context).length;
        Log.d(TAG, "count_list: " + listCount);
        return listCount;

    }

    // send the specific application with launcher package// onclick event of specific Application
    public static void startApp(Context context, String Package_name) {
        Log.d(TAG, "startApp: package name receive from unity " + Package_name);
        PackageManager manager;
        manager = context.getPackageManager();
        Intent i = manager.getLaunchIntentForPackage(Package_name);
        context.startActivity(i);
    }
    // function to get image url
    public static String[] getAppIcon(Context context,int count) {
        Log.d(TAG, "getAppIcon: ");
        manager = context.getPackageManager();
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        List<String> apps_String = new ArrayList<String>();
        for (ResolveInfo ri : availableActivities) {
            AppDetail app = new AppDetail();
            // get image in Drawable
            Drawable icon = ri.activityInfo.loadIcon(manager);
            // converting to bitmap
            Bitmap bmp = drawableToBitmap(icon);

            // bmp to String
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
            Log.d("unity", "getAppIcon: "+encodedImage);
            apps_String.add(encodedImage);
        }
        // converting Arraylist to array  to
        String[] arr = new String [count];
        arr = apps_String.toArray(arr);
        return arr ;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
