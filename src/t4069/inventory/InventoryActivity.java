package t4069.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.annotation.SuppressLint;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class InventoryActivity extends Activity {
	protected static final String ADD_NEW_CATEGORY = "Add a new category";
	private Button addButton, removeButton, filterButton, resetFilterButton,
			signOutButton, viewButton;
	private String filterName, filterNumber, filterManufacturer,
			filterCategory;
	private ListView partView;
	private int selectedPartPosition = -1;
	private Dialog filter, add, signOut, view, category;
	private static final int ADD = 1, SIGNOUT = 2, EDIT = 3, FILTER = 4;
	private static final String TAG = "FRC Inventory";
	private ArrayList<CharSequence> categories = new ArrayList<CharSequence>();
	private ArrayList<Part> parts = new ArrayList<Part>();
	private OnClickListener buttonListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button1: {
				add.show();
				break;
			}
			case R.id.button2: {
				view();
				break;
			}
			case R.id.button3: {
				signOut.show();
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		categories.add("None");
		categories.add(ADD_NEW_CATEGORY);
		setContentView(R.layout.activity_main);
		partView = (ListView) findViewById(R.id.listView1);
		partView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				selectedPartPosition = arg2;
			}
		});
		refreshParts();
		addButton = (Button) findViewById(R.id.button1);
		removeButton = (Button) findViewById(R.id.button4);
		viewButton = (Button) findViewById(R.id.button2);
		signOutButton = (Button) findViewById(R.id.button3);
		filterButton = (Button) findViewById(R.id.button5);
		resetFilterButton = (Button) findViewById(R.id.button6);
		makeDialogs();
		addButton.setOnClickListener(buttonListener);
		removeButton.setOnClickListener(buttonListener);
		viewButton.setOnClickListener(buttonListener);
		signOutButton.setOnClickListener(buttonListener);
		filterButton.setOnClickListener(buttonListener);
		resetFilterButton.setOnClickListener(buttonListener);
		loadPreferences();
	}

	protected void view() {
		view = new Dialog(this);
		View viewLayout = getLayoutInflater().inflate(R.layout.part_view, null);
		view.setTitle("View Part");
		view.setContentView(viewLayout);
		Part viewPart = (Part) parts.get(selectedPartPosition);
		final TextView nameField = (TextView) viewLayout.findViewById(R.id.nameViewView);
		final TextView numberField = (TextView) viewLayout.findViewById(R.id.numberViewView);
		final TextView manufacturerField = (TextView) viewLayout.findViewById(R.id.manufacturerViewView);
		final TextView categoryField = (TextView) viewLayout.findViewById(R.id.categoryViewView);
		final TextView quantityField = (TextView) viewLayout.findViewById(R.id.quantityViewView);
		nameField.setText(viewPart.name);
		numberField.setText(viewPart.number);
		manufacturerField.setText(viewPart.manufacturer);
		categoryField.setText(viewPart.category);
		quantityField.setText(viewPart.quantity);
		view.show();

	}

	private void loadPreferences() {
		refreshParts();
	}

	private void makeDialogs() {
		LayoutInflater inflater = getLayoutInflater();
		View category_layout = inflater.inflate(R.layout.category_dialog, null);
		makeCategoryDialog(category_layout);
		View add_layout = inflater.inflate(R.layout.add_dialog, null);
		makeAddDialog(add_layout);
		
	}

	private ArrayList<CharSequence> getModifiableCategories() {
		@SuppressWarnings("unchecked")
		ArrayList<CharSequence> mCategories = (ArrayList<CharSequence>) categories
				.clone();
		mCategories.remove("None");
		mCategories.remove(ADD_NEW_CATEGORY);
		return mCategories;
	}

	String selectedCategory;

	private void makeCategoryDialog(View category_layout) {
		category = new Dialog(this);
		category.setTitle("Category");
		category.setContentView(category_layout);
		final EditText categoryNameField = (EditText) category_layout
				.findViewById(R.id.categoryNameField);
		final ListView categoryListView = (ListView) category_layout
				.findViewById(R.id.categoryListView);
		final Button addButton = (Button) category_layout
				.findViewById(R.id.addCategoryButton);
		final Button deleteButton = (Button) category_layout
				.findViewById(R.id.deleteCategoryButton);
		categoryListView.setAdapter(new ArrayAdapter<CharSequence>(this,
				android.R.layout.simple_expandable_list_item_1,
				getModifiableCategories()));
		categoryListView.requestFocus();
		categoryListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				selectedCategory = (String) arg0.getItemAtPosition(arg2);
			}
		});
		((Button) category_layout.findViewById(R.id.cancelCategoryButton))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						category.dismiss();
					}
				});
		addButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final String name = categoryNameField.getText().toString();
				categories.add(name);
				categoryListView.setAdapter(new ArrayAdapter<CharSequence>(
						getApplicationContext(),
						android.R.layout.simple_expandable_list_item_1,
						getModifiableCategories()));
			}
		});
		deleteButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				categoryListView.setAdapter(new ArrayAdapter<CharSequence>(
						getApplicationContext(),
						android.R.layout.simple_expandable_list_item_1,
						getModifiableCategories()));

				if (selectedCategory == null) {
					Toast.makeText(getApplicationContext(),
							"No category selected!", Toast.LENGTH_SHORT).show();
					return;
				}
				for (int i = 0; i < categories.size(); i++) {
					if (selectedCategory.equals(categories.get(i))) {
						categories.remove(i);
						break;
					}
				}
				categoryListView.setAdapter(new ArrayAdapter<CharSequence>(
						getApplicationContext(),
						android.R.layout.simple_expandable_list_item_1,
						getModifiableCategories()));
			}
		});
	}

	private void makeAddDialog(View add_layout) {
		add = new Dialog(this);
		add.setTitle("Add Part");
		add.setContentView(add_layout);
		final Spinner addCategories = (Spinner) add_layout
				.findViewById(R.id.spinner1);
		addCategories.setAdapter((new ArrayAdapter<CharSequence>(
				getApplicationContext(),
				android.R.layout.simple_expandable_list_item_1, categories)));
		final Button m_addButton = (Button) add_layout
				.findViewById(R.id.addDialogButton1);
		final Button m_cancelButton = (Button) add_layout
				.findViewById(R.id.addDialogButton2);
		final EditText nameField = (EditText) add_layout
				.findViewById(R.id.editText1);
		final EditText partField = (EditText) add_layout
				.findViewById(R.id.editText2);
		final EditText manufacturerField = (EditText) add_layout
				.findViewById(R.id.editText3);
		m_addButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (nameField.getText().toString() == null
						&& partField.getText().toString() == null) {
					Toast.makeText(getApplicationContext(), "No name or ID!",
							Toast.LENGTH_SHORT).show();
					add.dismiss();
				}
				String name = "";
				String manufacturer = "";
				String partNum = "";
				String category;
				name = nameField.getText().toString();
				manufacturer = manufacturerField.getText().toString();
				partNum = partField.getText().toString();
				category = addCategories.getSelectedItem().toString();
				if (parts.contains(new Part(name, partNum, manufacturer,
						category))) {
					Toast.makeText(getApplicationContext(),
							"Part already exists!", Toast.LENGTH_SHORT).show();
					refreshParts();
					return;
				}
				parts.add(new Part(name, partNum, manufacturer, category));
				refreshParts();
				add.dismiss();
			}
		});
		m_cancelButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				add.dismiss();
			}
		});
		addCategories.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String categoryname = (String) addCategories.getSelectedItem();
				if (categoryname.equals(ADD_NEW_CATEGORY)) {
					addCategories.setSelection(0);
					category.show();
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});
	}

	protected void remove() {
		if (selectedPartPosition == -1) {
			Toast.makeText(this, "No part selected!", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		Part removePart = (Part) parts.get(selectedPartPosition);
		parts.remove(removePart);
		refreshParts();

	}

	protected void resetFilter() {

	}

	protected void filter() {
		// filter.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	final String[] keys = new String[] { "Name", "Number" };

	protected void refreshParts() {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for (Part part : parts) {
			Map<String, String> datum = new HashMap<String, String>(keys.length);
			datum.put(keys[0], part.name);
			datum.put(keys[1], part.number);
			data.add(datum);
		}
		SimpleAdapter viewAdapter = new SimpleAdapter(getApplicationContext(),
				data, android.R.layout.simple_list_item_2, keys, new int[] {
						android.R.id.text1, android.R.id.text2 });
		partView.setAdapter(viewAdapter);
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private SimpleAdapter filter(ArrayList<Part> list) {
		ArrayList<Part> parts = (ArrayList<Part>) this.parts.clone();
		boolean numberFiltered = filterNumber != null;
		boolean manufacturerFiltered = filterManufacturer != null, categoryFiltered = filterCategory != null;
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for (Part part : parts) {
			if ((filterName.length() != 0 && !part.name
					.equalsIgnoreCase(filterName))
					|| (filterNumber.length() != 0 && !part.number
							.equalsIgnoreCase(filterNumber))
					|| (filterManufacturer.length() != 0 && !part.manufacturer
							.equalsIgnoreCase(filterManufacturer))
					|| (filterCategory.length() != 0 && !part.category
							.equalsIgnoreCase(filterCategory))) {
				parts.remove(part);
				continue;
			}
			Map<String, String> datum = new HashMap<String, String>(keys.length);
			datum.put(keys[0], part.name);
			datum.put(keys[1], part.number);
			data.add(datum);
		}

		SimpleAdapter viewAdapter = new SimpleAdapter(getApplicationContext(),
				data, android.R.layout.simple_list_item_2, keys, new int[] {
						android.R.id.text1, android.R.id.text2 });
		return viewAdapter;
	}

	class Part {
		public CharSequence quantity;
		String name;
		String number;
		String manufacturer;
		String category;

		public Part(String name, String number, String manufacturer,
				String category) {
			this.name = name;
			this.number = number;
			this.manufacturer = manufacturer;
			setStr("category", category);
		}

		public boolean equals(Object other) {
			if (other == this)
				return true;
			if (!(other instanceof Part))
				return false;
			Part otherPart = (Part) other;
			if (otherPart.name.equals(name) && otherPart.number.equals(number)) {
				return true;
			}
			return false;
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
				for (CharSequence cat : categories) {
					if (category.equals(cat))
						this.category = (String) cat;
				}
			} else
				throw new IllegalArgumentException("Property " + prop
						+ " doesn't exist!");
		}

	}

}
