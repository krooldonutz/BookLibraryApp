package com.example.labweek5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.labweek5.provider.BookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.StringTokenizer;

import androidx.core.view.GestureDetectorCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    DatabaseReference mRef;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyRecyclerAdapter adapter;
    private static final String DEBUG_TAG = "WEEK10_TAG";
    private BookViewModel mBookViewModel;
    private int availableRow;
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);

//        mRef= FirebaseDatabase.getInstance("https://booklibraryapp-44a53-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Book/book");


        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS, android.Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        MyBroadcastReceiver myBroadCastReceiver = new MyBroadcastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.dl);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);

        NavigationView navigationView = findViewById(R.id.navigationMenu);
        navigationView.setNavigationItemSelectedListener(new MyDrawerListener());

        recyclerView = findViewById(R.id.recycler);

        layoutManager = new LinearLayoutManager(this);  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager

        adapter = new MyRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        mBookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        mBookViewModel.getAllBooks().observe(this, newData -> {
            adapter.setData(newData);
            adapter.notifyDataSetChanged();
            availableRow = newData.size();
        });
        FirebaseDatabase database= FirebaseDatabase.getInstance("https://booklibraryapp-44a53-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mRef = database.getReference("Book/books");
        Log.i("lab3", "onCreate");
        View view=findViewById(R.id.frame_layout_id);
        GestureListener myGestureListener = new GestureListener(this);
        mDetector = new GestureDetectorCompat(this, myGestureListener);
        mDetector.setOnDoubleTapListener(myGestureListener);
        view.setOnTouchListener(this);
//        view.setOnTouchListener(new View.OnTouchListener() {
//            private int startX;
//            private int endX;
//            private int startY;
//            private int endY;
//            private boolean isSwipingRight;
//            WorkerThread workerThread = new WorkerThread();
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getActionMasked();
//                switch(action) {
//                    case (MotionEvent.ACTION_DOWN) :
//                        startX=(int)event.getX();
//                        startY = (int)event.getY();
//                        int topLeftx = v.getWidth()/4;
//                        int topLefty = v.getHeight()/4;
//                        if (startX <= topLeftx && startY <= topLefty){
//                            EditText Author = findViewById(R.id.Author);
//                            String s = Author.getText().toString();
//                            String res = s.substring(0, 1).toUpperCase() + s.substring(1);
//                            Author.setText(res);
//                        }
//                        Log.d(DEBUG_TAG,"Action was DOWN at x="+startX+ " and y="+startY);
//                        return true;
//                    case (MotionEvent.ACTION_MOVE) :
//                        startY = (int)event.getY();
//                        if (endX > startX) {
////                             Swiped from right to left
//                            EditText Price = findViewById(R.id.Price);
//                            int amount = 1 + Integer.parseInt(Price.getText().toString());
//                            Price.setText(Integer.toString(amount));
//                            isSwipingRight = true;
//
//                        }
////                        if (isSwipingRight){
////                            workerThread.setStopped(false);
////                            workerThread.run();
////                            System.out.println("testing");
////                        }
//
//                        Log.d(DEBUG_TAG,"Action was MOVE at x="+startX+ " and endX="+endX+ isSwipingRight);
//                        return true;
//                    case (MotionEvent.ACTION_UP) :
//                        endY = (int)event.getY();
//                        endX = (int)event.getX();
//                        workerThread.setStopped(true);
//                        isSwipingRight = false;
//                        workerThread.stopThread();
//                        if (endX < startX) {
//                            addToast(findViewById(R.id.addMenu));
//                        }
//                        if (startY < endY) {
//                            clearFields(null);
//                        }
//                        if (startY > endY) {
//                            finish();
//
//                        }
//                        Log.d("DEBUG_TAG","Action was UP at startY="+startY+ " and endY="+endY);
//                        return true;
//                    default :
//                        return false;
//                }
//            }
//        });
    }


    protected void onStart(){
        super.onStart();
        SharedPreferences myData = getSharedPreferences("f1", 0);
        String id = myData.getString("id","");
        String title = myData.getString("title","");
        String isbn = myData.getString("isbn","");
        String description = myData.getString("description","");
        String author = myData.getString("author","");
        String price = myData.getString("price","");

        EditText Title = findViewById(R.id.Title);
        EditText Price = findViewById(R.id.Price);
        EditText ID = findViewById(R.id.ID);
        EditText ISBN = findViewById(R.id.ISBN);
        EditText Author = findViewById(R.id.Author);
        EditText Description = findViewById(R.id.Description);

        if (!id.equals("")){
            ID.setText(id);
        }
        if (!title.equals("")){
            Title.setText(title);
        }
        if (!isbn.equals("")){
            ISBN.setText(isbn);
        }
        if (!description.equals("")){
            Description.setText(description);
        }
        if (!author.equals("")){
            Author.setText(author);
        }
        if (!price.equals("")){
            Price.setText(price);
        }

        Log.i("lab3", "onStart");
    }

    protected void onPause(){
        super.onPause();
        Log.i("lab3", "onPause");
    }

    protected void onResume(){
        super.onResume();
        Log.i("lab3", "onResume");
    }

    //    @Override
    protected void onStop() {
        super.onStop();

        EditText Title = findViewById(R.id.Title);
        EditText Price = findViewById(R.id.Price);
        EditText ID = findViewById(R.id.ID);
        EditText ISBN = findViewById(R.id.ISBN);
        EditText Author = findViewById(R.id.Author);
        EditText Description = findViewById(R.id.Description);

        SharedPreferences myData = getSharedPreferences("f1", 0);
        SharedPreferences.Editor myEditor = myData.edit();
        myEditor.putString("title", Title.getText().toString());
        myEditor.putString("price", Price.getText().toString());
        myEditor.putString("id", ID.getText().toString());
        myEditor.putString("isbn", ISBN.getText().toString());
        myEditor.putString("author", Author.getText().toString());
        myEditor.putString("description", Description.getText().toString());
        myEditor.commit();

        Log.i("lab3", "onStop");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.i("lab3", "onDestroy");
    }


    public void nextActivity(View v){
        Intent myIntent = new Intent(this, MainActivity2.class);
        startActivity(myIntent);
    }

    public void loadLast(View v){
        SharedPreferences myData = getSharedPreferences("f1", 0);
        String id = myData.getString("id","");
        String title = myData.getString("title","");
        String isbn = myData.getString("isbn","");
        String description = myData.getString("description","");
        String author = myData.getString("author","");
        String price = myData.getString("price","");

        EditText Title = findViewById(R.id.Title);
        EditText Price = findViewById(R.id.Price);
        EditText ID = findViewById(R.id.ID);
        EditText ISBN = findViewById(R.id.ISBN);
        EditText Author = findViewById(R.id.Author);
        EditText Description = findViewById(R.id.Description);

        if (!id.equals("")){
            ID.setText(id);
        }
        if (!title.equals("")){
            Title.setText(title);
        }
        if (!isbn.equals("")){
            ISBN.setText(isbn);
        }
        if (!description.equals("")){
            Description.setText(description);
        }
        if (!author.equals("")){
            Author.setText(author);
        }
        if (!price.equals("")){
            Price.setText(price);
        }

    }

    public void addToast(View v){
        EditText Title = findViewById(R.id.Title);
        EditText Price = findViewById(R.id.Price);
        String output = Title.getText().toString() + " ($" + Price.getText().toString() +") " +"is added";
//        Toast myMessage = Toast.makeText(this , output, Toast.LENGTH_SHORT);
        Snackbar myMessage = Snackbar.make(v, output, Snackbar.LENGTH_LONG).setAction("undo", undoListener);
        myMessage.show();

        EditText ID = findViewById(R.id.ID);
        EditText ISBN = findViewById(R.id.ISBN);
        EditText Author = findViewById(R.id.Author);
        EditText Description = findViewById(R.id.Description);

        SharedPreferences myData = getSharedPreferences("f1", 0);
        SharedPreferences.Editor myEditor = myData.edit();
        myEditor.putString("title", Title.getText().toString());
        myEditor.putString("price", Price.getText().toString());
        myEditor.putString("id", ID.getText().toString());
        myEditor.putString("isbn", ISBN.getText().toString());
        myEditor.putString("author", Author.getText().toString());
        myEditor.putString("description", Description.getText().toString());
        myEditor.commit();


        Book book = new Book(Title.getText().toString(), Author.getText().toString(),
                Price.getText().toString(),ID.getText().toString(),ISBN.getText().toString(),
                Description.getText().toString());
        availableRow++;
        book.setRow(availableRow);
        Log.i("row", Integer.toString(availableRow));
        mBookViewModel.insert(book);
        mRef.push().setValue(book);
        adapter.notifyDataSetChanged();
    }

    View.OnClickListener undoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mBookViewModel.removeRow(availableRow);
            adapter.notifyDataSetChanged();
        }
    };

    public void removeUnknown(){
        mBookViewModel.deleteUnknown();
        adapter.notifyDataSetChanged();
    }


    public void clearFields(View v){
        EditText Title = findViewById(R.id.Title);
        EditText ID = findViewById(R.id.ID);
        EditText ISBN = findViewById(R.id.ISBN);
        EditText Price = findViewById(R.id.Price);
        EditText Author = findViewById(R.id.Author);
        EditText Description = findViewById(R.id.Description);
        Title.getText().clear();
        ID.getText().clear();
        ISBN.getText().clear();
        Price.getText().clear();
        Author.getText().clear();
        Description.getText().clear();
        Toast myMessage = Toast.makeText(this , "Fields are cleared!", Toast.LENGTH_SHORT);
        myMessage.show();
    }

    public void doublePrice(View v){
        EditText Price = findViewById(R.id.Price);
        int amount = 2 * Integer.parseInt(Price.getText().toString());
        Price.setText(Integer.toString(amount));
    }

    public void isbnChange(View v){
        SharedPreferences myData = getSharedPreferences("f1", 0);
        SharedPreferences.Editor myEditor = myData.edit();
        myEditor.putString("isbn", "00112233");
        myEditor.commit();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
        String title = savedInstanceState.getString("Title");
        String isbn = savedInstanceState.getString("ISBN");
        EditText Title = findViewById(R.id.Title);
        EditText ISBN = findViewById(R.id.ISBN);
        if (!title.equals("")){
            Title.setText(title);
        }
        if (!isbn.equals("")){
            ISBN.setText(isbn);
        }
        EditText ID = findViewById(R.id.ID);
        EditText Price = findViewById(R.id.Price);
        EditText Author = findViewById(R.id.Author);
        EditText Description = findViewById(R.id.Description);
        ID.getText().clear();
        Price.getText().clear();
        Author.getText().clear();
        Description.getText().clear();
        Log.i("lab3", "onRestore");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
        EditText Title = findViewById(R.id.Title);
        EditText ISBN = findViewById(R.id.ISBN);
        outState.putString("Title", Title.getText().toString());
        outState.putString("ISBN",ISBN.getText().toString() );
        Log.i("lab3", "onSave");
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mDetector.onTouchEvent(motionEvent);
        return true;
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            /*
             * Retrieve the message from the intent
             * */
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            /*
             * String Tokenizer is used to parse the incoming message
             * The protocol is to have the account holder name and account number separate by a semicolon
             * */

            StringTokenizer sT = new StringTokenizer(msg, "|");
            String id = sT.nextToken();
            String title = sT.nextToken();
            String isbn = sT.nextToken();
            String author = sT.nextToken();
            String description = sT.nextToken();
            String price = sT.nextToken();
            int priceINT = Integer.parseInt(price);
            String priceBool = sT.nextToken();
            boolean price_boolean = Boolean.parseBoolean(priceBool);

            EditText Title = findViewById(R.id.Title);
            EditText Price = findViewById(R.id.Price);
            EditText ID = findViewById(R.id.ID);
            EditText ISBN = findViewById(R.id.ISBN);
            EditText Author = findViewById(R.id.Author);
            EditText Description = findViewById(R.id.Description);

            ID.setText(id);
            Title.setText(title);
            ISBN.setText(isbn);
            Author.setText(author);
            Description.setText(description);
            Price.setText(price);

            if (price_boolean){
                Price.setText(Integer.toString(priceINT + 100));
            }
            else{
                Price.setText(Integer.toString(priceINT + 5));
            }

            Log.i("lab4", "SMS");
        }
    }
    class MyDrawerListener implements NavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.addMenu) {
                addToast(findViewById(R.id.floatingActionButton));

            } else if (id == R.id.removeLast) {
                if (availableRow > 0){
                    mBookViewModel.removeRow(availableRow);
                    adapter.notifyDataSetChanged();
                }
            }
            else if(id == R.id.clearMenu){
                if (availableRow > 0){
                    mBookViewModel.deleteAll();
                    mRef.removeValue();
                    availableRow = 0;
                    adapter.notifyDataSetChanged();
                }
            }
            else if(id == R.id.closeApp){
                finish();
            }
            else if(id == R.id.listMenu){
                nextActivity(null);
            }
            // close the drawer
            drawerLayout.closeDrawers();
            // tell the OS
            return true;
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options,menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.clearOptions) {
            clearFields(null);
        } else if (id == R.id.loadData) {
            loadLast(null);
        }
        else if(id == R.id.totalBooks){
            String output = availableRow + " Books";
            Toast myMessage = Toast.makeText(this , output, Toast.LENGTH_SHORT);
            myMessage.show();
        }
        else if(id == R.id.removeUnknown){
            removeUnknown();
        }
        // close the drawer

        // tell the OS
        return true;
