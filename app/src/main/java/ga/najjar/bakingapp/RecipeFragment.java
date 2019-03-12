package ga.najjar.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ga.najjar.bakingapp.Utils.Utils;

public class RecipeFragment extends Fragment {

    OnRecipeClickListener mCallback;

    public interface OnRecipeClickListener {
        void onRecipeCardClick(int position);
    }

    public RecipeFragment()
    {


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mCallback = (OnRecipeClickListener) context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipes,container,false);

        ListView listView = root.findViewById(R.id.lv_recipes);


        // TODO set the adapter to get all recipes
        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(Utils.Recipes,getContext());
        listView.setAdapter(recipeCardAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onRecipeCardClick(position);
            }
        });

        return root;
    }
}
