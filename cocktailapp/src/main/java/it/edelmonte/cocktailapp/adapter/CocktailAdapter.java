package it.edelmonte.cocktailapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import it.edelmonte.cocktailapp.R;
import it.edelmonte.cocktailapp.model.Cocktail;

public class CocktailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<Cocktail> mDataset;
    private List<Cocktail> filteredDataset;
    private Activity activity;
    private CocktailFilter cocktailFilter;

    public CocktailAdapter(List<Cocktail> mDataset, Activity activity) {
        this.mDataset = mDataset;
        this.activity = activity;
        this.filteredDataset = mDataset;
    }

    public static class ViewHolderList extends RecyclerView.ViewHolder {

        public View layout;
        public ImageView cocktailImage;
        public TextView cocktailName;

        public ViewHolderList(@NonNull View itemView) {
            super(itemView);
            this.layout = itemView;
            this.cocktailImage = itemView.findViewById(R.id.cocktail_image);
            this.cocktailName = itemView.findViewById(R.id.cocktail_name);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        viewHolder = new CocktailAdapter.ViewHolderList(inflater.inflate(R.layout.adapter_cocktail, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CocktailAdapter.ViewHolderList) {
            // Set views values
            CocktailAdapter.ViewHolderList holderCasted = (ViewHolderList) holder;
            holderCasted.cocktailName.setText(filteredDataset.get(position).getName());
            Glide.with(activity).load(filteredDataset.get(position).getImage()).into(holderCasted.cocktailImage);
        }
    }

    @Override
    public int getItemCount() {
        return filteredDataset.size();
    }

    @Override
    public Filter getFilter() {
        if (cocktailFilter == null) {
            cocktailFilter = new CocktailFilter();
        }
        return cocktailFilter;
    }

    private class CocktailFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Cocktail> tempList = new ArrayList<>();
                for (Cocktail cocktail : mDataset) {
                    if (cocktail.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(cocktail);
                    }
                    filterResults.count = tempList.size();
                    filterResults.values = tempList;
                }
            } else {
                filterResults.count = mDataset.size();
                filterResults.values = mDataset;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredDataset = (ArrayList<Cocktail>) results.values;
            notifyDataSetChanged();
        }
    }
}
