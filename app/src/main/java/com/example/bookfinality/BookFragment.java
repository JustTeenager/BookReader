package com.example.bookfinality;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;
import com.example.bookfinality.database.DataBase;
import com.example.bookfinality.database.MyDataBase;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class BookFragment extends Fragment {

    public static final int REGISTER_CODE =1;
    private static final int TIMER=10000;
    private DataBase dataBase;
    private MyDataBase myDataBase;
    private int page;
    private PDFView bookReader;
    private Timer timer;
    private TimerTask timerTask;
    private boolean isDialog=false;

    private static final String KEY_TO_PAGE="kp";

    public static Fragment newFragment(int page){
        BookFragment fragment=new BookFragment();
        Bundle args=new Bundle();
        args.putInt(KEY_TO_PAGE,page);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.book_fragment,container,false);
        bookReader=v.findViewById(R.id.book_reader);
        dataBase = Room.databaseBuilder(getActivity(),DataBase.class,"Sample.bd").allowMainThreadQueries().build();
        if (dataBase.dao().getMyDataBase()==null){
            myDataBase = new MyDataBase(0,false);
            dataBase.dao().insertDataBase(myDataBase);
        }
        else {
            myDataBase = dataBase.dao().getMyDataBase();
        }
        if (getArguments()!=null &&getArguments().getInt(KEY_TO_PAGE)>0) page=getArguments().getInt(KEY_TO_PAGE);
        else page=myDataBase.getNumber_page();
        timer = new Timer();
        timerTask = new MyTimerTask();
        if (!myDataBase.isRegister())
            timer.schedule(timerTask,TIMER,TIMER);
        else {
            timer.cancel();
            timer = null;
        }

        setUpBookReader(bookReader,page);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setUpBookReader(PDFView pdfView, int page){
        pdfView.fromAsset("book.pdf")
                // allows to block changing pages using swipe
                //.enableDoubletap(true)
                .defaultPage(page)
                .swipeHorizontal(true)
                .pageSnap(true)
                .autoSpacing(true)
                .pageFling(true)
                .spacing(2)
                .enableAntialiasing(true)
                .fitEachPage(true)
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        myDataBase.setNumber_page(page);
                        dataBase.dao().updateDataBase(myDataBase);
                    }
                })
                //.onLoad(onLoadCompleteListener) // called after document is loaded and starts to be rendered
                //.onRender(onRenderListener) // called after document is rendered for the first time
                .enableAnnotationRendering(true) // render annotations (such as comments, colors or forms)
                //.pageFitPolicy(FitPolicy.HEIGHT) // mode to fit pages in the view
                // toggle night mode
                .load();
    }

    private void setDialog(){
        if (!isDialog) {
            RegisterDialog dialog = new RegisterDialog();
            dialog.setTargetFragment(this, REGISTER_CODE);
            dialog.setCancelable(false);
            try {
                dialog.show(getFragmentManager(), null);
            } catch (Exception e){}
            isDialog=true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REGISTER_CODE && resultCode==Activity.RESULT_OK){
            myDataBase.setRegister(true);
            dataBase.dao().updateDataBase(myDataBase);
            timer.cancel();
            timer=null;
        }
        if (requestCode==REGISTER_CODE && resultCode == Activity.RESULT_CANCELED){
            isDialog=false;
        }
    }

    class MyTimerTask extends TimerTask{
        @Override
        public void run() {
            setDialog();
        }
    }

}
