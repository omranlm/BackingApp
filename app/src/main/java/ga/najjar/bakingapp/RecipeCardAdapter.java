package ga.najjar.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import ga.najjar.bakingapp.Utils.Recipe;


public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.RecipeViewHolder> {

    private Context mContext;
    private List<Recipe> recipes;
    private ItemClickListener mItemClickListener;

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recipe_card_item, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        Recipe recipe = recipes.get(position);
        holder.recipeName.setText(recipe.getName());

        if (recipe.getImage() != null)
            Picasso.with(mContext).load(recipe.getImage()).into(holder.recipeImage);

    }

    @Override
    public int getItemCount() {
        if (recipes == null) return 0;
        else return recipes.size();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }


    public RecipeCardAdapter(@NonNull Context context, int resource) {

        mContext = context;

    }

    public RecipeCardAdapter(Context context, ItemClickListener mItemClickListener, List<Recipe> recipes) {


        this.recipes = recipes;
        this.mContext = context;
        this.mItemClickListener = mItemClickListener;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView recipeName;
        private ImageView recipeImage;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = (TextView) itemView.findViewById(R.id.recipe_name);
            recipeImage = (ImageView) itemView.findViewById(R.id.im_recipe);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // TODO item clicked
            int movieId = recipes.get(getAdapterPosition()).getId();

            //
            mItemClickListener.onItemClickListener(getAdapterPosition());

        }
    }
}
