package t4069.inventory;
 
import java.util.ArrayList;
import java.util.HashMap;

import com.google.android.imageloader.ImageLoader;
 
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class LazyAdapter extends BaseAdapter {
 
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;
 
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader();
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return data.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);
 
        TextView name = (TextView)vi.findViewById(R.id.name); // title
        TextView manufacturer = (TextView)vi.findViewById(R.id.manufacturer); // artist name
        TextView category = (TextView)vi.findViewById(R.id.category); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
 
        HashMap<String, String> part = new HashMap<String, String>();
        part = data.get(position);
 
        // Setting all values in listview
        name.setText(part.get(InventoryActivity.KEY_NAME));
        manufacturer.setText(part.get(InventoryActivity.KEY_MANUFACTURER));
        category.setText(part.get(InventoryActivity.KEY_CATEGORY));
        if (part.get(InventoryActivity.KEY_IMG_URL) != null)imageLoader.bind(thumb_image,part.get(InventoryActivity.KEY_IMG_URL), null);
        return vi;
    }
}