//        return super.onOptionsItemSelected(item);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Log.d(DEBUG_TAG,"Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.d(DEBUG_TAG,"Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.d(DEBUG_TAG,"Action was UP");
                return true;
            default :
                return false;
        }
    }
    class WorkerThread extends Thread{

        public void setStopped(boolean stopped) {
            this.stopped = stopped;
        }

        private volatile boolean stopped = false;

        @Override
        public void run(){
            super.run();
            System.out.println(stopped + "stopped");
            while(!stopped){
                //do your work here
                EditText Price = findViewById(R.id.Price);
                int amount = 1 + Integer.parseInt(Price.getText().toString());
                Price.setText(Integer.toString(amount));
                System.out.println("mama mia");
            }
        }

        public void stopThread(){
            stopped = true;
        }
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        Activity activity;
        public GestureListener(Activity activity) {
            this.activity = activity;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            // Generate a new random ISBN
            String isbn = generateRandomISBN();
            EditText isbnOld = findViewById(R.id.ISBN);
            isbnOld.setText(isbn);
            Log.d("week10","test");
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // Clear all fields
            clearFields(null);
            Log.d("week10","test");
            return true;
        }

        private String generateRandomISBN() {
            // Generate a random 10-digit number
            int number = (int) (Math.random() * 1000000000);

            // Create an ISBN string
            String isbn = Integer.toString(number);
            Log.d("week10","test");
            // Return the ISBN string
            return isbn;
        }
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // Increment the price by the amount of scroll
            EditText price = findViewById(R.id.Price);
            boolean dir = e1.getX()  < e2.getX();

            if (dir) {
                int newPrice = Integer.parseInt(price.getText().toString()) + (int) distanceX * -1;
                price.setText(String.valueOf(newPrice));
            }

            // Decrement the price by the amount of scroll
            else {
                int newPrice = Integer.parseInt(price.getText().toString()) - (int) distanceX ;
                price.setText(String.valueOf(newPrice));
            }


            if (distanceY > 0 || distanceY < 0) {
                EditText title =findViewById(R.id.Title);
                title.setText("untitled");
            }
            return true;
        }
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // Move the app to the background
            if (velocityY > 2500){
                activity.moveTaskToBack(true);
            }
//            Log.d("week10",Integer.toString((int) velocityY));
            return true;
        }

        public void onLongPress (MotionEvent e){
            loadLast(null);
        }

    }

}