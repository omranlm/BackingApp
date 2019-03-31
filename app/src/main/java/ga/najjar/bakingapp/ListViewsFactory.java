package ga.najjar.bakingapp;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import ga.najjar.bakingapp.Utils.Ingredient;

public class ListViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;

    private static String [] ingredients;

    public static void bindIngredients( Ingredient[] _ingredients) {
        ingredients = new String[_ingredients.length];
        for (int i = 0; i < _ingredients.length; i++) {
            ingredients[i] = _ingredients[i].getFullName();
        }
    }

    public ListViewsFactory(Context applicationContext) {

        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

        // set count,


    }

    @Override
    public void onDataSetChanged() {



    }


    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredients == null)
            return 0;
        else return ingredients.length;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingrediaent_widget_item);
        views.setTextViewText(R.id.tv_widget_ingredient, ingredients[position]);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}