package buttons.games.sounds.darbouzduotisv7.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import buttons.games.sounds.darbouzduotisv7.Models.HistoryModel;
import buttons.games.sounds.darbouzduotisv7.R;

public class HistoryAdapter extends ArrayAdapter<HistoryModel> {
    private List<HistoryModel> historyModelList;

    public HistoryAdapter(@NonNull Context context, @NonNull List<HistoryModel> historyModels) {
        super(context, 0, historyModels);
        historyModelList = new ArrayList<>(historyModels);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return historyFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.history_autocomplete_row, parent, false
            );
        }
        TextView word = convertView.findViewById(R.id.wordItem_tv);
        TextView partOfSpeech = convertView.findViewById(R.id.partOfSpeechItem_tv);
        TextView dateSearch = convertView.findViewById(R.id.dateSearch_tv);

        HistoryModel historyModel = getItem(position);

        if (historyModel != null) {
            word.setText(historyModel.getWord());
            partOfSpeech.setText(historyModel.getPartOfSpeech());
            dateSearch.setText(historyModel.getDateSearch());
        }

        return convertView;
    }

    private Filter historyFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<HistoryModel> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(historyModelList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (HistoryModel item : historyModelList) {
                    if (item.getWord().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((HistoryModel) resultValue).getWord();
        }
    };
}
