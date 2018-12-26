package android.django.com.rxjavamysanmples;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitAdapter {

    private Retrofit retrofit;
    private static final String BASE_URL  = "https://jsonplaceholder.typicode.com/";

    public Retrofit RetrofitAdapter() {
        if (retrofit==null){
            retrofit = new  Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();

        }
        return retrofit;
    }
}
