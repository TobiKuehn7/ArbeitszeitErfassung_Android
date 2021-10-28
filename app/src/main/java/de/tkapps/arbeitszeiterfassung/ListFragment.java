package de.tkapps.arbeitszeiterfassung;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;

import de.tkapps.arbeitszeiterfassung.databinding.FragmentListBinding;
import de.tkapps.arbeitszeiterfassung.helpers.SavingHelpers;
import de.tkapps.arbeitszeiterfassung.models.Workday;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;
    ArrayList<Workday> workdays;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = binding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        workdays = (ArrayList<Workday>) SavingHelpers.getAllWorkdays(getActivity());

        CustomAdapter customAdapter = new CustomAdapter();
        binding.workdayList.setAdapter(customAdapter);
        binding.workdayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), workdays.get(i).getHash(), Toast.LENGTH_LONG).show();
            }
        });

        binding.addWorkday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // for passing data into new fragment use bundles (https://stackoverflow.com/questions/59452243/android-navigation-component-pass-value-arguments-in-fragments)
                Toast.makeText(getActivity(), "Add a new Workday", Toast.LENGTH_LONG).show();
                NavHostFragment.findNavController(ListFragment.this)
                        .navigate(R.id.action_SecondFragment_to_ThirdFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return workdays.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View layout = getLayoutInflater().inflate(R.layout.workday_list_item, null);
            TextView startTime = layout.findViewById(R.id.txt_beginn_arbeitszeit);
            TextView endTime = layout.findViewById(R.id.txt_ende_arbeitszeit);
            TextView nettoTime = layout.findViewById(R.id.txt_arbeitszeit_netto);

            startTime.setText("Beginn: " + workdays.get(i).getDateTimeStart());
            endTime.setText("Ende: " + workdays.get(i).getDateTimeEnd());
            nettoTime.setText("Brutto Arbeitszeit: " + workdays.get(i).getWorkTimeNetto());

            return layout;
        }
    }

}