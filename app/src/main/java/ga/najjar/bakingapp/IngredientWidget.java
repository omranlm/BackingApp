package ga.najjar.bakingapp;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;
import java.util.List;

import ga.najjar.bakingapp.DB.AppDatabase;
import ga.najjar.bakingapp.Utils.Ingredient;
import ga.najjar.bakingapp.Utils.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidget extends AppWidgetProvider {


    private static final String NEXT_INTENT = "nextRecipe";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe recipe, PendingIntent nextRecipePendingIntent) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);


        Intent intent = new Intent(context, ListService.class);
        ListViewsFactory.bindIngredients(recipe.getIngredients());

        views.setRemoteAdapter(R.id.lv_widget_ingredient, intent);


        views.setTextViewText(R.id.tv_widget_recipe_name, recipe.getName());
        views.setOnClickPendingIntent(R.id.tv_widget_recipe_name, nextRecipePendingIntent);
        views.setOnClickPendingIntent(R.id.tv_tap_to_change, nextRecipePendingIntent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        refreshWidgetWithRecipeId(context, appWidgetManager, appWidgetIds, 0);

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void refreshWidgetWithRecipeId(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds, final int currentRecipes) {
        new AsyncTask<Context, Void, List<Recipe>>() {
            @Override
            protected List<Recipe> doInBackground(Context... context) {

                AppDatabase mDb = AppDatabase.getInstance(context[0]);
                List<Recipe> recipes = mDb.recipeDao().loadRecipe();

                for (Recipe res : recipes) {
                    List<Ingredient> temp = mDb.ingredientDao().loadIngredient(res.getId());
                    Ingredient[] array = new Ingredient[temp.size()];
                    array = temp.toArray(array);
                    res.setIngredients(array);
                }


                return recipes;
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            protected void onPostExecute(List<Recipe> List) {
                super.onPostExecute(List);

                if (List != null) {
                    int currentRecipeIndex = currentRecipes;
                    for (int i = 0; i < appWidgetIds.length; i++) {

                        if (currentRecipes >= List.size()) {
                            currentRecipeIndex = 0;
                            Bundle whichRecipe = appWidgetManager.getAppWidgetOptions(appWidgetIds[i]);
                            whichRecipe.putInt("whichRecipe", currentRecipeIndex);
                            appWidgetManager.updateAppWidgetOptions(appWidgetIds[i], whichRecipe);
                        }

                        updateAppWidget(context, appWidgetManager, appWidgetIds[i], List.get(currentRecipeIndex), getPendingSelfIntent(context, NEXT_INTENT));
                        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds[i],R.id.lv_widget_ingredient);

                    }

                }

            }

        }.execute(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (NEXT_INTENT.equals(intent.getAction())) {


            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, IngredientWidget.class));
            //Trigger data update to handle the GridView widgets and force a data refresh

            int currentRecipeIndex = 0;
            Bundle whichRecipe = appWidgetManager.getAppWidgetOptions(appWidgetIds[0]);
            currentRecipeIndex = whichRecipe.getInt("whichRecipe") + 1;
            whichRecipe.putInt("whichRecipe", currentRecipeIndex);
            for (int i = 0; i < appWidgetIds.length; i++) {
                appWidgetManager.updateAppWidgetOptions(appWidgetIds[i], whichRecipe);
            }

            refreshWidgetWithRecipeId(context, appWidgetManager, appWidgetIds, currentRecipeIndex);
        }


    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        //got this idea from https://stackoverflow.com/questions/23220757/android-widget-onclick-listener-for-several-buttons
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}

