package br.com.darisson.gitview.view.activity;


import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;
import java.util.List;
import br.com.darisson.gitview.R;
import br.com.darisson.gitview.adapter.RepositoryAdapter;
import br.com.darisson.gitview.event.RepositoryClickEvent;
import br.com.darisson.gitview.event.RequestRepositorySuccessfulEvent;
import br.com.darisson.gitview.model.Owner;
import br.com.darisson.gitview.model.Repository;
import br.com.darisson.gitview.service.GitHubService;

@EActivity(R.layout.activity_repository)
public class RepositoryActivity extends BaseActivity {

    public static final String OWNER_EXTRA = "owner";

    @ViewById(R.id.lista_recycler)
    protected RecyclerView recyclerView;

    @ViewById(R.id.repository_image)
    protected ImageView fotoUsuario;

    @ViewById(R.id.usuario_git)
    protected TextView usuario;

    @Bean
    protected GitHubService gitHubService;

    @Bean
    protected RepositoryAdapter adapter;

    @Extra
    protected Integer idOwner;

    @AfterViews
    public void afterViews() {

        Owner owner = gitHubService.getOwner(idOwner);

        gitHubService.requestProfile(owner.getLogin());
        showProgressDialog(this);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration
                (recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        Picasso.with(this).load(owner.getAvatarUrl()).into(fotoUsuario);
        usuario.setText(owner.getLogin());

    }

    @Subscribe
    public void onEvent(RepositoryClickEvent event){
        RepositoryDetailActivity_.intent(this).idRepository(event.getId()).start();

    }

    @Subscribe
    public void onEvent(RequestRepositorySuccessfulEvent event){
        adapter.setItems(gitHubService.getListRepository(idOwner));
        recyclerView.setAdapter(adapter);
        dismissProgressDialog();
    }

    @Click(R.id.botao_voltar)
    public void Voltar(){
        onBackPressed();
    }
}

