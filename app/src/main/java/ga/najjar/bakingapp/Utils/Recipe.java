package ga.najjar.bakingapp.Utils;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "recipe")
public class Recipe {

    public Recipe(int id,String name,int servings, String image)
    {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

    @Ignore
    public Recipe()
    {

    }
    @PrimaryKey
    private int id;
    private String name;

    private int servings;
    private String image;

    @Ignore
    private Step [] steps;
    @Ignore
    private Ingredient [] ingredients;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }

    public Step[] getSteps() {
        return steps;
    }

    public void setSteps(Step[] steps) {
        this.steps = steps;
    }
}




