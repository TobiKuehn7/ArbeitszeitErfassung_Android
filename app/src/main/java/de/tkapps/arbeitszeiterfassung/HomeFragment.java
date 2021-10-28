package de.tkapps.arbeitszeiterfassung;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import de.tkapps.arbeitszeiterfassung.databinding.FragmentHomeBinding;
import de.tkapps.arbeitszeiterfassung.helpers.SavingHelpers;
import de.tkapps.arbeitszeiterfassung.helpers.TimeHelpers;
import de.tkapps.arbeitszeiterfassung.models.Workday;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // change start/end button
        binding.btnStart.setVisibility(View.VISIBLE);

        String[] fileParts = SavingHelpers.getFilePath();
        File file = new File(getActivity().getExternalFilesDir(fileParts[0]), fileParts[1] + ".csv");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (SavingHelpers.fileHasStartedWorkday(getActivity())) {
                resetUIAfterBtn(0);
                Workday startedWorkday = SavingHelpers.getStartedWorkday(getActivity());
                Date startDateTime = TimeHelpers.saveStringToDate(startedWorkday.getDateTimeStart());
                String startTime = TimeHelpers.dateToShowString(startDateTime);
                binding.txtBeginnArbeitszeit.setText("Beginn: " + startTime);
            }
        }

        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set UI elements
                resetUIAfterBtn(0);

                // get time of work beginnig
                Date dateTime = new Date();
                String dateTimeStart = TimeHelpers.dateToShowString(dateTime);
                binding.txtBeginnArbeitszeit.setText(getText(R.string.beginnArbeitszeit) + dateTimeStart);

                // save time to file and db if possible
                SavingHelpers.saveStartTime(getActivity(), TimeHelpers.dateToSaveString(dateTime));
                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();
            }
        });

        binding.btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set UI elements
                resetUIAfterBtn(1);

                // get time of work ending
                Date dateEnd = new Date();
                String dateTimeEnd = TimeHelpers.dateToShowString(dateEnd);
                binding.txtEndeArbeitszeit.setText(getText(R.string.endeArbeitszeit) + dateTimeEnd);

                // get start time
                String timeStart = (String) binding.txtBeginnArbeitszeit.getText();
                Date dateStart = TimeHelpers.showStringToDate(timeStart);

                // calc difference in time between work beginning and end (brutto time)
                Date dateDifference = TimeHelpers.calcTimeDiff(dateStart, dateEnd);
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                formatter.setTimeZone(TimeZone.getTimeZone("MEZ"));
                binding.txtArbeitszeitBrutto.setText("Arbeitszeit (brutto): " + formatter.format(dateDifference));
                String workTimeBrutto = formatter.format(dateDifference);
                String workTimeNetto;
                String pauseTime;

                // if date diff is bigger than 6 hours, substract 30 mins. If it is bigger than 9 hours substract 45 mins
                int halfHour = 1800000;
                int quaterHour = 2700000;
                int sixHours = 21600000;
                int nineHours = 32400000;
                if (dateDifference.getTime() >= nineHours) {
                    // substract 45 mins
                    Date date = new Date(dateDifference.getTime() - quaterHour);
                    binding.txtArbeitszeitNetto.setText("Arbeitszeit (netto): " + formatter.format(date));
                    workTimeNetto = formatter.format(date);
                    date = new Date(quaterHour);
                    binding.txtPausenzeit.setText("Pausenzeit: " + formatter.format(date));
                    pauseTime = formatter.format(date);
                    binding.txtPausenzeit.setVisibility(View.VISIBLE);
                } else if (dateDifference.getTime() >= sixHours) {
                    // substract 30 mins
                    Date date = new Date(dateDifference.getTime() - halfHour);
                    binding.txtArbeitszeitNetto.setText("Arbeitszeit (netto): " + formatter.format(date));
                    workTimeNetto = formatter.format(date);
                    date = new Date(halfHour);
                    binding.txtPausenzeit.setText("Pausenzeit: " + formatter.format(date));
                    pauseTime = formatter.format(date);
                    binding.txtPausenzeit.setVisibility(View.VISIBLE);
                } else {
                    // dont need to substract anything
                    Date date = new Date(dateDifference.getTime());
                    binding.txtArbeitszeitNetto.setText("Arbeitszeit (netto): " + formatter.format(date));
                    workTimeNetto = formatter.format(date);
                    date = new Date(0);
                    pauseTime = formatter.format(date);
                }

                // get start time
                String startTime = (String) binding.txtBeginnArbeitszeit.getText();
                startTime = startTime.split(": ")[1];
                Date dateTimeStart = TimeHelpers.showStringToDate(startTime);

                // save time to file and db if possible
                SavingHelpers.saveEndTime(getActivity(),TimeHelpers.dateToSaveString(dateTimeStart), TimeHelpers.dateToSaveString(dateEnd), workTimeBrutto, workTimeNetto, pauseTime);
                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();

                resetUIAfterBtn(1);
            }
        });
        
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    /**
     * this method resets all UI elements at startup of the start method to clean out all old and
     * already saved data
     * @param state represents the state the buttons are in (0 = Button was 'START', 1 = Button was 'ENDE')
     */
    private void resetUIAfterBtn(int state) {

        if (state == 0) {
            // reset all buttons
            binding.btnStart.setVisibility(View.GONE);
            binding.btnEnd.setVisibility(View.VISIBLE);

            // reset all texts
            binding.txtBeginnArbeitszeit.setText(R.string.beginnArbeitszeit);
            binding.txtEndeArbeitszeit.setText(R.string.endeArbeitszeit);
            binding.txtArbeitszeitBrutto.setText(R.string.arbeitszeitBrutto);
            binding.txtPausenzeit.setText(R.string.pausenzeit);
            binding.txtArbeitszeitNetto.setText(R.string.arbeitszeitNetto);
        } else {
            binding.btnStart.setVisibility(View.VISIBLE);
            binding.btnEnd.setVisibility(View.GONE);
        }

    }
}