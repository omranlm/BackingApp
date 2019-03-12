package ga.najjar.bakingapp.DB;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import ga.najjar.bakingapp.Utils.Ingredient;



@Dao
public interface IngredientDao {

    @Query("SELECT * FROM ingredient WHERE recipeId = :recipeId")
    List<Ingredient> loadIngredient(int recipeId);

    @Insert
    void insertIngredient(Ingredient ingredient);
}
