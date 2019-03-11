package ga.najjar.bakingapp.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecipeJSONUtil {


    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SERVINGS = "servings";
    private static final String IMAGE = "ımage";
    private static final String INGREDIENTS = "ingredients";
    private static final String MEASURE = "measure" ;
    private static final String INGREDIENT = "ingredient";
    private static final String QUANTITY = "quantity";
    private static final String STEPS = "steps";
    private static final String SHORTDESCRIPTION = "shortDescription";
    private static final String DESCRIPTION = "description";
    private static final String VIDEOURL = "videoURL";
    private static final String THUMBNAILURL = "thumbnailURL";

    public static Recipe[] parseRecipe(String recipeString) throws JSONException {

        JSONArray recipeArray = new JSONArray(recipeString);

        if (recipeArray.equals("") || recipeArray.length() == 0) return null;

        Recipe[] recipes = new Recipe[recipeArray.length()];

        for (int i = 0; i < recipes.length ; i++) {
            Recipe recipe = new Recipe();
            JSONObject recipeJSONObject = recipeArray.getJSONObject(i);

            String attribute = ID;
            if (recipeJSONObject.has(attribute) && !recipeJSONObject.isNull(attribute)&& !recipeJSONObject.getString(attribute).equals("null") && !recipeJSONObject.getString(attribute).isEmpty())
            {
                recipe.setId(recipeJSONObject.getInt(attribute));
            }

            attribute = NAME;
            if (recipeJSONObject.has(attribute) && !recipeJSONObject.isNull(attribute)&& !recipeJSONObject.getString(attribute).equals("null") && !recipeJSONObject.getString(attribute).isEmpty())
            {
                recipe.setName(recipeJSONObject.getString(attribute));
            }

            attribute = SERVINGS;
            if (recipeJSONObject.has(attribute) && !recipeJSONObject.isNull(attribute)&& !recipeJSONObject.getString(attribute).equals("null") && !recipeJSONObject.getString(attribute).isEmpty())
            {
                recipe.setServings(recipeJSONObject.getInt(attribute));
            }

            attribute = IMAGE;
            if (recipeJSONObject.has(attribute) && !recipeJSONObject.isNull(attribute)&& !recipeJSONObject.getString(attribute).equals("null") && !recipeJSONObject.getString(attribute).isEmpty())
            {
                recipe.setImage(recipeJSONObject.getString(attribute));
            }

            attribute = STEPS;
            if (recipeJSONObject.has(attribute) && !recipeJSONObject.isNull(attribute)&& !recipeJSONObject.getString(attribute).equals("null") && !recipeJSONObject.getString(attribute).isEmpty())
            {
                JSONArray stepsArray =recipeJSONObject.getJSONArray(attribute);

                Step [] steps = new Step[stepsArray.length()];

                for (int j = 0; j < steps.length; j++) {

                    JSONObject stepJSONObject = stepsArray.getJSONObject(i);
                    Step step = new Step();

                    attribute = ID;
                    if (stepJSONObject.has(attribute) && !stepJSONObject.isNull(attribute)&& !stepJSONObject.getString(attribute).equals("null") && !stepJSONObject.getString(attribute).isEmpty())
                    {
                        step.setId(stepJSONObject.getInt(attribute));
                    }

                    attribute = SHORTDESCRIPTION;
                    if (stepJSONObject.has(attribute) && !stepJSONObject.isNull(attribute)&& !stepJSONObject.getString(attribute).equals("null") && !stepJSONObject.getString(attribute).isEmpty())
                    {
                        step.setShortDescription(stepJSONObject.getString(attribute));
                    }

                    attribute = DESCRIPTION;
                    if (stepJSONObject.has(attribute) && !stepJSONObject.isNull(attribute)&& !stepJSONObject.getString(attribute).equals("null") && !stepJSONObject.getString(attribute).isEmpty())
                    {
                        step.setDescription(stepJSONObject.getString(attribute));
                    }
                    attribute = VIDEOURL;
                    if (stepJSONObject.has(attribute) && !stepJSONObject.isNull(attribute)&& !stepJSONObject.getString(attribute).equals("null") && !stepJSONObject.getString(attribute).isEmpty())
                    {
                        step.setVideoURL(stepJSONObject.getString(attribute));
                    }

                    attribute = THUMBNAILURL;
                    if (stepJSONObject.has(attribute) && !stepJSONObject.isNull(attribute)&& !stepJSONObject.getString(attribute).equals("null") && !stepJSONObject.getString(attribute).isEmpty())
                    {
                        step.setThumbnailURL(stepJSONObject.getString(attribute));
                    }
                    steps[j] = step;
                }
                recipe.setSteps(steps);
            }

            attribute = INGREDIENTS;
            if (recipeJSONObject.has(attribute) && !recipeJSONObject.isNull(attribute)&& !recipeJSONObject.getString(attribute).equals("null") && !recipeJSONObject.getString(attribute).isEmpty())
            {
                JSONArray ingredientsArray =recipeJSONObject.getJSONArray(attribute);
                Ingredient [] ingredients = new Ingredient[ingredientsArray.length()];
                for (int j = 0; j < ingredients.length; j++) {

                    JSONObject ingredientJSONObject = ingredientsArray.getJSONObject(i);
                    Ingredient ingredient = new Ingredient();

                    attribute = MEASURE;
                    if (ingredientJSONObject.has(attribute) && !ingredientJSONObject.isNull(attribute)&& !ingredientJSONObject.getString(attribute).equals("null") && !ingredientJSONObject.getString(attribute).isEmpty())
                    {
                        ingredient.setMeasure(ingredientJSONObject.getString(attribute));
                    }


                    attribute = INGREDIENT;
                    if (ingredientJSONObject.has(attribute) && !ingredientJSONObject.isNull(attribute)&& !ingredientJSONObject.getString(attribute).equals("null") && !ingredientJSONObject.getString(attribute).isEmpty())
                    {
                        ingredient.setIngredient(ingredientJSONObject.getString(attribute));
                    }

                    attribute = QUANTITY;
                    if (ingredientJSONObject.has(attribute) && !ingredientJSONObject.isNull(attribute)&& !ingredientJSONObject.getString(attribute).equals("null") && !ingredientJSONObject.getString(attribute).isEmpty())
                    {
                        ingredient.setQuantity(ingredientJSONObject.getInt(attribute));
                    }

                    ingredients[j] = ingredient;
                }

                recipe.setIngredients(ingredients);
            }

            recipes[i] = recipe;
        }


        return recipes;

    }
}
