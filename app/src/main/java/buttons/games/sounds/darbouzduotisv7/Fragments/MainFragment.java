package buttons.games.sounds.darbouzduotisv7.Fragments;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import buttons.games.sounds.darbouzduotisv7.Adapters.RcItemAdapter;
import buttons.games.sounds.darbouzduotisv7.Models.DefinitionExampleModel;
import buttons.games.sounds.darbouzduotisv7.Models.DefinitionModel;
import buttons.games.sounds.darbouzduotisv7.Models.ExamplesModel;
import buttons.games.sounds.darbouzduotisv7.R;

public class MainFragment extends Fragment  {

    ExamplesModel examplesObject;
    DefinitionExampleModel definitionExampleObject;
    List<DefinitionModel> definitionObjectList;
    List<String> definitionsStringList;

    Bundle bundle;

    TextView wordTv, partOfSpeechTv;
    private RecyclerView definitionsRv;
    private RecyclerView examplesRv;

    View view;
    Context context;

    RcItemAdapter rcItemAdapter;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);
        init();
        getBundle();
        wordTv.setText(examplesObject.getWord());
        partOfSpeechTv.setText(getPartOfSpeech(definitionExampleObject));
        checkIfListNotEmpty();
        setRecyclerViews();

        return view;
    }

    private void init(){
        definitionsRv = view.findViewById(R.id.my_recycler_view_definitions);
        examplesRv = view.findViewById(R.id.my_recycler_view_examples);
        context = Objects.requireNonNull(getActivity()).getBaseContext();
        wordTv = view.findViewById(R.id.word_tv);
        partOfSpeechTv = view.findViewById(R.id.partOfSpeechMain_tv);
    }

    private void getBundle(){
        bundle = this.getArguments();
        if (bundle != null) {
            examplesObject = bundle.getParcelable(context.getString(R.string.KEY_EXAMPLES));
            definitionExampleObject = bundle.getParcelable(context.getString(R.string.KEY_DEFINITION));
            assert definitionExampleObject != null;
            definitionObjectList  = definitionExampleObject.getDefinitions();
        }
    }

    private String getPartOfSpeech(DefinitionExampleModel definitionExampleObject){
        String partOfSpeech;
        if( definitionExampleObject.getDefinitions().size() > 0){
            partOfSpeech = definitionExampleObject.getDefinitions().get(0).getPartOfSpeech();
        } else {
            partOfSpeech = context.getString(R.string.part_of_speech_empty);
        }
        return partOfSpeech;
    }

    private void createAndAddDefStringList(){
        definitionsStringList = new ArrayList<>();
        for (DefinitionModel item: definitionObjectList) {
            definitionsStringList.add(item.getDefinition());
        }
    }

    private void checkIfListNotEmpty(){
        createAndAddDefStringList();
        if(definitionsStringList.isEmpty())
            definitionsStringList.add(context.getString(R.string.nothing_txt));
        if (examplesObject.getExamples().isEmpty())
            examplesObject.getExamples().add(context.getString(R.string.nothing_txt));
    }

    private void setRecyclerViews(){
        definitionsRv.setLayoutManager(new LinearLayoutManager(context));
        rcItemAdapter = new RcItemAdapter(context,definitionsStringList);
        definitionsRv.setAdapter(rcItemAdapter);
        examplesRv.setLayoutManager(new LinearLayoutManager(context));
        rcItemAdapter = new RcItemAdapter(context,examplesObject.getExamples());
        examplesRv.setAdapter(rcItemAdapter);
    }
}
