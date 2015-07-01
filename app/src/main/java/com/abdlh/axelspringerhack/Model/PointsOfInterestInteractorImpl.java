package com.abdlh.axelspringerhack.Model;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.abdlh.axelspringerhack.UI.Listners.onLoadingListner;
import com.abdlh.axelspringerhack.gettyConnectSDK.connectsdk.ConnectSdk;
import com.abdlh.axelspringerhack.gettyConnectSDK.connectsdk.SdkException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abdellah on 7/1/15.
 */
public class PointsOfInterestInteractorImpl implements PointsOfInterestInteractor {
    String TAG = "POI Interactor";


    @Override
    public void fetchPointsOfInterests(GoogleApiClient googleApiClient, final onLoadingListner onLoadingListner) {
        final StringBuilder stringBuilder = new StringBuilder();
        PendingResult<PlaceLikelihoodBuffer> mPlaces;
        PlaceFilter placeFilter = new PlaceFilter();
        final List<Element<?>> elementList = new ArrayList<>();
        mPlaces = Places.PlaceDetectionApi.getCurrentPlace(googleApiClient, null);
        if (mPlaces != null) {
            mPlaces.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                @Override
                public void onResult(PlaceLikelihoodBuffer placeLikelihoods) {
                    PointOfInterest pointOfInterest = null;
                    for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                        pointOfInterest = new PointOfInterest(null, null, 0);
                        if (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_ART_GALLERY | Place.TYPE_LIBRARY
                                | Place.TYPE_MUSEUM | Place.TYPE_PAINTER | Place.TYPE_UNIVERSITY)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.CULTURE);
                        } else if (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_AQUARIUM | Place.TYPE_AMUSEMENT_PARK
                                | Place.TYPE_BOWLING_ALLEY | Place.TYPE_BAR | Place.TYPE_CAFE | Place.TYPE_CASINO | Place.TYPE_SPA)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.ACTIVITIES);
                        } else if (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_CHURCH | Place.TYPE_MOSQUE
                                | Place.TYPE_SYNAGOGUE | Place.TYPE_HINDU_TEMPLE | Place.TYPE_PLACE_OF_WORSHIP)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.RELIGION);
                        } else if (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_SHOPPING_MALL | Place.TYPE_SHOE_STORE
                                | Place.TYPE_CONVENIENCE_STORE | Place.TYPE_JEWELRY_STORE)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.SHOPPING);
                        } else if (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_CAFE | Place.TYPE_FOOD
                                | Place.TYPE_RESTAURANT)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.FOOD);
                        } else if (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_STADIUM | Place.TYPE_GYM)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.SPORT);
                        } else if (placeLikelihood.getPlace().getPlaceTypes().contains(Place.TYPE_CAMPGROUND)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.HOTEL);
                        } else {
                            pointOfInterest.setName("NOTHING");
                        }

                        elementList.add(pointOfInterest);
                        //| Place.TYPE_AMUSEMENT_PARK | Place.TYPE_AQUARIUM))
                        Log.d(TAG, String.format("Place '%s' has likelihood: %g",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()));

                        stringBuilder.append(String.format("Place '%s' has likelihood: %g",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()) + "\n");
                    }
                    onLoadingListner.onElementsLoaded(elementList);
                    placeLikelihoods.release();
                }
            });
        } else
            onLoadingListner.onError();
    }
}

 /*   private class SearchTask extends AsyncTask<String, Void, String>
    {
        private Bitmap firstBitmap;

        @Override
        protected String doInBackground(String... searchTerm) {
            String result;
            try {
                ConnectSdk connectSdk = new ConnectSdk(apiKey, apiSecret);
                result = connectSdk.Search().Images().Creative().WithPhrase(searchTerm[0]).WithPage(10).ExecuteAsync();

                try {
                    JSONObject json = (JSONObject) new JSONObject(result);

                    JSONArray images = json.getJSONArray("images");

                    JSONObject image = images.getJSONObject(0);

                    JSONArray displaySizes = image.getJSONArray("display_sizes");

                    JSONObject displaySize = displaySizes.getJSONObject(0);

                    String firstImageUri = displaySize.getString("uri");

                    firstBitmap = getBitmapFromURL(firstImageUri);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //result = connectSdk.Images().WithId("92823652").WithId("92822221").WithId("OneNotFound").WithField("artist").WithField("license_model").ExecuteAsync();
                //result = connectSdk.Download().WithId("92822221").ExecuteAsync();
            } catch (SdkException e) {
                result = e.getMessage();
            }
            catch (Exception e) {
                result = e.toString();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result)
        {
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(result);

            ImageView imageView = (ImageView)findViewById(R.id.imageView);
            imageView.setImageBitmap(firstBitmap);
        }
        public Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

    }*/
