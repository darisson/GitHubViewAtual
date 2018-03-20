package br.com.darisson.gitview.view.activity;

import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;
import br.com.darisson.gitview.R;
import br.com.darisson.gitview.event.RequestFailedEvent;
import br.com.darisson.gitview.event.RequestOwnerSuccessfulEvent;
import br.com.darisson.gitview.model.Owner;
import br.com.darisson.gitview.service.GitHubService;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.texto_busca)
    protected TextView editText;

    @ViewById(R.id.botao_busca)
    protected Button botaoBusca;

    @Bean
    protected GitHubService gitHubService;

    @AfterViews
    public void afterViews(){
        gitHubService.deleteRepository();

    }

    @Click(R.id.botao_busca)
    public void buscar(){
        GitHubService gitHubService = new GitHubService();
        String str = editText.getText().toString().trim();
        if(!str.isEmpty()){
            showProgressDialog(this);
            gitHubService.requestUser(str);
            botaoBusca.setEnabled(false);
        }else{
            String userErro = getString(R.string.user_erro);
            showAlertDialog(MainActivity.this, userErro,"erro");
            botaoBusca.setEnabled(true);
        }
    }

    @Subscribe
    public void onEvent(RequestOwnerSuccessfulEvent event){
        RepositoryActivity_.intent(this).idOwner(event.getId()).start();
        botaoBusca.setEnabled(true);
        dismissProgressDialog();
    }

    @Subscribe
    public void onEvent(RequestFailedEvent failedEvent){
        String errorMsg = getString(R.string.err_usuaruio);
        if (failedEvent.isDefaultError()){
            if(failedEvent.getMessage().equals("")){
                errorMsg = getString(failedEvent.getErr_message());
            }else{
                errorMsg = failedEvent.getMessage();
            }
        }
        showAlertDialog(MainActivity.this, errorMsg,"erro");
        botaoBusca.setEnabled(true);
        dismissProgressDialog();
    }
}

