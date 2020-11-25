package com.example.lockscreendemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.jaeger.library.StatusBarUtil;

public class LockScreenActivity extends AppCompatActivity {

    private RecyclerView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        StatusBarUtil.setTranslucent(this);
        StatusBarUtil.setDarkMode(this);

        setContentView(R.layout.activity_lock_screen);

        initView();
    }

    private void initView(){
        content = findViewById(R.id.content);
        content.setAdapter(new MyAdapter());
        content.setLayoutManager(new LinearLayoutManager(this));

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(content);

        content.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if(llm.findFirstVisibleItemPosition() == 1){
                        finish();
                    }
                }
            }
        });
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.lock_screen_content_layout, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            if(position == 0){
                holder.itemView.setVisibility(View.VISIBLE);
            }else{
                holder.itemView.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}