package android.django.com.rxjavamysanmples;

public class Post {

    private int userId;
    private String id;
    private String title;
    private String body;

    private DownloadingStatus status;


    public Post() {
    }

    public Post(int userId, String id, String title, String body, DownloadingStatus status) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public DownloadingStatus getStatus() {
        return status;
    }

    public void setStatus(DownloadingStatus status) {
        this.status = status;
    }
}
