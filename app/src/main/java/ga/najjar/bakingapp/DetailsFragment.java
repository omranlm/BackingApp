package ga.najjar.bakingapp;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import ga.najjar.bakingapp.Utils.Ingredient;
import ga.najjar.bakingapp.Utils.Recipe;
import ga.najjar.bakingapp.Utils.Step;
import ga.najjar.bakingapp.Utils.Utils;

public class DetailsFragment extends Fragment implements View.OnClickListener {


    private SimpleExoPlayer mExoPlayer;
    private int stepInt;
    private Recipe selectedRecipe;
    private View root;
    private ImageButton mImageNext;
    private TextView mTextNext;
    private ImageButton mImagePrevious;
    private TextView mTextPrevious;
    private TextView mTextNoMedia;
    public DetailsFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // This is to avoid re creating the fragment when rotating from the main activity on a landscape over 600 screens
        if (savedInstanceState !=null) return null;

        selectedRecipe = null;
        Bundle bundle = this.getArguments();

        int recipeId = bundle.getInt("recipeId");//TODO remove the static text

        for (Recipe res : Utils.Recipes) {
            if (res.getId() == recipeId)
                selectedRecipe = res;

        }

        root = inflater.inflate(R.layout.fragment_details, container, false);
        // Code from https://developer.android.com/guide/topics/ui/layout/recyclerview


        // TODO get steps from Room
        TextView recipeName = root.findViewById(R.id.tv_recipe_name);
        recipeName.setText(selectedRecipe.getName());

        TextView recipeIngredients = root.findViewById(R.id.tv_ingredients);
        recipeIngredients.setText(formulateIngredients(selectedRecipe.getIngredients()));

        TextView recipeServings = root.findViewById(R.id.tv_serving);
        recipeServings.setText(String.format("Servings %s people", selectedRecipe.getServings()));
        // TODO move static strings

        mImageNext = (ImageButton)root.findViewById(R.id.btn_next_step);
        mTextNext = root.findViewById(R.id.tv_next);

        mImagePrevious = root.findViewById(R.id.btn_previous_step);
        mTextPrevious = root.findViewById(R.id.tv_previous);

        mTextNoMedia = root.findViewById(R.id.tv_no_media);

        stepInt = 0;
        mImageNext.setVisibility(View.VISIBLE);
        mTextNext.setVisibility(View.VISIBLE);

        mImagePrevious.setVisibility(View.INVISIBLE);
        mTextPrevious.setVisibility(View.INVISIBLE);


        mImageNext.setOnClickListener(this);
        mImagePrevious.setOnClickListener(this);

        setupStep(root);

        return root;
    }

    private void setupStep(View root) {


        Step currentStep = selectedRecipe.getSteps()[stepInt];
        TextView stepDescription = (TextView) root.findViewById(R.id.tv_step_description);
        ImageView stepImage =  root.findViewById(R.id.iv_step_image);

        stepDescription.setText(currentStep.getDescription());

        //set the video
        SimpleExoPlayerView mExoViewer = (SimpleExoPlayerView) root.findViewById(R.id.exo_player);
        String uri = currentStep.getVideoURL();
        if (uri != null && !uri.equals("")) {
            initializePlayer(Uri.parse(currentStep.getVideoURL()), mExoViewer);
            mExoViewer.setVisibility(View.VISIBLE);
            mTextNoMedia.setVisibility(View.GONE);

            stepImage.setVisibility(View.GONE);

        }
        else if (currentStep.getThumbnailURL() != null && !currentStep.getThumbnailURL().equals(""))
        {
            stepImage.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(currentStep.getThumbnailURL()).into(stepImage);
            mExoViewer.setVisibility(View.GONE);
            mTextNoMedia.setVisibility(View.GONE);
        }
        else {
            mExoViewer.setVisibility(View.GONE);
            // hide image
            stepImage.setVisibility(View.GONE);
            mTextNoMedia.setVisibility(View.VISIBLE);
        }

    }

    private void initializePlayer(Uri mediaUri, SimpleExoPlayerView mExoViewer) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mExoViewer.setPlayer(mExoPlayer);


            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
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


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {



        destroyPlayer();
        Object ob = v.getTag();
        if (ob == null) return;

        if (ob.toString() == getResources().getString(R.string.previous_image_button))
        {
            // Previous
            stepInt --;
            if (stepInt == 0)
            {
                mImageNext.setVisibility(View.VISIBLE);
                mTextNext.setVisibility(View.VISIBLE);

                mImagePrevious.setVisibility(View.INVISIBLE);
                mTextPrevious.setVisibility(View.INVISIBLE);
            }
            else
            {
                mImageNext.setVisibility(View.VISIBLE);
                mTextNext.setVisibility(View.VISIBLE);
                mImagePrevious.setVisibility(View.VISIBLE);
                mTextPrevious.setVisibility(View.VISIBLE);
            }
        }
        else if ((ob.toString() == getResources().getString(R.string.next_image_button)))
        {
            // Next
            stepInt ++;
            if (stepInt == selectedRecipe.getSteps().length - 1)
            {
                mImageNext.setVisibility(View.INVISIBLE);
                mTextNext.setVisibility(View.INVISIBLE);

                mImagePrevious.setVisibility(View.VISIBLE);
                mTextPrevious.setVisibility(View.VISIBLE);

            }
            else
            {
                mImageNext.setVisibility(View.VISIBLE);
                mTextNext.setVisibility(View.VISIBLE);
                mImagePrevious.setVisibility(View.VISIBLE);
                mTextPrevious.setVisibility(View.VISIBLE);
            }

        }
        setupStep(root);

    }

    public void destroyPlayer() {
        if (mExoPlayer == null) return;
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyPlayer();
    }

    public void pausePlayer() {
        if (mExoPlayer == null) return;

        mExoPlayer.setPlayWhenReady(false);
        mExoPlayer.getPlaybackState();
    }
    public void resumPlayer()
    {
        if (mExoPlayer == null) return;

        mExoPlayer.setPlayWhenReady(true);
        mExoPlayer.getPlaybackState();
    }
}
