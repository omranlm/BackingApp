package ga.najjar.bakingapp.Utils;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "ingredient", foreignKeys = @ForeignKey(entity = Recipe.class,
        parentColumns = "id",
        childColumns = "recipeId",
        onDelete = CASCADE))

public class Ingredient
{
    @PrimaryKey(autoGenerate = true)
    private int id; // added by Omran it is not part of the JSON

    private int quantity;
    private String measure;
    private String ingredient;
    private int recipeId;

    public Ingredient(int quantity,String measure,String ingredient,int recipeId)
    {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
        this.recipeId = recipeId;

    }

    @Ignore
    public Ingredient()
    {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getMeasure() {
        return measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Ignore
    public String getFullName() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getQuantity());
        stringBuilder.append(String.format(" %s", getMeasure()));
        stringBuilder.append(String.format(" of %s", getIngredient()));
        stringBuilder.append("\n");// TODO move static strings

        return stringBuilder.toString();
    }
}