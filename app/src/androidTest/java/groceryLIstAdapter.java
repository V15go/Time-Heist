import java.util.ArrayList;

public class groceryLIstAdapter {


    String title;
    ArrayList<String> list;

    public groceryLIstAdapter(){

    }

    public groceryLIstAdapter(String title, ArrayList<String> list){
        this.title = title;
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }
}
