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
    
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_text = findViewById(R.id.tv_text);
        RetrofitAdapter retrofitAdapter = new RetrofitAdapter();

        Observable.fromIterable(populateUrls())
                .flatMap((Function<Integer, Observable<Post>>) s -> {
                    Log.e(TAG, "apply: id? "+s );
                    return retrofitAdapter.RetrofitAdapter().create(RestService.class).getPosts(s);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(post->{
                            tv_text.append(post.getTitle()+"\n");
                            tv_text.append("------------------------------- \n");
                },error->{
                    Log.e(TAG, "error: "+error.getMessage());
                });


    }

    private List<Integer> populateUrls(){
        List<Integer> mUrls = new ArrayList<>();
        mUrls.add(1);
        mUrls.add(2);
        mUrls.add(3);
        mUrls.add(4);
        mUrls.add(5);
        mUrls.add(6);
        mUrls.add(7);
        return mUrls;
    }

}
