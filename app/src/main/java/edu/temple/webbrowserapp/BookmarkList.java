package edu.temple.webbrowserapp;

//Bookmart struct
public class BookmarkList {
    private int bmID;
    private String bmTitle;
    private String bmURL;

    public BookmarkList(){
    }

    public int setVal(int iID,String sTitle, String sURL){
        this.bmID=iID;
        this.bmTitle=sTitle;
        this.bmURL=sURL;
        return 0;
    }

    public BookmarkList getVal(){
        BookmarkList rtn=new BookmarkList();
        rtn.setVal(this.bmID,this.bmTitle,this.bmURL);
        return rtn;
    }

    public int getID(){
        return this.bmID;
    }

    public String getTitle(){
        return  this.bmTitle;
    }

    public String getURL(){
        return this.bmURL;
    }
}
