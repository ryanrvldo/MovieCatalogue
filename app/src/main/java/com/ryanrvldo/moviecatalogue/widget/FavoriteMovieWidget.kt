package com.ryanrvldo.moviecatalogue.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import android.widget.Toast
import com.ryanrvldo.moviecatalogue.R

class FavoriteMovieWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action != null) {
            if (intent.action == TOAST_ACTION) {
                val title =
                    intent.getStringExtra(EXTRA_ITEM)
                Toast.makeText(context, title, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onEnabled(context: Context) {}
    override fun onDisabled(context: Context) {}

    companion object {
        const val TOAST_ACTION = "com.ryanrvldo.TOAST_ACTION"
        const val EXTRA_ITEM = "com.ryanrvldo.EXTRA_ITEM"
        fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val views = RemoteViews(context.packageName, R.layout.favorite_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.empty_view)
            val toastIntent = Intent(context, FavoriteMovieWidget::class.java)
            toastIntent.action = TOAST_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val toastPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        @JvmStatic
        fun updateWidget(context: Context) {
            val manager = AppWidgetManager.getInstance(context)
            val appWidgetIds =
                manager.getAppWidgetIds(ComponentName(context, FavoriteMovieWidget::class.java))
            manager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view)
        }
    }
}