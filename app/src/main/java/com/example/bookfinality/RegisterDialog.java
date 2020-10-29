package com.example.bookfinality;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;


public class RegisterDialog extends DialogFragment {

    private EditText emailText;
    private EditText nameText;
    private EditText phoneText;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment,null,false);
        emailText=v.findViewById(R.id.email_edit_text);
        nameText=v.findViewById(R.id.name_edit_text);
        phoneText=v.findViewById(R.id.phone_edit_text);
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        AlertDialog dialog=builder
                .setView(v)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (phoneText.getText().toString().length()>14){
                            Toast.makeText(getActivity(),getActivity().getString(R.string.phone_invalid),Toast.LENGTH_SHORT).show();
                        }
                        else if (phoneText.getText().toString().isEmpty() || nameText.getText().toString().isEmpty()){
                            Toast.makeText(getActivity(),getActivity().getString(R.string.empty_field),Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.e("REG","now");
                            FireBaseWorker.getWorker().addToDatabase(emailText.getText().toString(), phoneText.getText().toString(),
                                    nameText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Toast.makeText(getActivity(), R.string.thanks, Toast.LENGTH_SHORT).show();
                                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
                                    }
                                    else  Toast.makeText(getActivity(), R.string.gone_wrong, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        getTargetFragment().onActivityResult(getTargetRequestCode(),Activity.RESULT_CANCELED,null);
                    }
                })
                .setNegativeButton(R.string.later, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED,null);
                    }
                })
                .create();
        return dialog;
    }

}