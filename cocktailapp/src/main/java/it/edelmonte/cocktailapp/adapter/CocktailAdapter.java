package it.edelmonte.cocktailapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import it.edelmonte.cocktailapp.R;
import it.edelmonte.cocktailapp.model.Cocktail;

public class CocktailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Cocktail> mDataset;
    private Activity activity;

    public CocktailAdapter(List<Cocktail> mDataset, Activity activity) {
        this.mDataset = mDataset;
        this.activity = activity;
    }

    public static class ViewHolderList extends RecyclerView.ViewHolder{

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
        if(holder instanceof  CocktailAdapter.ViewHolderList){
            // Set views values
            CocktailAdapter.ViewHolderList holderCasted = (ViewHolderList) holder;
            holderCasted.cocktailName.setText(mDataset.get(position).getName());
            Glide.with(activity).load(mDataset.get(position).getImage()).centerCrop().into(holderCasted.cocktailImage);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
