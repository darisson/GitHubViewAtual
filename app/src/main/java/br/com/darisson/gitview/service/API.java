package br.com.darisson.gitview.service;

import java.util.List;
import br.com.darisson.gitview.model.Owner;
import br.com.darisson.gitview.model.Repository;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API{

    /**
     *Call<List<Repository>> getRepository(@Path("username") String username);
     *recebendo uma lista de repositorios do JSON como valor de retorno atraves do call
     */

    @GET("users/{username}")
    Call<Owner> getUser(@Path("username") String username);

    @GET("users/{username}/repos")
    Call<List<Repository>> getRepository(@Path("username") String username);

    @GET("repos/{username}/{repository}/contributors")
    Call<List<Owner>> getUserRepositoriesContributors(@Path("username") String user, @Path("repository") String repository);





}
