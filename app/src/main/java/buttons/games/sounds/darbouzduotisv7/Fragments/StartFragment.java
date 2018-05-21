package buttons.games.sounds.darbouzduotisv7.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easyandroidanimations.library.FadeInAnimation;
import com.easyandroidanimations.library.ScaleInAnimation;

import buttons.games.sounds.darbouzduotisv7.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {


    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        TextView text = view.findViewById(R.id.start_text);
        new FadeInAnimation(text).setDuration(3000).animate();
        return view;
    }

}
