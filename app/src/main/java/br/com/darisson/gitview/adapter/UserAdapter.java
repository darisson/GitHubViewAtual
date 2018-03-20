package br.com.darisson.gitview.adapter;

import android.content.Context;
import android.view.ViewGroup;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import java.util.List;
import br.com.darisson.gitview.model.Owner;
import br.com.darisson.gitview.view.holder.UserViewHolder;
import br.com.darisson.gitview.view.holder.UserViewHolder_;
import br.com.darisson.gitview.view.holder.ViewWrapper;

@EBean
public class UserAdapter extends RecyclerViewAdapterBase<Owner , UserViewHolder> {

    @RootContext
    Context context;

    public void setItems(List<Owner> owners) {
        this.items = owners;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewWrapper<UserViewHolder> holder, int position) {
        UserViewHolder view = holder.getView();
        view.bind(items.get(position));
    }

    @Override
    protected UserViewHolder onCreateItemView(ViewGroup parent, int viewType) {
        return UserViewHolder_.build(context);
    }







}
