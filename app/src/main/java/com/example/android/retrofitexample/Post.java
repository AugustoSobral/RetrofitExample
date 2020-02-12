package com.example.android.retrofitexample;

import com.google.gson.annotations.SerializedName;

//Esse objeto corresponde ao objeto Gson, que queremos requisitar.
public class Post {

    private int userId;
    private Integer id;
    private String title;

    @SerializedName("body")      //Essa anotação informa a qual variável no objeto Gson a variável (String text) corresponde. Se as variáveis já tiverem nomes idênticos, a anotação é desnecessária.
    private String text;


    //Para ser usado também como um objeto Java, criamos um constructor
    public Post(int userId, String title, String text) {
        this.userId = userId;
        this.title = title;
        this.text = text;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
