package com.service.andrewsamir.main.adaptors;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.service.andrewsamir.main.Activities.Data_Show;
import com.service.andrewsamir.main.R;
import com.service.andrewsamir.main.Singleton.SingletonDataShow;
import com.service.andrewsamir.main.data.Name;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by andre on 29-Apr-17.
 */

public class KashfAdapter extends RecyclerView.Adapter<KashfAdapter.MyViewHolder> {

    private List<Name> kashfList;
    ArrayList<Name> searchList;
    Activity activity;
    DBhelper myDB;
    DatabaseReference myRef;
    FirebaseDatabase database;

    public KashfAdapter(List<Name> kashfList, Activity activity) {
        this.kashfList = kashfList;
        this.searchList = new ArrayList<>();
        this.searchList.addAll(kashfList);
        this.activity = activity;
        myDB = new DBhelper(activity);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.names_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Name kashfItem = kashfList.get(position);

        holder.tvName.setText(kashfItem.getName());
        holder.num.setText(position + 1 + "");

        if (kashfItem.getHomeNo().equals("e") || kashfItem.getStreet().equals("e"))
            holder.tvAddress.setVisibility(View.INVISIBLE);
        else
            holder.tvAddress.setText(kashfItem.getHomeNo() + " شارع " + kashfItem.getStreet());

        if (kashfItem.getLastEftkad().equals("e"))
            holder.tvLastEftkad.setText("-");
        else
            holder.tvLastEftkad.setText(kashfItem.getLastEftkad());

        if (kashfItem.getAttendance().equals("e"))
            holder.tvLastAttendance.setText("-");
        else {
            try {
                Date date = new Date(Long.parseLong(kashfItem.getAttendance())); // *1000 is to convert seconds to milliseconds
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // the format of your date
                sdf.setTimeZone(TimeZone.getTimeZone("GMT+2")); // give a timezone reference for formating (see comment at the bottom
                String formattedDate = sdf.format(date);
                holder.tvLastAttendance.setText(formattedDate);
            } catch (Exception e) {
                e.printStackTrace();
                holder.tvLastAttendance.setText("-");
            }
        }
        Log.d("lastImage", kashfItem.getKey());
        if (!kashfItem.getImage().equals("e")) {
            try {
                Bitmap bitmap = StringToBitMap(kashfItem.getImage());
                holder.circleImageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        holder.cardKashfItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemLongClick(position, kashfItem);
                return true;
            }
        });


        holder.cardKashfItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonDataShow.getInstance().setAnotherAdd(kashfItem.getAnotherAdd());
                SingletonDataShow.getInstance().setAttendance(kashfItem.getAttendance());
                SingletonDataShow.getInstance().setBirthdate(kashfItem.getBirthdate());
                SingletonDataShow.getInstance().setDescription(kashfItem.getDescription());
                SingletonDataShow.getInstance().setFlat(kashfItem.getFlat());
                SingletonDataShow.getInstance().setFloor(kashfItem.getFloor());
                SingletonDataShow.getInstance().setHomeNo(kashfItem.getHomeNo());
                SingletonDataShow.getInstance().setImage(kashfItem.getImage());
                SingletonDataShow.getInstance().setLastEftkad(kashfItem.getLastEftkad());
                SingletonDataShow.getInstance().setMama(kashfItem.getMama());
                SingletonDataShow.getInstance().setName(kashfItem.getName());
                SingletonDataShow.getInstance().setPapa(kashfItem.getPapa());
                SingletonDataShow.getInstance().setPhone(kashfItem.getPhone());
                SingletonDataShow.getInstance().setPlace(kashfItem.getPlace());
                SingletonDataShow.getInstance().setStreet(kashfItem.getStreet());
                SingletonDataShow.getInstance().setKey(kashfItem.getKey());

              //  activity.startActivity(new Intent(activity, Data_Show.class));

