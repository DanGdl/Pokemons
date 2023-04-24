package com.mdgd.pokemon.ui.pokemons;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mdgd.mvi.fragments.HostedFragment;
import com.mdgd.pokemon.PokemonsApp;
import com.mdgd.pokemon.R;
import com.mdgd.pokemon.models.filters.FilterData;
import com.mdgd.pokemon.models.repo.dao.schemas.PokemonFullDataSchema;

import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class PokemonsFragment extends HostedFragment<PokemonsContract.View, PokemonsContract.ViewModel, PokemonsContract.Host>
        implements PokemonsContract.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private final CompositeDisposable onDestroyDisposables = new CompositeDisposable();
    private final PokemonsAdapter adapter = new PokemonsAdapter();
    private SwipeRefreshLayout refreshSwipe;
    private final EndlessScrollListener scrollListener = new EndlessScrollListener() {
        @Override
        public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
            if (refreshSwipe != null) {
                refreshSwipe.setRefreshing(true);
            }
            getModel().loadPage(page);
        }
    };
    private ImageButton filterAttack;
    private ImageButton filterDefence;
    private ImageButton filterSpeed;
    private View refresh;

    public static PokemonsFragment newInstance() {
        return new PokemonsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onDestroyDisposables.add(adapter.getOnItemClickSubject().subscribe(pokemon -> getModel().onItemClicked(pokemon)));
    }

    @Override
    protected PokemonsContract.ViewModel createModel() {
        return new ViewModelProvider(this, new PokemonsViewModelFactory(PokemonsApp.getInstance().getAppComponent())).get(PokemonsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pokemons, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView recyclerView = view.findViewById(R.id.pokemons_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnScrollListener(scrollListener);
        recyclerView.setAdapter(adapter);
        refresh = view.findViewById(R.id.pokemons_refresh);
        refreshSwipe = view.findViewById(R.id.pokemons_swipe_refresh);
        filterAttack = view.findViewById(R.id.pokemons_filter_attack);
        filterDefence = view.findViewById(R.id.pokemons_filter_defence);
        filterSpeed = view.findViewById(R.id.pokemons_filter_movement);

        refresh.setOnClickListener(this);
        refreshSwipe.setOnRefreshListener(this);

        filterAttack.setOnClickListener(this);
        filterDefence.setOnClickListener(this);
        filterSpeed.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        refresh = null;
        refreshSwipe = null;
        filterAttack = null;
        filterDefence = null;
        filterSpeed = null;
        super.onDestroyView();
    }

    @Override
    public void proceedToNextScreen(long id) {
        if (hasHost()) {
            getFragmentHost().proceedToPokemonScreen(id);
        }
    }

    @Override
    public void updateFilterButtons(boolean isActive, String filter) {
        final ImageButton view;
        switch (filter) {
            case FilterData.FILTER_ATTACK:
                view = filterAttack;
                break;

            case FilterData.FILTER_DEFENCE:
                view = filterDefence;
                break;

            case FilterData.FILTER_SPEED:
                view = filterSpeed;
                break;
            default:
                view = null;
        }
        if (view == null) {
            return;
        }
        if (isActive) {
            view.setColorFilter(ContextCompat.getColor(requireContext(), R.color.filter_active));
        } else {
            view.setColorFilter(ContextCompat.getColor(requireContext(), R.color.filter_inactive));
        }
    }

    @Override
    public void onClick(View view) {
        if (view == refresh) {
            if (!refreshSwipe.isRefreshing()) {
                refreshSwipe.setRefreshing(true);
            }
        } else {
            if (filterAttack == view) {
                getModel().sort(new FilterData(Arrays.asList(FilterData.FILTER_ATTACK)));
            } else if (filterDefence == view) {
                getModel().sort(new FilterData(Arrays.asList(FilterData.FILTER_DEFENCE)));
            } else if (filterSpeed == view) {
                getModel().sort(new FilterData(Arrays.asList(FilterData.FILTER_SPEED)));
            }
        }
    }

    @Override
    public void onRefresh() {
        scrollListener.resetState();
        getModel().reload();
    }

    @Override
    public void onDestroy() {
        onDestroyDisposables.clear();
        super.onDestroy();
    }

    @Override
    public void setProgressVisibility(boolean isVisible) {
        if (refreshSwipe != null && refreshSwipe.isRefreshing() != isVisible) {
            refreshSwipe.setRefreshing(isVisible);
        }
    }

    @Override
    public void setItems(List<PokemonFullDataSchema> list) {
        adapter.setItems(list);
    }

    @Override
    public void showError(Throwable error) {
        if (hasHost()) {
            getFragmentHost().showError(error);
        }
    }
}
