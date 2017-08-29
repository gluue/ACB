package arik.acb;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Craig on 8/28/2017.
 */

public class HomeViewHolder extends RecyclerView.ViewHolder {
    TextView itemName;
    RatingBar itemRating;
    ImageView imageView;

    public HomeViewHolder(View view){
        super(view);
        this.itemName = (TextView)view.findViewById(R.id.textViewHomeTitle);
        this.itemRating = (RatingBar)view.findViewById(R.id.ratingBarHome);
        this.imageView = (ImageView)view.findViewById(R.id.imageViewHomeImage);
    }
}
