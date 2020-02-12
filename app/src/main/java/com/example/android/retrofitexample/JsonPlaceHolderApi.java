package com.example.android.retrofitexample;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

//Essa interface é equivalente a @Dao do RoomDatabase, porém para WebService
public interface JsonPlaceHolderApi {

    //"posts representa a informação adicional da Url: https://jsonplaceholder.typicode.com/posts
    //@Query lança uma pesquisa personalizada na Url: https://jsonplaceholder.typicode.com/posts?userId=1
    //Pode-se lançar vários parâmetros de pesquisa como: https://jsonplaceholder.typicode.com/posts?userId=1&_sort=id&_order=desc  que vai depender da documentação da API.
    @GET("posts")
    Call<List<Post>> getPost(
            @Query("userId") Integer[] userId,        //Declarando id como Integer, podemos lidar com valores null. Pode-se também passar vários userId tornando Integer um array, que serão filtrados nos resultados.
            @Query("_sort") String sort,
            @Query("_order") String order);

    //@QueryMap Map< nome do parâmetro ("userId") , valor do parâmetro ("1")>
    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String, String> parametros);

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);            //A anotação Path indica que o método receberá uma variável de entrada, no caso postId, marcada dentro da informação adicional da Url com chaves {var}.


    //@Url recebe toda a string que personalizará a busca, fazendo a função do @GET(String).
    @GET
    Call<List<Comment>> getComments(@Url String url);

    //Comando POST com um objeto.
    @POST("posts")
    Call<Post> createPost(@Body Post post);

    //Comando POST com os itens do objeto.
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@Field("userId") int userId, @Field("title") String title, @Field("body") String text);

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@FieldMap Map<String, String> fields);
}
