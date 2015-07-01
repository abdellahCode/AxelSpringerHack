package com.abdlh.axelspringerhack.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

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
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Abdellah on 7/1/15.
 */
public class PointsOfInterestInteractorImpl implements PointsOfInterestInteractor {
    String TAG = "POI Interactor";
    final List<Element<?>> elementList = new ArrayList<>();
    onLoadingListner onLoadingListner;
    final OkHttpClient okHttpClient = new OkHttpClient();
    int size = 0;
    @Override
    public void fetchPointsOfInterests(GoogleApiClient googleApiClient, final onLoadingListner onLoadingListner, final Context context) {
        final StringBuilder stringBuilder = new StringBuilder();
        PendingResult<PlaceLikelihoodBuffer> mPlaces;
        PlaceFilter placeFilter = new PlaceFilter();
        this.onLoadingListner = onLoadingListner;
        mPlaces = Places.PlaceDetectionApi.getCurrentPlace(googleApiClient, null);
        if (mPlaces != null) {
            mPlaces.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                @Override
                public void onResult(PlaceLikelihoodBuffer placeLikelihoods) {
                    PointOfInterest pointOfInterest = null;
                    for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                        pointOfInterest = new PointOfInterest(null, null, 0);
                        List<Integer> list = new ArrayList<>();
                        list = placeLikelihood.getPlace().getPlaceTypes();
                        if (list.contains(Place.TYPE_ART_GALLERY) || list.contains(Place.TYPE_LIBRARY)
                                || list.contains(Place.TYPE_MUSEUM) || list.contains(Place.TYPE_PAINTER) || list.contains(Place.TYPE_UNIVERSITY)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.CULTURE);
                        } else if (list.contains(Place.TYPE_AQUARIUM) || list.contains(Place.TYPE_AMUSEMENT_PARK)
                                || list.contains(Place.TYPE_BOWLING_ALLEY) || list.contains(Place.TYPE_BAR) || list.contains(Place.TYPE_CAFE)
                                || list.contains(Place.TYPE_CASINO) || list.contains(Place.TYPE_SPA)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.ACTIVITIES);
                        } else if (list.contains(Place.TYPE_CHURCH) || list.contains(Place.TYPE_MOSQUE)
                                || list.contains(Place.TYPE_SYNAGOGUE) || list.contains(Place.TYPE_HINDU_TEMPLE)
                                || list.contains(Place.TYPE_PLACE_OF_WORSHIP)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.RELIGION);
                        } else if (list.contains(Place.TYPE_SHOPPING_MALL) || list.contains(Place.TYPE_SHOE_STORE)
                                || list.contains(Place.TYPE_CONVENIENCE_STORE) || list.contains(Place.TYPE_JEWELRY_STORE)
                                || list.contains(Place.TYPE_GROCERY_OR_SUPERMARKET) || list.contains(Place.TYPE_BOOK_STORE)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.SHOPPING);
                        } else if (list.contains(Place.TYPE_CAFE) || list.contains(Place.TYPE_FOOD)
                                || list.contains(Place.TYPE_RESTAURANT)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.FOOD);
                        } else if (list.contains(Place.TYPE_STADIUM) || list.contains( Place.TYPE_GYM)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.SPORT);
                        } else if (list.contains(Place.TYPE_CAMPGROUND) || list.contains(Place.TYPE_LODGING)) {

                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
                            pointOfInterest.setType(Element.type.HOTEL);
                        } else {
                            pointOfInterest.setName(placeLikelihood.getPlace().getName().toString());
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
                    String[] keywords = new String[2];
                    for (int i = 0; i < elementList.size(); i++) {
                        StringTokenizer st = new StringTokenizer(elementList.get(i).getName(), " ");
                        for (int j = 0; j < 2; j++) {
                            if (st.hasMoreTokens())
                                keywords[j] = st.nextToken();
                        }
                        Request request = new Request.Builder().url("https://api.qwant.com/api/search/images?count=1&locale=de_de&offset=1&q=" + keywords[0] + "%20" + keywords[1]).build();
                        final Handler mainHandler = new Handler(context.getMainLooper());
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                Log.d("OKHTTP", "OKHTTP ERROR: " + e.getMessage());
                                size++;


                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
                                String s = response.body().string();
                                try {
                                    JSONObject jo = new JSONObject(s);
                                    String url = jo.getJSONObject("data").getJSONObject("result").getJSONArray("items").getJSONObject(0).getString("media");
                                    Log.d("OKHTTP", "OKHTTP WORKING URL: " + url);
                                    elementList.get(size).setImageUrl(url);
                                    size++;
                                    if (size == elementList.size()) {
                                        mainHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                onLoadingListner.onElementsLoaded(elementList);
                                            }
                                        });
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                    }

                    //new SearchTask().execute();
                    placeLikelihoods.release();
                }
            });
        } else
            onLoadingListner.onError();


    }


}