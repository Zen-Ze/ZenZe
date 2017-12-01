package com.ucd.comp41690.team21.zenze.fragments;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.support.v4.app.DialogFragment;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucd.comp41690.team21.zenze.R;

public class InfoDialogFragment extends DialogFragment {
    public interface InfoDialogListener {
        void onInfoDialogPositiveClick(DialogFragment dialog);
        void onInfoDialogNegativeClick(DialogFragment dialog);
    }
    InfoDialogListener mListener;
    private String name;
    private String infoText;
    private Bitmap image;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            name = args.getString("Name");
            infoText = args.getString("Info");
            image = args.getParcelable("Image");
        } else{
            infoText = "display info here.";
        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (InfoDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }

    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_info, null);
        TextView nameView = view.findViewById(R.id.info_Name);
        ImageView imageView = view.findViewById(R.id.info_ImageView);
        TextView textView = view.findViewById(R.id.info_TextView);
        imageView.setImageBitmap(image);
        textView.setText(infoText);
        nameView.setText(name);

        builder.setPositiveButton(R.string.infoDialog_Continue, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onInfoDialogPositiveClick(InfoDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.infoDialog_Return, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onInfoDialogNegativeClick(InfoDialogFragment.this);
                    }
                })
                .setTitle(R.string.infoDialog_Title)
                .setView(view);

        return builder.create();
    }

    @Override
    public void onCancel (DialogInterface dialog){
        mListener.onInfoDialogNegativeClick(InfoDialogFragment.this);
    }

}
