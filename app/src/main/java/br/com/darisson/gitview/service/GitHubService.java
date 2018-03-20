package br.com.darisson.gitview.service;

import org.androidannotations.annotations.EBean;
import org.greenrobot.eventbus.EventBus;
import java.util.List;
import br.com.darisson.gitview.R;
import br.com.darisson.gitview.event.RequestFailedEvent;
import br.com.darisson.gitview.event.RequestOwnerSuccessfulEvent;
import br.com.darisson.gitview.event.RequestRepositorySuccessfulEvent;
import br.com.darisson.gitview.model.Owner;
import br.com.darisson.gitview.model.Repository;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EBean
public class GitHubService extends BaseGitHubService {

    public void requestUser(String username){

        getGitHubAPI().getUser(username).enqueue(new Callback<Owner>() {
            @Override
            public void onResponse(Call<Owner> call, Response<Owner> response) {

                if (response.isSuccessful()){
                    deleteRepository();
                    salvaOwner(response.body());
                    EventBus.getDefault().post(new RequestOwnerSuccessfulEvent(response.body().getId()));
                }else{
                    EventBus.getDefault().post(new RequestFailedEvent(response.message(),false,R.string.err_message));
                }
            }
            @Override
            public void onFailure(Call<Owner> call, Throwable t) {
                EventBus.getDefault().post(new RequestFailedEvent("",true,R.string.err_message));
            }
        });
    }

    public void requestProfile(String username){

        getGitHubAPI().getRepository(username).enqueue(new Callback<List<Repository>>(){
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                if (response.isSuccessful()){
                    salvaRepository(response.body());
                    EventBus.getDefault().post(new RequestRepositorySuccessfulEvent());
                }else{
                    EventBus.getDefault().post(new RequestFailedEvent(response.message(),false, R.string.err_message));
                }
            }
            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                EventBus.getDefault().post(new RequestFailedEvent("",true, R.string.err_message));

            }
        });
    }

    public void requestRepositoryContributors(String username, final String repository){
        getGitHubAPI().getUserRepositoriesContributors(username,repository).enqueue(new Callback<List<Owner>>() {
            @Override
            public void onResponse(Call<List<Owner>> call, Response<List<Owner>> response) {
                if (response.isSuccessful()){
                    EventBus.getDefault().post(response.body());
                }else{
                    EventBus.getDefault().post(new RequestFailedEvent(response.message(), false, R.string.err_message));
                }
            }
            @Override
            public void onFailure(Call<List<Owner>> call, Throwable t) {
                EventBus.getDefault().post(new RequestFailedEvent("", true, R.string.err_message));

            }
        });

    }

    private void salvaRepository(List<Repository> repositories) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(repositories);
        realm.commitTransaction();
    }

    public Repository getRepository(Integer id) {
        Realm realm = Realm.getDefaultInstance();

        Repository repository = realm
                .where(Repository.class)
                .equalTo("id", id)
                .findFirst();

        if (repository != null) {
            return realm.copyFromRealm(repository);
        } else {
            return null;
        }
    }

    private void salvaOwner(Owner owner) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(owner);
        realm.commitTransaction();
    }

    public Owner getOwner(Integer id) {
        Realm realm = Realm.getDefaultInstance();

        Owner owner = realm
                .where(Owner.class)
                .equalTo("id", id)
                .findFirst();

        if (owner != null) {
            return realm.copyFromRealm(owner);
        } else {
            return null;
        }
    }

    public List<Repository> getListRepository(){
        Realm realm = Realm.getDefaultInstance();

        List<Repository> repositories = realm
                .where(Repository.class)
                .findAll();
        if (repositories != null) {
            return realm.copyFromRealm(repositories);
        } else {
            return null;
        }
    }

    public void deleteRepository(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }


}




