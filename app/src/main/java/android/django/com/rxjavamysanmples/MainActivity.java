package android.django.com.rxjavamysanmples;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    private final String TAG = "MainActivity";

    private int cont = 0;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_text = findViewById(R.id.tv_text);
        List<String> mUrls = new ArrayList<>();


        RetrofitAdapter retrofitAdapter = new RetrofitAdapter();


//        retrofitAdapter.RetrofitAdapter().create(RestService.class).getPosts().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(obj->{
//                    if (obj!=null){
//                        tv_text.setText("");
//                        for (Post post : obj){
//                            Log.e(TAG, "onCreate: "+post.getBody());
//                            tv_text.append(post.getTitle() + "\n");
//                        }
//                    }
//                }, error->{
//                    Log.e(TAG, "onCreate: "+error.getMessage());
//                });


//        retrofitAdapter.RetrofitAdapter().create(RestService.class).getPosts().subscribeOn(Schedulers.io())
//                .obs

//        Observable.fromArray(populateUrls())
//                .flatMap(new Function<List<String>, Observable<List<Post>>>() {
//                    @Override
//                    public Observable<List<Post>> apply(List<String> strings) throws Exception {
//                        cont++;
//                        Log.e(TAG, "apply: "+cont+" time" );
//                        return retrofitAdapter.RetrofitAdapter().create(RestService.class).getPosts();
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response->{
//                    for (Post post : response){
//                        tv_text.append(post.getTitle());
//                    }
//                });


        Observable.fromIterable(populateUrls())
                .flatMap((Function<String, Observable<List<Post>>>) s -> {
                    Log.e(TAG, "apply: "+s );
                    return retrofitAdapter.RetrofitAdapter().create(RestService.class).getPosts();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{

                },error->{

                });


    }

    private List<String> populateUrls(){
        List<String> mUrls = new ArrayList<>();
        mUrls.add("https://jsonplaceholder.typicode.com/posts");
        mUrls.add("https://jsonplaceholder.typicode.com/posts");
        mUrls.add("https://jsonplaceholder.typicode.com/posts");
        return mUrls;
    }

}
