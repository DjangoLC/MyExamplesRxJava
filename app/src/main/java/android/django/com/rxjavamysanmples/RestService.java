package android.django.com.rxjavamysanmples;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestService {

    @GET("posts/{id}")
    Observable<Post> getPosts(@Path("id") int id);
}
