package ga.najjar.bakingapp;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import ga.najjar.bakingapp.Utils.NetworkUtilities;
import ga.najjar.bakingapp.Utils.Recipe;
import ga.najjar.bakingapp.Utils.RecipeJSONUtil;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private int LOADER_ID = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadRecipes();
    }

    private void loadRecipes() {
        if (NetworkUtilities.isOnline(this)) {
            LoaderManager loaderManager = getSupportLoaderManager();
            Loader<String> dataLoader = loaderManager.getLoader(LOADER_ID);
            if (dataLoader == null)
                loaderManager.initLoader(LOADER_ID, null, this).forceLoad();
            else
                loaderManager.restartLoader(LOADER_ID, null, this).forceLoad();

        } else {
            // TODO Manage no internet message

        }

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {


        return new AsyncTaskLoader<String>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                // TODO show loading progress bar
            }

            @Nullable
            @Override
            public String loadInBackground() {


                URL recipesURL = NetworkUtilities.recipesURL();

                String results = null;
                try {
                    results = NetworkUtilities.getResponseFromHttpUrl(recipesURL);

                    Recipe[] recipes = RecipeJSONUtil.parseRecipe(results);

                    Log.d("recipes.length ",String.valueOf(recipes.length));

                } catch (IOException e) {
                    // TODO manage exception in getting the data
                    e.printStackTrace();
                } catch (JSONException e) {

                    // TODO manage exception in parsing the da
                    e.printStackTrace();
                }

                return results;
            }

        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
