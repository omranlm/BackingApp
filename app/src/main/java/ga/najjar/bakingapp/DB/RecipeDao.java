package ga.najjar.bakingapp.DB;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import ga.najjar.bakingapp.Utils.Recipe;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe")
    List<Recipe> loadRecipe();

    @Insert
    void insertRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);

}
