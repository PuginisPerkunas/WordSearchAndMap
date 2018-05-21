package buttons.games.sounds.darbouzduotisv7.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import buttons.games.sounds.darbouzduotisv7.R;

public class RcItemAdapter extends RecyclerView.Adapter<RcItemAdapter.ItemHolder> {

    private Context context;
    private List<String> itemsList;

    public static class ItemHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public ItemHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.item_tv);
        }
    }

    public RcItemAdapter(Context context, List<String> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item, parent, false);
        return new ItemHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            holder.mTextView.setText(itemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }



}
