package edu.temple.webbrowserapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class BookmarksActivity extends AppCompatActivity {
    private DeletableAdapter adapter;
    private ArrayList<BookmarkList> ListBookmark;
    private ArrayList<String> arrayBookmartTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        ListView list_view = (ListView) findViewById(R.id.list_view);

        getSupportActionBar().setTitle("Bookmark List");
        ListBookmark = LoadBookmark();
        arrayBookmartTitle = new ArrayList<String>();

        for (int i=0;i<ListBookmark.size();i++){
            arrayBookmartTitle.add(ListBookmark.get(i).getTitle());
        }

        adapter = new DeletableAdapter(this, arrayBookmartTitle);

        adapter.setAttentionClickListener(new DeletableAdapter.AttentionClickListener() {
            @Override
            public void DeleteItem(int iID) {
                ListBookmark.remove(iID);
                SaveBookmark();
            }

            @Override
            public void OnBookmartClick(int index){
                BrowserActivity.ToBookmark(index);
                finish();
            }
        });

        list_view.setAdapter(adapter);

        //close form button
        Button btnClose = (Button) findViewById(R.id.ButtonClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //load the bookmark
    private ArrayList<BookmarkList> LoadBookmark(){
        ArrayList<BookmarkList> arrTemp=new ArrayList<>();
        Context context = getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences("MyAppInfo",Context.MODE_PRIVATE);

        int itotalBookmark=pref.getInt("TotalBookmark" , 0);

        for (int i=0; i<itotalBookmark;i++){
            BookmarkList bkTmp=new BookmarkList();
            int iTmpID=pref.getInt("B_ID_"+i,-1);
            String iTmpTitle=pref.getString("B_Title_"+i,"");
            String iTmpURL=pref.getString("B_URL_"+i,"");
            bkTmp.setVal(iTmpID,iTmpTitle,iTmpURL);
            arrTemp.add(bkTmp);
        }
        return arrTemp;
    }

    //save the bookmark
    private int SaveBookmark(){
        Context context = getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences("MyAppInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("TotalBookmark" ,ListBookmark.size());

        for (int i=0; i<ListBookmark.size();i++){
            editor.putInt("B_ID_"+i,ListBookmark.get(i).getID());
            editor.putString("B_Title_"+i,ListBookmark.get(i).getTitle());
            editor.putString("B_URL_"+i,ListBookmark.get(i).getURL());
        }

        editor.apply();
        return 0;
    }

    private ArrayList<String> getBookmarkTitleList(){
        ArrayList<String> arrTitle=new ArrayList<>();
        for (int i=0;i<ListBookmark.size();i++){
            arrTitle.add(ListBookmark.get(i).getTitle());
        }
        return arrTitle;
    }
}