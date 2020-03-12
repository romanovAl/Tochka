package ru.romanovAl.tochkatest.model;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import ru.romanovAl.tochkatest.R;

public class CustomAlertDialog extends AlertDialog {

    private Button buttonSearch, buttonCancel;

    private EditText editTextSearch;

    public CustomAlertDialog(@NonNull Context context) {
        super(context);

        View customAlertDialog = getLayoutInflater()
                .inflate(R.layout.custom_alert_dialog,null);

        setTitle("Введите имя пользователя");

        buttonSearch =customAlertDialog.findViewById(R.id.button_search);
        buttonCancel = customAlertDialog.findViewById(R.id.button_cancel);
        editTextSearch = customAlertDialog.findViewById(R.id.editText_search);


        super.setView(customAlertDialog);

    }
    public void setButtonSearchOnClick(View.OnClickListener onClickListener) {
        buttonSearch.setOnClickListener(onClickListener);
    }

    public void setButtonCancelOnClick(View.OnClickListener onClickListener){
        buttonCancel.setOnClickListener(onClickListener);
    }

    public void setEditTextOnClick(View.OnClickListener onClickListener){
        editTextSearch.setOnClickListener(onClickListener);
    }

    public String getEditTextSearchText(){
        return editTextSearch.getText().toString();
    }



}
