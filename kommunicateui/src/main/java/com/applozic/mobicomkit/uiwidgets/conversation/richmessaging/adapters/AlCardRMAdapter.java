package com.applozic.mobicomkit.uiwidgets.conversation.richmessaging.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.applozic.mobicomkit.api.conversation.Message;
import com.applozic.mobicomkit.uiwidgets.R;
import com.applozic.mobicomkit.uiwidgets.conversation.richmessaging.AlRichMessage;
import com.applozic.mobicomkit.uiwidgets.conversation.richmessaging.callbacks.ALRichMessageListener;
import com.applozic.mobicomkit.uiwidgets.conversation.richmessaging.models.ALRichMessageModel;
import com.applozic.mobicommons.json.GsonUtils;
import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.List;

import static android.view.View.GONE;

public class AlCardRMAdapter extends ALRichMessageAdapter {

    private List<ALRichMessageModel.ALPayloadModel> payloadList;

    AlCardRMAdapter(Context context, ALRichMessageModel model, ALRichMessageListener listener, Message message) {
        super(context, model, listener, message);
        this.payloadList = Arrays.asList((ALRichMessageModel.ALPayloadModel[])
                GsonUtils.getObjectFromJson(model.getPayload(), ALRichMessageModel.ALPayloadModel[].class));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.al_rich_message_item, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bindItems(holder, position);
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        LinearLayout roomRootLayout;
        TextView productNameSingleLine;
        TextView productPrice;
        ImageView productImage;
        TextView productRating;
        TextView productLocation;
        TextView productDescription;
        RelativeLayout productNameSplitLayout;
        TextView productName;
        View productImageOverlay;
        TextView bookAction1, bookAction2, bookAction3;
        View viewAction1, viewAction2, viewAction3;

        public CardViewHolder(View itemView) {
            super(itemView);

            roomRootLayout = itemView.findViewById(R.id.roomRootLayout);
            productNameSingleLine = itemView.findViewById(R.id.productNameSingleLine);
            productImage = itemView.findViewById(R.id.productImage);
            productRating = itemView.findViewById(R.id.productRating);
            productLocation = itemView.findViewById(R.id.productLocation);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDescription = itemView.findViewById(R.id.productDescription);
            productName = itemView.findViewById(R.id.productName);
            productImageOverlay = itemView.findViewById(R.id.productImageOverlay);
            productNameSplitLayout = itemView.findViewById(R.id.productNameSplitLayout);
            productRating = itemView.findViewById(R.id.productRating);
            bookAction1 = itemView.findViewById(R.id.bookingAction1);
            bookAction2 = itemView.findViewById(R.id.bookingAction2);
            bookAction3 = itemView.findViewById(R.id.bookingAction3);
            viewAction1 = itemView.findViewById(R.id.viewAction1);
            viewAction2 = itemView.findViewById(R.id.viewAction2);
            viewAction3 = itemView.findViewById(R.id.viewAction3);
        }
    }

