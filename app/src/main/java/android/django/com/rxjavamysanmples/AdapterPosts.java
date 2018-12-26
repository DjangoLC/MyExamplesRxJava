package android.django.com.rxjavamysanmples;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterPosts extends RecyclerView.Adapter<AdapterPosts.MyViewHolder> {

    private List<Post> postList;
    private DownloadingStatus status;
    private final String TAG = "AdapterPosts";

    public AdapterPosts(List<Post> postList) {
        //postList = new ArrayList<>();
        this.postList = postList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_post,viewGroup,false);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Post current = postList.get(i);
        myViewHolder.tv_content_title.setText(current.getTitle());

        myViewHolder.img_error.setVisibility(current.getStatus() == DownloadingStatus.ERROR ? View.VISIBLE : View.GONE);


        if (current.getStatus() == DownloadingStatus.ERROR){
            myViewHolder.img_error.setVisibility(View.VISIBLE);
            myViewHolder.progressBar.setVisibility(View.GONE);
            myViewHolder.tv_content_title.setTextColor(Color.RED);
        }
        if (current.getStatus() == DownloadingStatus.DOWNLOADED){
            myViewHolder.progressBar.setVisibility(View.GONE);
            myViewHolder.img_error.setVisibility(View.GONE);
            myViewHolder.tv_content_title.setTextColor(Color.GREEN);
        }
        if (current.getStatus() == DownloadingStatus.DOWNLOADING){
            myViewHolder.progressBar.setVisibility(View.VISIBLE);
            myViewHolder.img_error.setVisibility(View.GONE);
            myViewHolder.img_waiting.setVisibility(View.GONE);
        }
        if (current.getStatus() == DownloadingStatus.WAITING){
            myViewHolder.img_waiting.setVisibility(View.VISIBLE);
        }

        //myViewHolder.img_waiting.setVisibility();

    }

    public void setPostList(Post post){
        for (Post post1 :  postList){
            if (post1.getUserId() == post.getUserId()){
                post1.setStatus(post.getStatus());
                Log.e(TAG, "index: "+postList.indexOf(post1)+" post ID: "+post.getUserId()+" are equals list ID "+ post1.getUserId()+" " +post1.getStatus().name());
                notifyDataSetChanged();
            }
        }


    }

    @Override
    public int getItemCount() {
        return postList!=null & postList.isEmpty() ? 0 : postList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_content_title;
        ImageView img_error,img_waiting;
        ProgressBar progressBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content_title = itemView.findViewById(R.id.tv_content_title);
            img_error = itemView.findViewById(R.id.img_error);
            img_waiting = itemView.findViewById(R.id.img_waiting);
            progressBar = itemView.findViewById(R.id.progress);
        }
    }





}
