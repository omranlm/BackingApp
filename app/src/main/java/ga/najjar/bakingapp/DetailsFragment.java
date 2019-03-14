package ga.najjar.bakingapp;

import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ga.najjar.bakingapp.Utils.Ingredient;
import ga.najjar.bakingapp.Utils.Recipe;
import ga.najjar.bakingapp.Utils.Utils;

public class DetailsFragment extends Fragment {



    public DetailsFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Recipe selectedRecipe = null;
        Bundle bundle = this.getArguments();

        int recipeId = bundle.getInt("recipeId");//TODO remove the static text

        for (Recipe res: Utils.Recipes
        ) {
            if (res.getId() == recipeId)
                selectedRecipe = res;

        }

        View root = inflater.inflate(R.layout.fragment_details,container,false);
        // Code from https://developer.android.com/guide/topics/ui/layout/recyclerview
        RecyclerView recyclerView = root.findViewById(R.id.rv_steps);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        layoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // specify an adapter
        StepAdapter stepAdapter = new StepAdapter(getContext(),Arrays.asList(selectedRecipe.getSteps()));
        recyclerView.setAdapter(stepAdapter);

        // TODO get steps from Room
        TextView recipeName = root.findViewById(R.id.tv_recipe_name);
        recipeName.setText(selectedRecipe.getName());

        TextView recipeIngredients = root.findViewById(R.id.tv_ingredients);
        recipeIngredients.setText(formulateIngredients(selectedRecipe.getIngredients()));

        TextView recipeServings = root.findViewById(R.id.tv_serving);
        recipeServings.setText(String.format("Servings %s people",selectedRecipe.getServings())) ;
        // TODO move static strings


        return root;
    }

    private String formulateIngredients(Ingredient[] ingredients) {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ingredients.length; i++) {

            stringBuilder.append(ingredients[i].getQuantity());
            stringBuilder.append(String.format(" %s", ingredients[i].getMeasure()));
            stringBuilder.append(String.format(" of %s", ingredients[i].getIngredient()));
            stringBuilder.append("\n");// TODO move static strings

        }
        return stringBuilder.toString();
    }
}
