package android.django.com.rxjavamysanmples;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RestService {

    @GET("posts")
    Observable<List<Post>> getPosts();
}
