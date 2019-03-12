package ga.najjar.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ga.najjar.bakingapp.Utils.Recipe;


public class RecipeCardAdapter extends ArrayAdapter<Recipe> {

    private Context mContext;
    private List<Recipe> recipes;




    public RecipeCardAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        mContext = context;
    }

    public RecipeCardAdapter(List<Recipe> data, Context context) {
        super(context, R.layout.recipe_card_item, data);

        this.recipes = data;
        this.mContext=context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        Recipe recipe = recipes.get(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.recipe_card_item, parent, false);

        TextView recipeName = (TextView)view.findViewById(R.id.recipe_name);
        recipeName.setText(recipe.getName());

        // TODO load the image if exists

        return view;
    }
}
