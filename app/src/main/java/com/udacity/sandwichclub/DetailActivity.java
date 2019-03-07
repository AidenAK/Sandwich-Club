package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView sandwichIv;
    private TextView originTv;
    private TextView aliasTv;
    private TextView descriptionTv;
    private TextView ingredientTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        sandwichIv = findViewById(R.id.image_iv);
        originTv = findViewById(R.id.origin_tv);
        aliasTv = findViewById(R.id.also_known_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientTv = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(sandwichIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        originTv.setText(sandwich.getPlaceOfOrigin());
        descriptionTv.setText(sandwich.getDescription());

        //display ingredients
        StringBuilder ingredientBuilder = new StringBuilder();
        for (int i=0; i < sandwich.getIngredients().size(); i++) {
            ingredientBuilder.append("- ");
            ingredientBuilder.append(sandwich.getIngredients().get(i));
            ingredientBuilder.append("\n");
        }
        ingredientTv.setText(ingredientBuilder.toString());

        //display other names
        StringBuilder otherNamesBuilder = new StringBuilder();
        for (int i=0; i < sandwich.getAlsoKnownAs().size(); i++) {
            otherNamesBuilder.append(sandwich.getAlsoKnownAs().get(i));
            otherNamesBuilder.append("\n");
        }
        aliasTv.setText(otherNamesBuilder.toString());
    }
}
