package ga.najjar.bakingapp;

import android.content.Context;
import android.content.Intent;

import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import ga.najjar.bakingapp.Utils.Ingredient;


public class ListService extends RemoteViewsService {

    private static String [] ingredients;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {


        return new ListViewsFactory(this.getApplicationContext());
    }


}
