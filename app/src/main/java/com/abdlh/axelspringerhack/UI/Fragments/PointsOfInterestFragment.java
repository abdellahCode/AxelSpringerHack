package com.abdlh.axelspringerhack.UI.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
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
import android.widget.Toast;

import com.abdlh.axelspringerhack.Model.Element;
import com.abdlh.axelspringerhack.Presenters.LociAdapter;
import com.abdlh.axelspringerhack.R;

import java.util.List;

import butterknife.ButterKnife;


public class PointsOfInterestFragment extends Fragment implements PointsOfInterestView, SwipeRefreshLayout.OnRefreshListener {
    ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static String TAG = "PointsOfInterestFragment";
    private LociAdapter lociAdapter;
    private List<Element<?>> mPoiList;

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
    private static final String ARG_PARAM2 = "param2";

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
    public static PointsOfInterestFragment newInstance(List<Element<?>> mPoiList, String param2) {
        PointsOfInterestFragment fragment = new PointsOfInterestFragment();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PointsOfInterestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_main, container, false);
//        ButterKnife.inject(this, view);

        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        progressBar = (ProgressBar) view.findViewById(android.R.id.progress);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView1);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.str_layout);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLongClickable(false);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.red, R.color.red, R.color.red, R.color.red);
        mSwipeRefreshLayout.setEnabled(true);

        prepareArticleAdapter(mPoiList);
        return view;
    }

    private void prepareArticleAdapter(List<Element<?>> mPoiList)
    {
        lociAdapter = new LociAdapter(mPoiList, getActivity());
        getRecyclerView().setAdapter(lociAdapter);
/*        articleAdapter.setCallbacks(new ArticleAdapter.Callbacks()
        {
            @Override
            public void onRefreshRequested()
            {
                executeUserRefreshRequest();
            }
        });*/
        getRecyclerView().setVisibility(View.VISIBLE);
        setHasOptionsMenu(true);
    }



    private RecyclerView getRecyclerView()
    {
        return recyclerView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onRefresh()
    {

    }
}
