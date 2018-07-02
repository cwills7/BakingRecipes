package com.wills.carl.bakingrecipes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wills.carl.bakingrecipes.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment {

    @BindView(R.id.step_instructions) TextView instructions;


    Step step;
    public StepDetailFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getArguments() != null){
            step = (Step) getArguments().getSerializable("step");
        }

        View root = inflater.inflate(R.layout.step_detail_fragment, container, false);
        ButterKnife.bind(this, root);

        instructions.setText(step.getDescription());

        return root;
    }

}
