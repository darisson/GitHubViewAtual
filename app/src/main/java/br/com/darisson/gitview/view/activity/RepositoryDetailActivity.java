package br.com.darisson.gitview.view.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import br.com.darisson.gitview.R;
import br.com.darisson.gitview.adapter.UserAdapter;
import br.com.darisson.gitview.event.ContribuidorClickEvent;
import br.com.darisson.gitview.event.RepositoryClickEvent;
import br.com.darisson.gitview.event.RequestOwnerSuccessfulEvent;
import br.com.darisson.gitview.model.Owner;
import br.com.darisson.gitview.model.Repository;
import br.com.darisson.gitview.service.GitHubService;

@EActivity(R.layout.activity_repository_detail)
public class RepositoryDetailActivity extends BaseActivity {

    @ViewById(R.id.recycler_view)
    protected RecyclerView recyclerView;

    @ViewById(R.id.text_descricao)
    protected TextView descricao;

    @ViewById(R.id.text_repository)
    protected TextView tvRepository;

    @ViewById(R.id.text_language)
    protected TextView language;

    @ViewById(R.id.txt_empty_contributors)
    protected TextView noContributors;

    @Extra
    protected Integer idRepository;

    @Bean
    protected UserAdapter userAdapter;

    @Bean
    protected GitHubService gitHubService;

    private Repository repository;

    @AfterViews
    public void afterViews(){

        repository = gitHubService.getRepository(idRepository);
        gitHubService.requestRepositoryContributors(repository.getOwner().getLogin(), repository.getName());

        tvRepository.setText(repository.getName());
        descricao.setText(repository.getDescription());
        language.setText(repository.getLanguage());
        showProgressDialog(this);

    }

    @Subscribe
    public void onEvent(ContribuidorClickEvent event){
        if (event.getLogin().equals(repository.getOwner().getLogin())) {
            finish();
        }else {
            showProgressDialog(this);
            gitHubService.requestUser(event.getLogin());

        }

    }

    @Subscribe
    public void onEvent(RequestOwnerSuccessfulEvent event){
        RepositoryActivity_.intent(this).idOwner(event.getId()).start();
        dismissProgressDialog();
    }


    @Subscribe
    public void onEvent(List<Owner> owner){
        dismissProgressDialog();
        if (owner.isEmpty()){
            noContributors.setVisibility(View.VISIBLE);
        }else{
            userAdapter.setItems(owner);
            recyclerView.setAdapter(userAdapter);
        }
    }

    @Click(R.id.botao_voltar)
    public void Voltar(){
        onBackPressed();
    }
}
