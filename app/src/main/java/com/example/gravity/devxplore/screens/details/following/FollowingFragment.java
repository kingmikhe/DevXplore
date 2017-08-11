package com.example.gravity.devxplore.screens.details.following;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gravity.devxplore.Injection;
import com.example.gravity.devxplore.R;
import com.example.gravity.devxplore.adapters.UsersAdapter;
import com.example.gravity.devxplore.data.model.User;
import com.example.gravity.devxplore.screens.details.DetailsContract;
import com.example.gravity.devxplore.screens.details.DetailsPresenter;
import com.example.gravity.devxplore.utilities.DividerItemDecoration;

import java.util.List;

/**
 * Created by gravity on 7/15/17.
 */

@SuppressWarnings("ALL")
public class FollowingFragment extends Fragment implements DetailsContract.DetailView4, UsersAdapter.UserAdapterListener {

    public final static String USERNAME = "";

    private RecyclerView mRecyclerView;
    private DetailsContract.Presenter mPresenter;
    @Nullable
    private String username;
    private List<User> mFollowing;

    @NonNull
    public static FollowingFragment newInstance(String username) {
        FollowingFragment followingFragment = new FollowingFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, username);
        followingFragment.setArguments(args);
        return followingFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadFollowing(username);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.following_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        DetailsPresenter mUserDetailsPresenter = new DetailsPresenter(Injection.provideDataManager(getActivity().getApplicationContext()), this);

        username = getArguments().getString(USERNAME);

        return view;
    }

    @Override
    public void setPresenter(DetailsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showFollowing(List<User> following) {
        this.mFollowing = following;
        mRecyclerView.setAdapter(new UsersAdapter(getActivity(), following, R.layout.list_item_user_linear, this));
    }

    @Override
    public void onCardClicked(int position) {
        User user = mFollowing.get(position);
        String username = user.getLogin();
        mPresenter.openUserDetails(getActivity(), username);
    }

    @Override
    public void onCardLongClicked(int position) {

    }

    @Override
    public void onCardFavouriteClicked(int position) {
        User following = mFollowing.get(position);
        boolean favourite = following.isFavourite();
        favourite = !favourite;
        mPresenter.updateFavourite(following, favourite);
    }
}
