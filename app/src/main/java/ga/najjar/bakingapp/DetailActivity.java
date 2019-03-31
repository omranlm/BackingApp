package ga.najjar.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    private DetailsFragment detailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        detailsFragment = new DetailsFragment();

        int recipeId = -1;
        if (getIntent().hasExtra("recipeId"))
            recipeId = getIntent().getIntExtra("recipeId",-1);

        Bundle bundle = new Bundle();
        bundle.putInt("recipeId",recipeId);
        detailsFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.detail_fragment_layout,detailsFragment)
                .commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
       detailsFragment.pausePlayer();

    }

    @Override
    protected void onResume() {
        super.onResume();
        detailsFragment.resumPlayer();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
