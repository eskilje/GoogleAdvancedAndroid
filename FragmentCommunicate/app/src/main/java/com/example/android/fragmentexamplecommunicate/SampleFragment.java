package com.example.android.fragmentexamplecommunicate;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SampleFragment extends Fragment {

    private static final int NONE = 2;
    private static final int YES = 1;
    private static final int NO = 0;
    private static final String CHOICE = "choice";
    public int radioButtonChoice = NONE;
    OnFragmentInteractionListener listener;

    public SampleFragment() {
        // Required empty public constructor
    }

    public static SampleFragment newInstance(int choice) {
        SampleFragment sampleFragment = new SampleFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(CHOICE, choice);
        sampleFragment.setArguments(arguments);
        return sampleFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        final View rootView =
                inflater.inflate(R.layout.fragment_sample, container, false);
        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);

        if(getArguments().containsKey(CHOICE)) {
            radioButtonChoice = getArguments().getInt(CHOICE);
            if(radioButtonChoice != NONE)
            {
                radioGroup.check(radioGroup.getChildAt(radioButtonChoice).getId());
            }
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);
                TextView textView =
                        rootView.findViewById(R.id.fragment_header);
                switch (index) {
                    case YES: // User chose "Yes."
                        textView.setText(R.string.yes_message);
                        radioButtonChoice = YES;
                        listener.onRadioButtonChoice(YES);
                        break;
                    case NO: // User chose "No."
                        textView.setText(R.string.no_message);
                        radioButtonChoice = NO;
                        listener.onRadioButtonChoice(NO);
                        break;
                    default:
                        radioButtonChoice = NONE;
                        listener.onRadioButtonChoice(NONE);
                        break;
                }
            }
        });

        // Return the View for the fragment's UI.
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        }
        else
            throw new ClassCastException(context.toString() + getResources().getString(R.string.exception_message));
    }

}
