package com.wills.carl.bakingrecipes;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    static AppWidgetManager manager;
    static int appId;
    static RemoteViews views;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);


        manager = appWidgetManager;
        appId = appWidgetId;

        //Create the pending intent to use when the widget is clicked
        //Just create main activity when widget is clicked
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        // Tell the widget manager to update the widget
        updateWidg(context, appWidgetManager, appWidgetId, views);
    }

    private static void updateWidg(Context context, AppWidgetManager appWidgetManager, int appWidgetId, RemoteViews views) {
        SharedPreferences prefs = context.getSharedPreferences("Prefs", 0);
        String currentIngreds = prefs.getString("ingreds", "Cannot retrieve ingredients for this recipe.");

        views.setTextViewText(R.id.appwidget_text, currentIngreds);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // When there are multiple widgets active...
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    public static class WidgetBroadcastReceiver extends android.content.BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            updateWidg(context, manager, appId, views);
        }
    }
}

