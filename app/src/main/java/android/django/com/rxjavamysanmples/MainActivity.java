package android.django.com.rxjavamysanmples;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.MainThread;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class MainActivity extends AppCompatActivity {


    private final String TAG = "MainActivity";
    private AdapterPosts adapterPosts;
    RecyclerView mRecyclerView;
    private Post current;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.mRecyclerView);
        RetrofitAdapter retrofitAdapter = new RetrofitAdapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterPosts = new AdapterPosts(populateUrls());
        mRecyclerView.setAdapter(adapterPosts);

        Observable.fromIterable(populateUrls())
                .flatMap((Function<Post, Observable<Post>>) s -> {
                    current = s;
                    return retrofitAdapter.RetrofitAdapter().create(RestService.class).getPosts(s.getUserId());
                })
                .doOnNext(next->{
                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            current.setStatus(DownloadingStatus.DOWNLOADING);
                            adapterPosts.setPostList(current);
                        }
                    });
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(post->{
                    if (post!=null){
                        current.setStatus(DownloadingStatus.DOWNLOADED);
                        adapterPosts.setPostList(current);
                    }else{
                        current.setStatus(DownloadingStatus.ERROR);
                        adapterPosts.setPostList(current);
                    }
                },error->{
                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, "error : "+error.getMessage() );
                            current.setStatus(DownloadingStatus.ERROR);
                            adapterPosts.setPostList(current);
                        }
                    });
                });


    }

    private List<Post> populateUrls(){
        List<Post> mUrls = new ArrayList<>();
        mUrls.add(new Post(1,"uno","title 1","",DownloadingStatus.NOT_DOWNLOADED));
        mUrls.add(new Post(2,"dos","title 2","",DownloadingStatus.NOT_DOWNLOADED));
        mUrls.add(new Post(3,"tres","title 3","",DownloadingStatus.NOT_DOWNLOADED));
        mUrls.add(new Post(4,"cuatro","title 4","",DownloadingStatus.NOT_DOWNLOADED));
        mUrls.add(new Post(1000000,"over 1000+","title 5","",DownloadingStatus.NOT_DOWNLOADED));
        return mUrls;
    }

}
