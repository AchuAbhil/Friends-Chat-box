package com.northampton.friendschatbox.ui.helper;

import com.northampton.friendschatbox.data.models.UserDetails;

public interface AdapterInterface {
    void onItemClicked(UserDetails userDetails, String email);

    void onDeleteCtaClicked(Integer id);

    void setEditableText(Integer id, String name);
}
