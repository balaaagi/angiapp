package me.balaaagi.angi.network;

import me.balaaagi.angi.utils.AngiUtils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BALAJI SRINIVASAN on 9/17/2017.
 */

public class APIClient {
    private static Retrofit retrofitClient=null;

    public static Retrofit getRetrofitClient(){
//        Uri.Builder movieDBAPIUrl=Uri.parse(MOVIE_DB_API_BASE_URL).buildUpon()
//                .appendQueryParameter(MDB_API_KEY_PARAM, BuildConfig.MY_MOVIE_API_KEY);
//        System.out.println(movieDBAPIUrl);
        if(retrofitClient==null){
            retrofitClient = new Retrofit.Builder()
                    .baseUrl(String.valueOf(AngiUtils.KAIROS_BASE_URL))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofitClient;
    }
}
