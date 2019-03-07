package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject sandwichObject = new JSONObject(json);

            JSONObject name = sandwichObject.getJSONObject("name");
            String mainName = name.getString("mainName");

            JSONArray otherName = name.getJSONArray("alsoKnownAs");
            List<String> otherNameList = createListFromJSONarray(otherName);

            String placeOfOrigin = sandwichObject.getString("placeOfOrigin");
            String description = sandwichObject.getString("description");
            String image = sandwichObject.getString("image");

            JSONArray ingredients = sandwichObject.getJSONArray("ingredients");
            List<String> ingresientList = createListFromJSONarray(ingredients);

            Sandwich sandwich = new Sandwich(mainName,
                    otherNameList,
                    placeOfOrigin,
                    description,
                    image,
                    ingresientList);

            return sandwich;
            
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage() );
            return null;
        }
    }

    private static List<String> createListFromJSONarray(JSONArray array) throws
            JSONException {
        List<String> list = new ArrayList<>(array.length());

        for (int i=0; i < array.length(); i++) {
            list.add(array.getString(i));
        }
        return list;
    }
}
