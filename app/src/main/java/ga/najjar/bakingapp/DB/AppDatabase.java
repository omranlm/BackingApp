package ga.najjar.bakingapp.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import ga.najjar.bakingapp.Utils.Ingredient;
import ga.najjar.bakingapp.Utils.Recipe;
import ga.najjar.bakingapp.Utils.Step;

@Database(entities = {Recipe.class, Step.class, Ingredient.class},version = 2,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "recipes";

    private static AppDatabase sInstance;

    public static  AppDatabase getInstance(Context context)
    {
        if(sInstance ==null)
        {
            synchronized (LOCK)
            {

                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,AppDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        // TODO remove allowMainThreadQueries
                        .build();
            }
        }
        return sInstance;
    }
    public abstract RecipeDao recipeDao();
    public abstract StepDao stepDao();
    public abstract IngredientDao ingredientDao();
}