    @Override
    protected void bindItems(RecyclerView.ViewHolder holder, int position) {
        super.bindItems(holder, position);
        CardViewHolder viewHolder = (CardViewHolder) holder;
        if (payloadList != null) {
            final ALRichMessageModel.ALPayloadModel payloadModel = payloadList.get(position);

            if (payloadModel.getHeader() != null && !TextUtils.isEmpty(payloadModel.getHeader().getImgSrc())) {
                Glide.with(context).load(payloadModel.getHeader().getImgSrc()).into(viewHolder.productImage);
                viewHolder.productImage.setVisibility(View.VISIBLE);
                viewHolder.productImageOverlay.setVisibility(View.VISIBLE);
                viewHolder.productPrice.setBackground(context.getResources().getDrawable(R.drawable.al_rich_messaging_price_border));
            } else {
                viewHolder.productImage.setVisibility(View.GONE);
                viewHolder.productImageOverlay.setVisibility(GONE);
                viewHolder.productPrice.setBackground(context.getResources().getDrawable(R.drawable.al_imageless_rich_message_price_border));
            }

            if (payloadModel.getHeader() != null && !TextUtils.isEmpty(payloadModel.getHeader().getOverlayText())) {
                viewHolder.productPrice.setText(payloadModel.getHeader().getOverlayText());
                viewHolder.productPrice.setVisibility(View.VISIBLE);
            } else {
                viewHolder.productPrice.setVisibility(View.GONE);
            }

            if (TextUtils.isEmpty(payloadModel.getTitleExt())) {
                viewHolder.productNameSplitLayout.setVisibility(GONE);
                if (!TextUtils.isEmpty(payloadModel.getTitle())) {
                    viewHolder.productNameSingleLine.setVisibility(View.VISIBLE);
                    viewHolder.productNameSingleLine.setText(payloadModel.getTitle());
                } else {
                    viewHolder.productNameSingleLine.setVisibility(View.GONE);
                }
            } else {
                viewHolder.productNameSplitLayout.setVisibility(View.VISIBLE);
                viewHolder.productNameSingleLine.setVisibility(View.GONE);
                viewHolder.productName.setVisibility(View.VISIBLE);
                viewHolder.productRating.setText(payloadModel.getTitleExt());

                if (!TextUtils.isEmpty(payloadModel.getTitle())) {
                    viewHolder.productName.setText(payloadModel.getTitle());
                } else {
                    viewHolder.productName.setText("");
                }
            }

            if (!TextUtils.isEmpty(payloadModel.getSubtitle())) {
                viewHolder.productLocation.setVisibility(View.VISIBLE);
                viewHolder.productLocation.setText(AlRichMessage.getHtmlText(payloadModel.getSubtitle()));
            } else {
                viewHolder.productLocation.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(payloadModel.getDescription())) {
                viewHolder.productDescription.setVisibility(View.VISIBLE);
                viewHolder.productDescription.setText(AlRichMessage.getHtmlText(payloadModel.getDescription()));
            } else {
                viewHolder.productDescription.setVisibility(View.GONE);
            }

            viewHolder.bookAction1.setVisibility(View.GONE);
            viewHolder.bookAction2.setVisibility(View.GONE);
            viewHolder.bookAction3.setVisibility(View.GONE);
            viewHolder.viewAction1.setVisibility(View.GONE);
            viewHolder.viewAction2.setVisibility(View.GONE);
            viewHolder.viewAction3.setVisibility(View.GONE);

            if (payloadModel.getButtons() != null && !payloadModel.getButtons().isEmpty()) {
                try {
                    List<ALRichMessageModel.AlButtonModel> actionsList = payloadModel.getButtons();
                    for (int i = 0; i < actionsList.size(); i++) {
                        if (i == 0) {
                            viewHolder.bookAction1.setVisibility(View.VISIBLE);
                            viewHolder.viewAction1.setVisibility(View.VISIBLE);
                            viewHolder.bookAction1.setText(actionsList.get(0).getName());
                            viewHolder.bookAction1.setOnClickListener(getGenericCardClickListener(actionsList.get(0)));
                        }

                        if (i == 1) {
                            viewHolder.bookAction2.setVisibility(View.VISIBLE);
                            viewHolder.viewAction2.setVisibility(View.VISIBLE);
                            viewHolder.bookAction2.setText(actionsList.get(1).getName());
                            viewHolder.bookAction2.setOnClickListener(getGenericCardClickListener(actionsList.get(1)));
                        }

                        if (i == 2) {
                            viewHolder.bookAction3.setVisibility(View.VISIBLE);
                            viewHolder.viewAction3.setVisibility(View.VISIBLE);
                            viewHolder.bookAction3.setText(actionsList.get(2).getName());
                            viewHolder.bookAction3.setOnClickListener(getGenericCardClickListener(actionsList.get(2)));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return payloadList.size();
    }
}
