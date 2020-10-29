package com.example.bookfinality;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContentFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private int [] pages;
    private ArrayList<String> pagesContent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.content_fragment,container,false);
        mRecyclerView=v.findViewById(R.id.content_rec_view);
        initContentsAndLabels();
        ContentAdapter adapter=new ContentAdapter(pages,pagesContent);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    private void initContentsAndLabels() {
        pages= new int[]{-1,5, 11, 17, 21,31,38,44,46,52,55,60,65,-1,66,71,78,85,87,93,97,105,112,117,129,130,137};
        pagesContent=new ArrayList<>();

        pagesContent.add("Часть 1");

        pagesContent.add("Начало");
        pagesContent.add("Первые деньги");
        pagesContent.add("Юрист - крупье");
        pagesContent.add("Волгоградский таксист");
        pagesContent.add("Истории на дорогах");
        pagesContent.add("Бизнес с нуля в Астане");
        pagesContent.add("Таинственная девушка \"Тифа\"");
        pagesContent.add("Десять тысяч визиток или неоправданные ожидания");
        pagesContent.add("Юридическое такси");
        pagesContent.add("Новые технологии в старом бизнесе");
        pagesContent.add("Большие партнеры");
        pagesContent.add("Точка сегодня");

        pagesContent.add("Часть 2");

        pagesContent.add("Дьявол кроется в деталях");
        pagesContent.add("Легких денег не бывает");
        pagesContent.add("Мысли как миллионер!");
        pagesContent.add("Самообразование - залог успеха");
        pagesContent.add("Мышление бедного и богатого человека");
        pagesContent.add("Планирование - половина успеха");
        pagesContent.add("Секретные знания про психологию людей");
        pagesContent.add("Правильный HR, или как собрать лидеров");
        pagesContent.add("Картина мира миллионера");
        pagesContent.add("Лучшая страна в мире");
        pagesContent.add("Анализируй рынок");
        pagesContent.add("Как сколотить капитал в миллион долларов");
        pagesContent.add("Как масштабировать бизнес");
    }

    public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder>{

        private int[] contents_num;
        private ArrayList<String> contents;
        public ContentAdapter(int [] contents_num,ArrayList<String> contents){
            this.contents_num=contents_num;
            this.contents=contents;
        }

        public int getItemViewType(int position)
        {
            if (position == 0 || position==13)
            {
                return 0;
            }
            else
            {
                return 1;
            }
        }

        @NonNull
        @Override
        public ContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ContentHolder(LayoutInflater.from(getActivity()).inflate(R.layout.content_fragment_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ContentHolder holder, int position) {
            switch (this.getItemViewType(position)){
                case 0: {
                    holder.bind(contents.get(position));
                    break;
                }
                case 1:{
                    holder.bind(contents_num[position],contents.get(position),position);
                    break;
                }
            }
        }

        @Override
        public int getItemCount() {
            return contents_num.length;
        }


        public class ContentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            private TextView label;
            private TextView content;

            private int page;

            public ContentHolder(@NonNull View itemView) {
                super(itemView);
                label=itemView.findViewById(R.id.text_label);
                content=itemView.findViewById(R.id.text_content);
                itemView.setOnClickListener(this);
            }

            public void bind(int page,String contents,int num){
                this.page=page;
                {
                    if (num<12) label.setText("Глава "+(num)+":");
                    else label.setText("Глава "+(num-1)+":");
                    content.setText(contents);
                }
            }

            @Override
            public void onClick(View v) {
                Intent intent=BookActivity.newIntent(getActivity(),page);
                startActivity(intent);
                getActivity().finish();
            }

            public void bind(String contents) {
                {
                    label.setTextSize(25);
                    label.setText(contents);
                    content.setVisibility(View.GONE);;
                    itemView.setEnabled(false);
                }
            }
        }
    }

}