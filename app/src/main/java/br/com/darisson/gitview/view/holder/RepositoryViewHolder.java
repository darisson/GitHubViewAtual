package br.com.darisson.gitview.view.holder;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import br.com.darisson.gitview.R;
import br.com.darisson.gitview.event.RepositoryClickEvent;
import br.com.darisson.gitview.model.Repository;

@EViewGroup(R.layout.item_view_repository)
public class  RepositoryViewHolder extends RelativeLayout {

    @ViewById(R.id.text_repository)
    protected TextView tvRepository;

    @ViewById(R.id.text_language)
    protected TextView language;

    @ViewById(R.id.item_repository)
    protected RelativeLayout item;

    public RepositoryViewHolder(Context context) {
        super(context);
    }

    public void bind(final Repository repository){
        tvRepository.setText(repository.getName());
        language.setText(repository.getLanguage());

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new RepositoryClickEvent(repository.getId()));
            }
        });
    }
}
