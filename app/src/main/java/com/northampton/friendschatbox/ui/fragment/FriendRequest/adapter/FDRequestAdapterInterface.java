package com.northampton.friendschatbox.ui.fragment.FriendRequest.adapter;

import com.northampton.friendschatbox.data.models.FriendRequestData;
import com.northampton.friendschatbox.data.models.UserDetails;

public interface FDRequestAdapterInterface {

    void onItemClicked(FriendRequestData userDetails, String email);

    void onDeleteCtaClicked(Integer id);

    void setEditableText(Integer id, String name);
}
