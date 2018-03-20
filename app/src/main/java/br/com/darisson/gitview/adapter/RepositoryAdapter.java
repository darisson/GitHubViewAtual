package br.com.darisson.gitview.adapter;

import android.content.Context;
import android.view.ViewGroup;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import java.util.List;
import br.com.darisson.gitview.model.Repository;
import br.com.darisson.gitview.view.holder.RepositoryViewHolder;
import br.com.darisson.gitview.view.holder.RepositoryViewHolder_;
import br.com.darisson.gitview.view.holder.ViewWrapper;

@EBean
public class RepositoryAdapter extends RecyclerViewAdapterBase<Repository, RepositoryViewHolder> {

    @RootContext
    Context context;

    public void setItems(List<Repository> repositories) {
        this.items = repositories;
    }

    @Override
    public void onBindViewHolder(ViewWrapper<RepositoryViewHolder> holder, int position) {
        RepositoryViewHolder view = holder.getView();
        view.bind(items.get(position));
    }

    @Override
    protected RepositoryViewHolder onCreateItemView(ViewGroup parent, int viewType) {
        return RepositoryViewHolder_.build(context);
    }
}
