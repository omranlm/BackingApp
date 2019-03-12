package ga.najjar.bakingapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import ga.najjar.bakingapp.DB.AppDatabase;
import ga.najjar.bakingapp.Utils.Ingredient;
import ga.najjar.bakingapp.Utils.NetworkUtilities;
import ga.najjar.bakingapp.Utils.Recipe;
import ga.najjar.bakingapp.Utils.RecipeJSONUtil;
import ga.najjar.bakingapp.Utils.Step;
import ga.najjar.bakingapp.Utils.Utils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> , RecipeFragment.OnRecipeClickListener {

    private int LOADER_ID = 22;
    private AppDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getInstance(getApplicationContext());

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

                    updateDB(recipes);

                    Log.d("recipes.length ",String.valueOf(recipes.length));

                    RecipeFragment recipeFragment = new RecipeFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.recipe_fragment_layout,recipeFragment)
                            .commit();


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


    private void updateDB(final Recipe[] recipes) {

        Thread update = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Recipe> roomRecipes = mDb.recipeDao().loadRecipe();

                Utils.Recipes = roomRecipes;

                for (Recipe rec: roomRecipes) {
                    mDb.recipeDao().deleteRecipe(rec);
                }

                // update the DB
                for (Recipe rec :recipes) {

                    mDb.recipeDao().insertRecipe(rec);

                    for (Step step: rec.getSteps()) {
                        mDb.stepDao().insertStep(step);
                    }

                    for (Ingredient ing : rec.getIngredients()) {
                        mDb.ingredientDao().insertIngredient(ing);
                    }

                }
            }
        });

        update.start();

    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    public void onRecipeCardClick(int position) {

        // TODO call child activity or load details fragment
        Toast.makeText(this, " clicked " + position,Toast.LENGTH_LONG).show();
    }
}
