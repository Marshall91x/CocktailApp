package it.edelmonte.cocktailapp.adapter;

import static org.koin.java.KoinJavaComponent.inject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import it.edelmonte.cocktailapp.R;
import it.edelmonte.cocktailapp.api.CocktailApiClient;
import it.edelmonte.cocktailapp.api.CocktailApiInterface;
import it.edelmonte.cocktailapp.fragment.DetailFragment;
import it.edelmonte.cocktailapp.model.Cocktail;
import it.edelmonte.cocktailapp.model.CocktailList;
import it.edelmonte.cocktailapp.util.CloudManager;
import kotlin.Lazy;

public class CocktailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<Cocktail> mDataset;
    private List<Cocktail> filteredDataset;
    private Activity activity;
    private Fragment fragment;
    private CocktailFilter cocktailFilter;
    private CocktailApiInterface apiService;
    private final Lazy<CloudManager> cloudManager = inject(CloudManager.class);

    public CocktailAdapter(List<Cocktail> mDataset, Activity activity, Fragment fragment) {
        this.mDataset = mDataset;
        this.activity = activity;
        this.filteredDataset = mDataset;
        this.fragment = fragment;
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
            holderCasted.cocktailName.setText(filteredDataset.get(holder.getAdapterPosition()).name);
            Glide.with(activity).load(filteredDataset.get(holder.getAdapterPosition()).image).into(holderCasted.cocktailImage);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadData(filteredDataset.get(holder.getAdapterPosition()).id);
                }
            });
        }
    }

    private void loadData(String id) {
        apiService = CocktailApiClient.getClient().create(CocktailApiInterface.class);
        Observable<CocktailList> observable = apiService.getCocktailById("lookup.php?i=" + id);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<CocktailList>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull CocktailList cocktailList) {
                 cloudManager.getValue().setCocktail(cocktailList.cocktails.get(0));
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                activity.runOnUiThread(() ->{
                    new AlertDialog.Builder(activity).setTitle(R.string.attention).setMessage(R.string.loading_error).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            loadData(id);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();
                });
            }

            @Override
            public void onComplete() {
                //Open detail dialog fragment
                DialogFragment dialogFragment = new DetailFragment();
                dialogFragment.show(fragment.getChildFragmentManager(), "cocktail_detail");
            }
        });
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
                    if (cocktail.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
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
