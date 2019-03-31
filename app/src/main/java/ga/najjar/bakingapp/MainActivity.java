package ga.najjar.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
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
import ga.najjar.bakingapp.Utils.SimpleIdlingResource;
import ga.najjar.bakingapp.Utils.Step;
import ga.najjar.bakingapp.Utils.Utils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, RecipeFragment.OnRecipeClickListener {

    private AppDatabase mDb;
    private Recipe[] recipes;
    private boolean mTwoPanelMode;
    private DetailsFragment detailsFragment;
    private ProgressBar mLoadingPB;
    @Nullable private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingPB = findViewById(R.id.pb_loading);
        mIdlingResource = getIdlingResource();

        mIdlingResource.setIdleState(false);
        mDb = AppDatabase.getInstance(getApplicationContext());


        loadRecipes();

        if (findViewById(R.id.detail_fragment_layout) != null) {
            mTwoPanelMode = true;
        }
        Bundle currentState = savedInstanceState;
    }
    @VisibleForTesting
    public SimpleIdlingResource getIdlingResource()
    {
        if (mIdlingResource == null)
            return new SimpleIdlingResource();

        return mIdlingResource;

    }
    private void loadRecipes() {
        if (NetworkUtilities.isOnline(this)) {
            LoaderManager loaderManager = getSupportLoaderManager();
            int LOADER_ID = 22;
            Loader<String> dataLoader = loaderManager.getLoader(LOADER_ID);
            if (dataLoader == null)
                loaderManager.initLoader(LOADER_ID, null, this).forceLoad();
            else
                mLoadingPB.setVisibility(View.INVISIBLE);
            // TODO restart the loader when a referesh button is clicked
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

                    recipes = RecipeJSONUtil.parseRecipe(results);

                    //
                    Utils.Recipes = Arrays.asList(recipes);

                    updateDB(recipes);


                    Log.d("recipes.length ", String.valueOf(recipes.length));

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

                for (Recipe rec : roomRecipes) {
                    mDb.recipeDao().deleteRecipe(rec);
                }

                // update the DB
                for (Recipe rec : recipes) {

                    mDb.recipeDao().insertRecipe(rec);

                    for (Step step : rec.getSteps()) {
                        mDb.stepDao().insertStep(step);
                    }

                    for (Ingredient ing : rec.getIngredients()) {
                        mDb.ingredientDao().insertIngredient(ing);
                    }

                }
                updateRecipeFragment();
            }
        });

        update.start();

    }

    private void updateRecipeFragment() {

        RecipeFragment recipeFragment = new RecipeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipe_fragment_layout, recipeFragment)
                .commitAllowingStateLoss();

        mIdlingResource.setIdleState(true);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        mLoadingPB.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

        if (Utils.Recipes != null)
        {
            updateRecipeFragment();
        }
    }

    @Override
    public void onRecipeCardClick(int position) {

        if (mTwoPanelMode) {
            detailsFragment = new DetailsFragment();

            int recipeId = Utils.Recipes.get(position).getId();
            Bundle bundle = new Bundle();
            bundle.putInt("recipeId", recipeId);

            detailsFragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.detail_fragment_layout, detailsFragment)
                    .commit();

        }
        // TODO call child activity or load details fragment
        else {
            Toast.makeText(this, " clicked " + position, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("recipeId", Utils.Recipes.get(position).getId());

            startActivity(intent);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (detailsFragment != null && mTwoPanelMode) {
            detailsFragment.destroyPlayer();
            getSupportFragmentManager().beginTransaction().remove(detailsFragment).commitAllowingStateLoss();
        }

    }
}
