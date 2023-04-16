package com.example.myZeitplan;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myZeitplan.CalendarDay;
import com.example.myZeitplan.Note;
public class AddNoteDialogFragment extends DialogFragment {

    private EditText titleEditText;
    private EditText contentEditText;
    private Button saveButton;
    private Button cancelButton;
    private OnNoteAddedListener onNoteAddedListener;
    private CalendarDay calendarDay;

    public interface OnNoteAddedListener {
        void onNoteAdded(Note note);
    }

    public static AddNoteDialogFragment newInstance(CalendarDay calendarDay) {
        AddNoteDialogFragment fragment = new AddNoteDialogFragment();
        fragment.calendarDay = calendarDay;
        return fragment;
    }

    public void setOnNoteAddedListener(OnNoteAddedListener listener) {
        onNoteAddedListener = listener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_note_layout, container, false);

        titleEditText = view.findViewById(R.id.titleEditText);
        contentEditText = view.findViewById(R.id.contentEditText);
        saveButton = view.findViewById(R.id.saveButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();
                if (!title.isEmpty() && !content.isEmpty()) {
                    Note note = new Note();
                    note.setTitle(title);
                    note.setContent(content);
                    note.setTimestamp(calendarDay.getDateInMillis());
                    if (onNoteAddedListener != null) {
                        onNoteAddedListener.onNoteAdded(note);
                    }
                    dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                dismiss();
            }
        };
    }
}
