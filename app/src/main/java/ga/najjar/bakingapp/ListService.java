package ga.najjar.bakingapp;

import android.content.Context;
import android.content.Intent;

import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


public class ListService extends RemoteViewsService {

    private String [] ingredients;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        int numberOfIng = intent.getIntExtra("count",0);
        ingredients = new String[numberOfIng];
        for (int i = 0; i < ingredients.length; i++) {
            ingredients[i] = intent.getStringExtra("ingredient"+ i);
        }
        return new ListViewsFactory(this.getApplicationContext(),ingredients);
    }

    @Override
    public IBinder onBind(Intent intent) {

        int numberOfIng = intent.getIntExtra("count",0);
        ingredients = new String[numberOfIng];
        for (int i = 0; i < ingredients.length; i++) {
            ingredients[i] = intent.getStringExtra("ingredient"+ i);
        }
        return super.onBind(intent);


    }
}
class ListViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private  Context mContext;

    private String [] ingredients;
    public ListViewsFactory(Context applicationContext, String [] _ingredients) {

        mContext = applicationContext;
        this.ingredients =  _ingredients;
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