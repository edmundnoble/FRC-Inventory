package t4069.inventory;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	// TODO: USE DIALOGS, ASSHOLE!
	private Button addButton, removeButton, filterButton, resetFilterButton,
			signOutButton, editButton;
	private ListView partView;
	private Dialog filter, add, signOut, edit;
	private static final int ADD = 1, SIGNOUT = 2, EDIT = 3, FILTER = 4;
	private ArrayList<String> categories = new ArrayList<String>();
	private ArrayList<Part> parts = new ArrayList<Part>();
	private ArrayList<String> visibleParts = new ArrayList<String>();
	private OnClickListener buttonListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button1: {
				add();
				break;
			}
			case R.id.button2: {
				edit();
				break;
			}
			case R.id.button3: {
				signOut();
				break;
			}
			case R.id.button4: {
				remove();
				break;
			}
			case R.id.button5: {
				filter();
				break;
			}
			case R.id.button6: {
				resetFilter();
				break;
			}
			}
		}
	};

	DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			if (dialog.equals(add)) {
				AlertDialog addDialog = (AlertDialog) add;

			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		partView = (ListView) findViewById(R.id.listView1);
		addButton = (Button) findViewById(R.id.button1);
		removeButton = (Button) findViewById(R.id.button4);
		editButton = (Button) findViewById(R.id.button2);
		signOutButton = (Button) findViewById(R.id.button3);
		filterButton = (Button) findViewById(R.id.button5);
		resetFilterButton = (Button) findViewById(R.id.button6);
		makeDialogs();
		addButton.setOnClickListener(buttonListener);
		removeButton.setOnClickListener(buttonListener);
		editButton.setOnClickListener(buttonListener);
		signOutButton.setOnClickListener(buttonListener);
		filterButton.setOnClickListener(buttonListener);
		resetFilterButton.setOnClickListener(buttonListener);
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case ADD: return add; 
		case EDIT: return edit; 
		case SIGNOUT: return signOut;
		case FILTER: return filter;
		default: return null;
		}
	}

	private void makeDialogs() {
		LayoutInflater inflater = (LayoutInflater) getApplicationContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.add_dialog, null);
		add = new Dialog(this);
		add.setTitle("Add Part");
		add.setContentView(layout);
		Spinner addCategories = (Spinner) layout.findViewById(R.id.spinner1);
		addCategories.setAdapter((new ArrayAdapter<String>(
				getApplicationContext(),
				android.R.layout.simple_expandable_list_item_1, categories)));
		if (add == null || layout == null) Log.d("FRC INVENTORY", "NULL!");
		

	}

	String name;
	String partNumber;
	String quantity;
	String storedQuantity;
	String category;

	protected void add() {
		add.show();
	}

	protected void edit() {
		edit.show();
	}

	protected void signOut() {
		signOut.show();
	}

	protected void remove() {
		
	}

	protected void resetFilter() {

	}

	protected void filter() {
		filter.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	class Part {
		String name;
		String number;
		String manufacturer;
		String category;

		public Part(String name, String number, String manufacturer,
				String category) {
			this.name = name;
			this.number = number;
			this.manufacturer = manufacturer;
			this.category = category;
		}

		public void setStr(String prop, String val) {
			if (prop.equalsIgnoreCase("name"))
				name = val;
			else if (prop.equalsIgnoreCase("number"))
				number = val;
			else if (prop.equalsIgnoreCase("manufacturer"))
				manufacturer = val;
			else if (prop.equalsIgnoreCase("category")) {
				String category = new String(val);
				for (String cat : categories) {
					if (category.equals(cat))
						this.category = cat;
				}
			} else
				throw new IllegalArgumentException("Property " + prop
						+ " doesn't exist!");
		}

	}

}
