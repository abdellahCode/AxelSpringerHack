package com.abdlh.axelspringerhack.UI.Fragments;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abdlh.axelspringerhack.MainActivity;
import com.abdlh.axelspringerhack.Model.DetailInteractorImpl;
import com.abdlh.axelspringerhack.Presenters.DetailsPresenter;
import com.abdlh.axelspringerhack.Presenters.DetailsPresenterImpl;
import com.abdlh.axelspringerhack.Model.Element;
import com.abdlh.axelspringerhack.Model.PointOfInterest;
import com.abdlh.axelspringerhack.UI.Adapters.LociAdapter;
import com.abdlh.axelspringerhack.R;
import com.abdlh.axelspringerhack.fragment_click;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.ButterKnife;


public class DetailsFragment extends Fragment implements PointsOfInterestView, SwipeRefreshLayout.OnRefreshListener {

    public static String TAG = "PointsOfInterestFragment";
    ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LociAdapter lociAdapter;
    private List<Element<?>> mPoiList;
    public DetailsPresenter detailPresenter;
    public fragment_click fragment_click;
    ImageView image;
    TextView title, title1, title2, text1, text2, domain1, domain2, soctitle1, soctitle2, soctext1, soctext2;
    LinearLayout news, social;
    @Override
    public void setPointsOfInterest(List<Element<?>> mPoiList) {
        this.mPoiList = mPoiList;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE); //= ProgressDialog.show(getActivity(), "Medics","Loading", true);
    }

    @Override
    public void hideLoading() {
        if(progressBar != null)
            progressBar.setVisibility(View.INVISIBLE);
        else
            Log.e(TAG, "progressDialog shouldn't be null");
    }

    @Override
    public void onErrorLoading() {
        Toast.makeText(getActivity(), "Error loading data..", Toast.LENGTH_SHORT).show();
    }

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "name";

    // TODO: Rename and change types of parameters
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static DetailsFragment newInstance(String name) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, name);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mPoi = getArguments().getParcelable("poi");
            mParam2 = getArguments().getString(ARG_PARAM2);
            String[] keywords = new String[2];
            StringTokenizer st = new StringTokenizer(mParam2, " ");
            for (int j = 0; j < 2; j++) {
                if (st.hasMoreTokens())
                    keywords[j] = st.nextToken();
            }
            mParam2 = keywords[0] += keywords[1] != null ? " " + keywords[1] : "";
        }
        detailPresenter = new DetailsPresenterImpl(this, new DetailInteractorImpl());



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//

        final View view = inflater.inflate(R.layout.detail_layout, container, false);
        ButterKnife.inject(this, view);

        image = (ImageView) view.findViewById(R.id.image);
        title1 = (TextView) view.findViewById(R.id.title1);
        title = (TextView) view.findViewById(R.id.title);
        title2 = (TextView) view.findViewById(R.id.title2);
        text1 = (TextView) view.findViewById(R.id.text1);
        text2 = (TextView) view.findViewById(R.id.text2);

        soctitle1 = (TextView) view.findViewById(R.id.soctitle1);
        soctitle2 = (TextView) view.findViewById(R.id.soctitle2);
        soctext1 = (TextView) view.findViewById(R.id.soctext1);
        soctext2 = (TextView) view.findViewById(R.id.soctext2);


        domain1 = (TextView) view.findViewById(R.id.domain1);
        domain2 = (TextView) view.findViewById(R.id.domain2);

        news = (LinearLayout) view.findViewById(R.id.news);
        social = (LinearLayout) view.findViewById(R.id.social);
        return view;
    }

    private void prepareArticleAdapter()
    {
        lociAdapter = new LociAdapter(mPoiList, getActivity());
        getRecyclerView().setAdapter(lociAdapter);

        getRecyclerView().setVisibility(View.VISIBLE);
        setHasOptionsMenu(true);
    }

    private RecyclerView getRecyclerView()
    {
        return recyclerView;
    }

    @Override
    public void onStart() {
        super.onStart();
        OkHttpClient okHttpClient = new OkHttpClient();
        title.setText(mParam2);
        Request request = new Request.Builder().url("https://api.qwant.com/api/search/images?count=2&locale=de_de&offset=1&q=" + mParam2).build();
        final Handler mainHandler = new Handler(getActivity().getMainLooper());

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                JSONObject jo = null;
                try {
                    jo = new JSONObject(s);
                    final String url = jo.getJSONObject("data").getJSONObject("result").getJSONArray("items").getJSONObject(1).getString("media");
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Picasso.with(getActivity()).load(url).into(image);                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Request request1 = new Request.Builder().url("https://api.qwant.com/api/search/news?count=2&f=source:welt.de&locale=de_de&offset=2&q="+mParam2).build();
        okHttpClient.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                JSONObject jo = null;
                try {
                    jo = new JSONObject(s);
                    final String title = jo.getJSONObject("data").getJSONObject("result").getJSONArray("items").getJSONObject(0).getString("title");
                    final String desc = jo.getJSONObject("data").getJSONObject("result").getJSONArray("items").getJSONObject(0).getString("desc");
                    final String domain = jo.getJSONObject("data").getJSONObject("result").getJSONArray("items").getJSONObject(0).getString("domain");

                    final String _title2 = jo.getJSONObject("data").getJSONObject("result").getJSONArray("items").getJSONObject(1).getString("title");
                    final String _desc2 = jo.getJSONObject("data").getJSONObject("result").getJSONArray("items").getJSONObject(1).getString("desc");
                    final String _domain2 = jo.getJSONObject("data").getJSONObject("result").getJSONArray("items").getJSONObject(1).getString("domain");
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            title1.setText(Html.fromHtml(title));
                            title2.setText(Html.fromHtml(_title2));
                            text1.setText(Html.fromHtml(desc));
                            text2.setText(Html.fromHtml(_desc2));
                            domain1.setText(Html.fromHtml(domain));
                            domain2.setText(Html.fromHtml(_domain2));
                        }
                    });

                } catch (JSONException e) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            news.setVisibility(View.GONE);
                        }
                    });
                    e.printStackTrace();
                }
            }
        });



        Request request2 = new Request.Builder().url("https://api.qwant.com/api/search/social?count=2&f=source%3Atwitter&locale=de_de&offset=10&q="+mParam2).build();
        okHttpClient.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                JSONObject jo = null;
                try {
                    jo = new JSONObject(s);
                    final String title = jo.getJSONObject("data").getJSONObject("result").getJSONArray("items").getJSONObject(0).getString("title");
                    final String desc = jo.getJSONObject("data").getJSONObject("result").getJSONArray("items").getJSONObject(0).getString("desc");

                    final String _title2 = jo.getJSONObject("data").getJSONObject("result").getJSONArray("items").getJSONObject(1).getString("title");
                    final String _desc2 = jo.getJSONObject("data").getJSONObject("result").getJSONArray("items").getJSONObject(1).getString("desc");
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            soctitle1.setText(Html.fromHtml(title));
                            soctitle2.setText(Html.fromHtml(_title2));
                            soctext1.setText(Html.fromHtml(desc));
                            soctext2.setText(Html.fromHtml(_desc2));
                        }
                    });

                } catch (JSONException e) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            social.setVisibility(View.GONE);
                        }
                    });

                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragment_click = (fragment_click) activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onRefresh()
    {

    }

    public void onStop() {
        super.onStop();
    }
}
