package com.example.tugasbesar_03;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.birin.gridlistviewadapters.Card;
import com.birin.gridlistviewadapters.ListGridAdapter;
import com.birin.gridlistviewadapters.dataholders.CardDataHolder;
import com.birin.gridlistviewadapters.utils.ChildViewsClickHandler;

class SimplestDemoAdadpter extends ListGridAdapter<Item, ViewHolder> {

    public SimplestDemoAdadpter(Context context, int totalCardsInRow) {
        super(context, totalCardsInRow);
    }

    @Override
    protected Card<ViewHolder> getNewCard(int cardwidth) {
        // Create card through XML (can be created programmatically as well.)
        View cardView = getLayoutInflater().inflate(R.layout.simplest_card_layout, null);
        cardView.setMinimumHeight(cardwidth);

        // Now create card view holder.
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.textView = (TextView) cardView.findViewById(R.id.name);

        return new Card<ViewHolder>(cardView, viewHolder);
    }

    @Override
    protected void setCardView(CardDataHolder<Item> cardDataHolder,
                               ViewHolder cardViewHolder) {
        Item item = cardDataHolder.getData();
        cardViewHolder.textView.setText(item.getPositionText());
    }

    @Override
    protected void onCardClicked(Item cardData) {
        Toast.makeText(getContext(),
                "Card click " + cardData.getPositionText(), Toast.LENGTH_LONG)
                .show();
    }

    private final int TEXT_VIEW_CLICK_ID = 0;

    @Override
    protected void registerChildrenViewClickEvents(ViewHolder cardViewHolder,
                                                   ChildViewsClickHandler childViewsClickHandler) {
        childViewsClickHandler.registerChildViewForClickEvent(
                cardViewHolder.textView, TEXT_VIEW_CLICK_ID);
    }

    @Override
    protected void onChildViewClicked(View clickedChildView, Item cardData, int eventId) {
        if (eventId == TEXT_VIEW_CLICK_ID) {
            Toast.makeText(getContext(),
                    "TextView click " + cardData.getPositionText(),
                    Toast.LENGTH_LONG).show();
        }
    }
}