                Intent intent = new Intent(activity, Data_Show.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    Pair<View, String> pair1 = Pair.create((View) holder.circleImageView, "profile");
                    Pair<View, String> pair2 = Pair.create((View) holder.tvName, "name");
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(activity, pair1, pair2);
                    activity.startActivity(intent, options.toBundle());
                } else {
                    activity.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return kashfList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName, tvAddress, num, tvLastEftkad, tvLastAttendance;
        de.hdodenhof.circleimageview.CircleImageView circleImageView;
        CardView cardKashfItem;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.textViewshowname);
            tvAddress = (TextView) itemView.findViewById(R.id.textViewshowaddr);
            num = (TextView) itemView.findViewById(R.id.textViewnumber);
            tvLastEftkad = (TextView) itemView.findViewById(R.id.tvLastEftkad);
            tvLastAttendance = (TextView) itemView.findViewById(R.id.tvLastAttendance);
            circleImageView = (de.hdodenhof.circleimageview.CircleImageView) itemView.findViewById(R.id.ivProfile);
            cardKashfItem = (CardView) itemView.findViewById(R.id.cardKashfItem);
        }
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    // Filter method
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        kashfList.clear();
        if (charText.length() == 0) {
            kashfList.addAll(searchList);
        } else {
            for (Name s : searchList) {
                if (s.getName().contains(charText)) {
                    kashfList.add(s);
                }
            }
        }
        notifyDataSetChanged();
    }

    private void itemLongClick(final int position, final Name kashfItem) {

        final CharSequence[] items = {
                activity.getString(R.string.add_to_eftkad), activity.getString(R.string.add_eftkad_date), activity.getString(R.string.remove)
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // builder.setTitle("Choose Photo From ..");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                //mDoneButton.setText(items[item]);
                if (items[item].equals(activity.getString(R.string.add_to_eftkad))) {

                    addToEftkad(position, kashfItem);

                } else if (items[item].equals(activity.getString(R.string.add_eftkad_date))) {

                    createDialog(kashfItem.getKey());
                } else if (items[item].equals(activity.getString(R.string.remove))) {

                    warningRemove(position, kashfItem);
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void addToEftkad(int position, Name kashfItem) {

        boolean t = myDB.ADD(kashfItem.getName(),
                kashfItem.getHomeNo(),
                kashfItem.getStreet(),
                kashfItem.getImage(),
                kashfItem.getKey(),
                Integer.toString(position),
                kashfItem.getLastEftkad(),
                kashfItem.getAttendance());

        if (t)
            Toast.makeText(activity, "تم اضافة " + kashfItem.getName() + " الى الافتقاد ", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(activity, kashfItem.getName() + "  موجود فى الافتقاد ", Toast.LENGTH_SHORT).show();

    }

    private void warningRemove(final int position, final Name kashfItem) {

        new AlertDialog.Builder(activity)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete " + kashfItem.getName() + " ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        removeFromFirebase(position, kashfItem);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void removeFromFirebase(int position, Name kashfItem) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("fsol/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        myRef.keepSynced(true);

        myRef.child("list").child(kashfItem.getKey()).removeValue();

    }

    private void createDialog(final String uuid) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.custom_data_paker);
        dialog.setTitle("تسجيل تاريخ اخر إفتقاد ");

        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker2);

        Button cancel = (Button) dialog.findViewById(R.id.cancelAbsent);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        Button continueButton = (Button) dialog.findViewById(R.id.conToAbsent);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day, 0, 0, 0);
                long startTime = calendar.getTimeInMillis();
                String date = Long.toString(startTime);
                date = date.substring(0, date.length() - 3);
                date = date + "000";
                String dateShow = Integer.toString(day) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(year);
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                myRef = database.getReference("fsol/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/list/" + uuid + "/lastEftkad");
                myRef.keepSynced(true);

                myRef.setValue(dateShow);
                dialog.dismiss();

            }
        });
        dialog.show();
    }

}
