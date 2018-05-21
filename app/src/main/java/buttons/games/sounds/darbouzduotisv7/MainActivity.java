package buttons.games.sounds.darbouzduotisv7;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.irozon.sneaker.Sneaker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import buttons.games.sounds.darbouzduotisv7.Adapters.HistoryAdapter;
import buttons.games.sounds.darbouzduotisv7.Fragments.MainFragment;
import buttons.games.sounds.darbouzduotisv7.Fragments.StartFragment;
import buttons.games.sounds.darbouzduotisv7.Managers.JsonRequestManager;
import buttons.games.sounds.darbouzduotisv7.Managers.JsonToObjectManager;
import buttons.games.sounds.darbouzduotisv7.Models.DefinitionExampleModel;
import buttons.games.sounds.darbouzduotisv7.Models.ExamplesModel;
import buttons.games.sounds.darbouzduotisv7.Models.HistoryModel;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    ProgressDialog loadingDialog;
    DefinitionExampleModel definitionExampleObject;
    ExamplesModel examplesObject;

    FragmentTransaction fragmentTransaction;
    SupportMapFragment mapFragment;
    LinearLayout mapBox;

    private List<HistoryModel> historyItems;
    private boolean useCustom;
    String partOfSpeech;

    Button searchBtn, moreBtn;
    AutoCompleteTextView editText;
    HistoryAdapter adapter;
    SharedPreferences mPrefs  ;
    SharedPreferences.Editor prefsEditor ;

    int[] markersArray = {R.drawable.sharp_map_ic1_black_48,
            R.drawable.sharp_map_ic2_black_48,
            R.drawable.sharp_map_ic3_black_48,
            R.drawable.sharp_map_ic4_black_48,
            R.drawable.sharp_map_ic5_black_48};

    String KEY_HISTORY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JsonRequestManager.getInstance(this);
        init();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapBox.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(useCustom) {
                    map.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(definitionExampleObject.getWord())
                            .snippet(partOfSpeech)
                            .icon(bitmapDescriptorFromVector(MainActivity.this, getRandomMarker())));
                } else {
                    map.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(definitionExampleObject.getWord())
                            .snippet(partOfSpeech)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }
            }
        });
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        assert vectorDrawable != null;
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private int getRandomMarker(){
        Random random = new Random();
        return markersArray[random.nextInt(markersArray.length)];
    }

    private void init(){
        searchBtn = findViewById(R.id.search_btn);
        moreBtn = findViewById(R.id.bt_more);
        editText = findViewById(R.id.actv);
        mapBox = findViewById(R.id.map_box_ll);
        useCustom = false;
        fillHistoryList();
        setLoadingDialog();
        setListeners();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
        fragmentTransaction.replace(R.id.content_frame, new StartFragment());
        fragmentTransaction.commit();
    }

    @SuppressLint("CommitPrefEdits")
    private void fillHistoryList() {
        String KEY_SP = getString(R.string.KEY_SP);
        mPrefs = getSharedPreferences(KEY_SP, Context.MODE_PRIVATE);
        prefsEditor = mPrefs.edit();
        KEY_HISTORY = getString(R.string.KEY_HISTORY);

        historyItems = getHistoryItems();
        Collections.reverse(historyItems);
        adapter = new HistoryAdapter(this, historyItems);
        editText.setAdapter(adapter);
        editText.setHintTextColor(getResources().getColor(R.color.colorPrimary));
    }

    private List<HistoryModel> getHistoryItems(){
        Gson gson = new Gson();
        String json = mPrefs.getString(KEY_HISTORY, "");
        List<HistoryModel> historyObjectsList;
        if(json.equals(""))
        {
            historyObjectsList = new ArrayList<HistoryModel>();
            return historyObjectsList;

        } else {
            historyObjectsList = new ArrayList<HistoryModel> (Arrays.asList(gson.fromJson(json, HistoryModel[].class)));
            return historyObjectsList;
        }
    }

    private void saveHistoryObject (HistoryModel historyObject){
        historyItems = getHistoryItems();
        historyItems.add(historyObject);
        Gson gson = new Gson();
        String json = gson.toJson(historyItems);
        prefsEditor.putString(getString(R.string.KEY_HISTORY),json);
        prefsEditor.commit();
    }

    private void setLoadingDialog(){
        loadingDialog = new ProgressDialog(MainActivity.this);
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.setMessage(getString(R.string.loading_txt));
        loadingDialog.setIndeterminate(true);
        loadingDialog.setCanceledOnTouchOutside(false);
    }

    private void showErrorDialog(){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Opps!")
                .setMessage(getString(R.string.wrong_txt))
                .setNeutralButton(getString(R.string.try_again_txt), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    private String getCurrentTime(){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(getApplicationContext().getString(R.string.date_format));
        return sdf.format(new Date());
    }

    private void setListeners(){
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, moreBtn);
                popup.getMenuInflater().inflate(R.menu.main, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Sneaker.with(MainActivity.this)
                                .setTitle(getApplicationContext().getString(R.string.choose_txt))
                                .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                                .setMessage(item.getTitle().toString(),R.color.primaryTextColor)
                                .setDuration(2000)
                                .autoHide(true)
                                .sneak(R.color.colorAccent);

                        int id = item.getItemId();
                        if (id == R.id.action_main) {
                            useCustom = false;
                            return true;
                        } else if(id == R.id.action_custom){
                            useCustom = true;
                            return true;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getCurrentFocus();
                makeRequest(editText.getText().toString(), getString(R.string.PARAM_DEFINITION), getString(R.string.PARAM_EXAMPLES));
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert inputManager != null;
                assert view != null;
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    makeRequest(editText.getText().toString(), getString(R.string.PARAM_DEFINITION), getString(R.string.PARAM_EXAMPLES));
                }
                return false;
            }
        });

        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert inputManager != null;
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                }
            }

        });
    }

    private void makeRequest(final String word , final String PARAMETER_DEF, final String PARAMETER_EXAMP){
        loadingDialog.show();
        JsonRequestManager.jsonRequest(word, PARAMETER_DEF, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {

                JsonToObjectManager jsonToObjectManager = new JsonToObjectManager(response);
                definitionExampleObject = jsonToObjectManager.getDefinitionObject();
                JsonRequestManager.jsonRequest(word, PARAMETER_EXAMP, new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {

                        JsonToObjectManager jsonToObjectManagerSecond = new JsonToObjectManager(response);
                        examplesObject = jsonToObjectManagerSecond.getExamplesObject();

                        Bundle bundle = new Bundle();
                        bundle.putParcelable(getString(R.string.KEY_DEFINITION), definitionExampleObject);
                        bundle.putParcelable(getString(R.string.KEY_EXAMPLES), examplesObject);

                        MainFragment myFragment = new MainFragment();
                        myFragment.setArguments(bundle);

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
                        fragmentTransaction.replace(R.id.content_frame, myFragment);
                        fragmentTransaction.commit();


                        if( definitionExampleObject.getDefinitions().size() > 0){
                            partOfSpeech = definitionExampleObject.getDefinitions().get(0).getPartOfSpeech();
                        } else {
                            partOfSpeech = getString(R.string.part_of_speech_empty);
                        }

                        HistoryModel historyObjectToSave = new HistoryModel(
                                definitionExampleObject.getWord(),
                                partOfSpeech,
                                getCurrentTime());

                        saveHistoryObject(historyObjectToSave);
                        fillHistoryList();
                        mapBox.setVisibility(View.VISIBLE);
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onError(String message) {
                        Log.d("ResPONSE",message);
                        loadingDialog.dismiss();
                        showErrorDialog();
                    }
                });
            }
            @Override
            public void onError(String message) {
                Log.d("ResPONSE",message);
                loadingDialog.dismiss();
                showErrorDialog();
            }
        });
    }
}
