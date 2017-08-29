package arik.acb;

/**
 * Created by Craig on 8/28/2017.
 */

public class HistoryItem {
    String itemType, itemName;


    int homePageRanking;

    public void setHomePageRanking(int homePageRanking) {
        this.homePageRanking = homePageRanking;
    }

    public int getHomePageRanking() {
        return homePageRanking;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }
}
