package t4069.inventory;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	// TODO: USE DIALOGS, ASSHOLE!
	private Button addButton, removeButton, filterButton, resetFilterButton,
			signOutButton, editButton;
	private ListView partView;
	private Dialog filter, add, signOut, edit;
	private CharSequence[] categories = {"No category"};
private OnClickListener buttonListener = new OnClickListener() {
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1: {
			add();
			break;}
		case R.id.button2: {
			edit();
			break;}
		case R.id.button3: {
			signOut();
			break;}
		case R.id.button4: {
			remove();
			break;}
		case R.id.button5: {
			filter();
			break;}
		case R.id.button6: {
			resetFilter();
			break;}
		}
	}
};

DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
	
	public void onClick(DialogInterface dialog, int which) {

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
	}

	protected void add() {
		
	}

	protected void edit() {
		
	}

	protected void signOut() {
		
	}

	protected void remove() {
		
	}

	protected void resetFilter() {
		
	}

	protected void filter() {
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void makeDialogs() {
		final CharSequence[] items = {"Red", "Green", "Blue"};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Pick a color");
		builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
		    }
		});
		AlertDialog filter = builder.create();
	}
	
	
}
