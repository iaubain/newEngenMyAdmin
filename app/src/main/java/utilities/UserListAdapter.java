package utilities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.olranz.payfuel.engenmyadmin.R;

import java.util.List;

import simplebean.userbean.UserDetails;

/**
 * Created by Hp on 11/14/2016.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private Context context;
    private List<UserDetails> mUsers;
    private UserListInteraction userListInteraction;
    private Dialog dialog;
    private Typeface font;

    public UserListAdapter(Context context,  UserListInteraction userListInteraction, List<UserDetails> mUsers, Dialog dialog) {
        this.context = context;
        this.mUsers = mUsers;
        this.userListInteraction = userListInteraction;
        this.dialog=dialog;
        font=Typeface.createFromAsset(context.getAssets(), "font/ubuntu.ttf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_style, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final UserDetails userDetails=mUsers.get(position);
        holder.listItem.setText(userDetails.getUserId()+". "+userDetails.getFname());
        holder.listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userListInteraction.onUserListInteraction(1, userDetails.getUserId(), userDetails, dialog);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView listItem;

        public ViewHolder(View v) {
            super(v);
            listItem = (TextView) v.findViewById(R.id.userElement);
            listItem.setTypeface(font, Typeface.BOLD);
        }
    }

    public interface UserListInteraction{
        void onUserListInteraction(int status, int pumpAgentId, Object object, Dialog dialog);
    }
}
