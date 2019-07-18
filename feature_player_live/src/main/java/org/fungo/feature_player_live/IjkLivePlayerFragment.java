package org.fungo.feature_player_live;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fungo.feature_player_live.persenter.IjkLivePlayerPresenter;


/**
 *
 */
public class IjkLivePlayerFragment extends Fragment {


    IjkLivePlayerPresenter ijkLivePlayerPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ijk_live, container, false);


        /**
         * 初始化viewModel
         */
        ijkLivePlayerPresenter = ViewModelProviders.of(getActivity()).get(IjkLivePlayerPresenter.class);
        /**
         * 监听生命周期
         */
        getLifecycle().addObserver(ijkLivePlayerPresenter);


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
