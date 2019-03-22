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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ga.najjar.bakingapp.Utils.Utils;

import static android.widget.GridLayout.VERTICAL;

public class RecipeFragment extends Fragment implements RecipeCardAdapter.ItemClickListener{

    OnRecipeClickListener mCallback;
    RecyclerView mRecyclerView;
    @Override
    public void onItemClickListener(int itemId) {
        //
        mCallback.onRecipeCardClick(itemId);
    }

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

        mRecyclerView = root.findViewById(R.id.rv_recipes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // TODO set the adapter to get all recipes
        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(getContext(),this,Utils.Recipes);
        mRecyclerView.setAdapter(recipeCardAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration);


        return root;
    }
}
