package com.northampton.friendschatbox.ui.fragment.FriendRequest.adapter;

import com.northampton.friendschatbox.data.models.FriendRequestData;
import com.northampton.friendschatbox.data.models.UserDetails;

public interface FDRequestAdapterInterface {

    void onAddItemClicked(FriendRequestData userDetails, String email);

    void onDeleteCtaClicked(FriendRequestData userDetails, String email);

    void setEditableText(Integer id, String name);
}
