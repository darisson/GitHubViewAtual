package br.com.darisson.gitview.view.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import br.com.darisson.gitview.R;
import br.com.darisson.gitview.event.ContribuidorClickEvent;
import br.com.darisson.gitview.event.RepositoryClickEvent;
import br.com.darisson.gitview.model.Owner;

@EViewGroup(R.layout.item_view_repository_detail)
public class UserViewHolder extends RelativeLayout{

    @ViewById(R.id.img_user)
    protected ImageView imgUser;

    @ViewById(R.id.usuario_git)
    protected TextView usuario;

    @ViewById(R.id.item_contribuidor)
    protected RelativeLayout item;

    public UserViewHolder(Context context){
        super(context);
    }

    public void bind(final Owner owner){
        Picasso.with(getContext()).load(owner.getAvatarUrl()).into(imgUser);
        usuario.setText(owner.getLogin());

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new ContribuidorClickEvent(owner.getLogin()));
            }
        });
    }
}
