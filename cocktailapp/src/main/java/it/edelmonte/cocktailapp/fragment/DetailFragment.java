package it.edelmonte.cocktailapp.fragment;

import static org.koin.java.KoinJavaComponent.inject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;

import it.edelmonte.cocktailapp.R;
import it.edelmonte.cocktailapp.model.Cocktail;
import it.edelmonte.cocktailapp.util.CloudManager;
import it.edelmonte.cocktailapp.util.Constants;
import it.edelmonte.cocktailapp.util.ExpandableHeightGridView;
import it.edelmonte.cocktailapp.util.Utility;
import kotlin.Lazy;


public class DetailFragment extends DialogFragment {

    private ImageView cocktailImage;
    private TextView name;
    private TextView recipe;
    private TextView video;
    private Cocktail cocktail;
    private ExpandableHeightGridView ingredients;
    private LinearLayout videoLayout;
    private Spinner langSpinner;
    private final Lazy<CloudManager> cloudManager = inject(CloudManager.class);

    public DetailFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_detail, null);
        init(view);
        builder.setView(view).setPositiveButton(R.string.close, (dialog, which) -> dialog.dismiss());
        return builder.create();
    }

    private void init(View view) {
        cocktailImage = view.findViewById(R.id.cocktail_image);
        name = view.findViewById(R.id.cocktail_name);
        recipe = view.findViewById(R.id.recipe);
        ingredients = view.findViewById(R.id.ingredients);
        langSpinner = view.findViewById(R.id.lang_spinner);
        video = view.findViewById(R.id.video);
        videoLayout = view.findViewById(R.id.video_layout);
        cocktail = cloudManager.getValue().getCocktail();
        bindData();
    }

    private void bindData() {
        Glide.with(getActivity()).load(cocktail.image).into(cocktailImage);
        name.setText(cocktail.name);
        recipe.setText(cocktail.instructionEn);
        ingredients.setNumColumns(2);
        ingredients.setExpanded(true);
        ingredients.setAdapter(Utility.createIngredientList(cocktail, getActivity()));
        if (cocktail.video != null) {
            videoLayout.setVisibility(View.VISIBLE);
            video.setMovementMethod(LinkMovementMethod.getInstance());
            video.setText(cocktail.video);
            video.setOnClickListener(view -> {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vdn.youtube:" + Utility.getVideoId(cocktail.video)));
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(cocktail.video));
                try {
                    startActivity(appIntent);
                } catch (ActivityNotFoundException ex){
                    startActivity(webIntent);
                }
            });
        }
        langSpinner.setAdapter(Utility.createLanguagesAdapter(getActivity(), cocktail));
        langSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String filter = (String) adapterView.getSelectedItem();
                switch (filter) {
                    case Constants.EN:
                        recipe.setText(cocktail.instructionEn);
                        break;
                    case Constants.DE:
                        recipe.setText(cocktail.instructionDe);
                        break;
                    case Constants.ES:
                        recipe.setText(cocktail.instructionEs);
                        break;
                    case Constants.FR:
                        recipe.setText(cocktail.instructionFr);
                        break;
                    case Constants.IT:
                        recipe.setText(cocktail.instructionIt);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}