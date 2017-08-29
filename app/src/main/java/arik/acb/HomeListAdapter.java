package arik.acb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Craig on 8/28/2017.
 */

public class HomeListAdapter extends RecyclerView.Adapter<HomeViewHolder> {
    LayoutInflater inflater;
    List<Dispensary> dList;
    List<Product> pList;
    List<String> totalList;
    List<HistoryItem> historyItemList;
    TextView itemName, itemAddress;
    RatingBar itemRating;
    int length, itemCount;

    public HomeListAdapter(Context c, List<HistoryItem> historyList){
        inflater = LayoutInflater.from(c);
        historyItemList = historyList;
        //dList = dispensaryList;
        //pList = productList;
        itemCount = 0;

        length = dList.size() + pList.size();

    }


    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {

        if(SuperVar.historyList.size()<position){

            for(Dispensary disp : SuperVar.dispensaryList){
                if(SuperVar.historyList.get(position).getItemType().equals(disp.getDispensaryName())){
                    holder.itemName.setText(dList.get(itemCount).getDispensaryName());
                    holder.itemRating.setRating(dList.get(position).getHomeRank());
                    holder.imageView.setBackground(dList.get(position).getImage());
                }
            }

            for(Product p : SuperVar.productList){
                if(SuperVar.historyList.get(position).getItemName().equals(p.getProductName())){
                    holder.itemName.setText(pList.get(itemCount).getProductName());
                    holder.itemRating.setRating(pList.get(position).getHomeRank());
                    holder.imageView.setBackground(pList.get(position).getImage());
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return SuperVar.historyList.size();
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.home_list_item_layout, parent, false);

        return new HomeViewHolder(view);
    }

}
