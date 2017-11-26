package com.ucd.comp41690.team21.zenze.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucd.comp41690.team21.zenze.R;

public class GameOverDialogFragment extends DialogFragment {
    public interface GameOverDialogListener {
        public void onGameOverDialogPositiveClick(DialogFragment dialog);
        public void onGameOverDialogNegativeClick(DialogFragment dialog);
    }
    GameOverDialogListener mListener;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (GameOverDialogListener) activity;
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
        ImageView imageView = view.findViewById(R.id.info_ImageView);
        TextView textView = view.findViewById(R.id.info_TextView);
        imageView.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.character));
        textView.setText(R.string.gameOverDialog_Text);

        builder.setPositiveButton(R.string.gameOverDialog_Continue, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onGameOverDialogPositiveClick(GameOverDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.gameOverDialog_Return, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onGameOverDialogNegativeClick(GameOverDialogFragment.this);
                    }
                })
                .setTitle(R.string.gameOverDialog_Title)
                .setView(view);

        return builder.create();
    }

    @Override
    public void onCancel (DialogInterface dialog){
        mListener.onGameOverDialogNegativeClick(GameOverDialogFragment.this);
    }

}
