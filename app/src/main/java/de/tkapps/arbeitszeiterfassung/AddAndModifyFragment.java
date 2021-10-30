package de.tkapps.arbeitszeiterfassung;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

import de.tkapps.arbeitszeiterfassung.databinding.FragmentAddAndModifyBinding;
import de.tkapps.arbeitszeiterfassung.databinding.FragmentHomeBinding;


public class AddAndModifyFragment extends Fragment {

    private FragmentAddAndModifyBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAddAndModifyBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        binding.editDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "." + month + "." + year;
                        binding.editDateStart.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        binding.editDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "." + month + "." + year;
                        binding.editDateEnd.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        binding.editTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOfHour) {
                        calendar.set(0, 0, 0, hourOfDay, minuteOfHour);
                        binding.editTimeStart.setText(DateFormat.format("HH:mm", calendar));
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });

        binding.editTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOfHour) {
                        calendar.set(0, 0, 0, hourOfDay, minuteOfHour);
                        binding.editTimeEnd.setText(DateFormat.format("HH:mm", calendar));
                    }
                }, hour, minute, true);
                timePickerDialog.show();
            }
        });


        binding.checkStartEndSameDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.checkStartEndSameDay.isChecked()) {
                    binding.editDateEnd.setVisibility(View.GONE);
                } else {
                    binding.editDateEnd.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AddAndModifyFragment.this)
                        .navigate(R.id.action_ThirdFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}