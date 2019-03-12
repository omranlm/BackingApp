package ga.najjar.bakingapp.DB;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import ga.najjar.bakingapp.Utils.Recipe;
import ga.najjar.bakingapp.Utils.Step;

@Dao
public interface StepDao {

    @Query("SELECT * FROM step WHERE recipeId = :recipeId ORDER BY id ASC")
    List<Step> loadSteps(int recipeId);

    @Insert
    void insertStep(Step step);

}
