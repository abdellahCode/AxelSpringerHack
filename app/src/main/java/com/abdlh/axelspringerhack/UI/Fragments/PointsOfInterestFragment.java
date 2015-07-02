package com.abdlh.axelspringerhack.UI.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abdlh.axelspringerhack.Model.Element;
import com.abdlh.axelspringerhack.Model.PointOfInterest;
import com.abdlh.axelspringerhack.Model.PointsOfInterestInteractorImpl;
import com.abdlh.axelspringerhack.Presenters.PointsOfInterestPresenter;
import com.abdlh.axelspringerhack.Presenters.PointsOfInterestPresenterImpl;
import com.abdlh.axelspringerhack.UI.Adapters.LociAdapter;
import com.abdlh.axelspringerhack.R;
import com.abdlh.axelspringerhack.UI.Listners.onLoadingListner;
import com.abdlh.axelspringerhack.fragment_click;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PointsOfInterestFragment extends Fragment implements PointsOfInterestView, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, SwipeRefreshLayout.OnRefreshListener {

    public static String TAG = "PointsOfInterestFragment";
    protected GoogleApiClient mGoogleApiClient;
    public Location mLastLocation;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LociAdapter lociAdapter;
    private List<Element<?>> mPoiList;
    public PointsOfInterestPresenter pointsOfInterestPresenter;
    public fragment_click fragment_click;
    ProgressDialog progressDialog;

    @Override
    public void setPointsOfInterest(List<Element<?>> mPoiList) {
        this.mPoiList = mPoiList;
        prepareArticleAdapter();
    }

    @Override
    public void showLoading() {
        progressDialog = ProgressDialog.show(getActivity(), "Locations", "Discovering your surrounding..", true);
    }

    @Override
    public void hideLoading() {
        if(progressDialog != null)
            progressDialog.dismiss();
        else
            Log.e(TAG, "progressDialog shouldn't be null");
    }

    @Override
    public void onErrorLoading() {
        Toast.makeText(getActivity(), "Error loading data..", Toast.LENGTH_SHORT).show();
    }
    public static PointsOfInterestFragment newInstance(String string, String param2) {
        PointsOfInterestFragment fragment = new PointsOfInterestFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public PointsOfInterestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mGoogleApiClient == null || !mGoogleApiClient.isConnected())
            buildGoogleApiClient();
        pointsOfInterestPresenter = new PointsOfInterestPresenterImpl(this, new PointsOfInterestInteractorImpl());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView1);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.str_layout);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLongClickable(false);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.red, R.color.red, R.color.red, R.color.red);
        mSwipeRefreshLayout.setEnabled(true);
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
        if(!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();
        else
            setPointsOfInterest(mPoiList);
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
        {}

    public void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            //Toast.makeText(getActivity(), "Got the location --> Lat: " + mLastLocation.getLatitude() + " and Lng: " + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
            pointsOfInterestPresenter.getElements(mGoogleApiClient, getActivity());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {


    }
}
