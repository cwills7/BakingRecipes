package com.wills.carl.bakingrecipes;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wills.carl.bakingrecipes.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetail extends AppCompatActivity {

    @BindView(R.id.back_button) Button backBtn;
    @BindView(R.id.next_button) Button nextBtn;

    int currentId;
    ArrayList<Step> stepList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_detail_fragment);
        ButterKnife.bind(this);

        final Step step = (Step) getIntent().getSerializableExtra("currentStep");
         stepList = (ArrayList<Step>) getIntent().getSerializableExtra("stepList");

        currentId = step.getId();

        checkBounds(currentId);

        Bundle bundle = new Bundle();
        bundle.putSerializable("step", step);
        updateFragments(bundle);

        //Create Fragments and add them.
        //Check to make sure the screen orientation.

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("step", stepList.get(currentId-1));
                updateFragments(b);
                currentId--;
                checkBounds(currentId);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("step", stepList.get(currentId+1));
                updateFragments(b);
                currentId++;
                checkBounds(currentId);
            }
        });



    }

    private void updateFragments(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setArguments(bundle);
        if(fragmentManager.findFragmentById(R.id.step_container) != null) {
            fragmentManager
                    .beginTransaction()
                    .remove(fragmentManager.findFragmentById(R.id.step_container)).commit();
        }
        fragmentManager
                .beginTransaction()
                .add(R.id.step_container, stepDetailFragment)
                .commit();
    }

    private void checkBounds(int currentId){
        backBtn.setVisibility(View.VISIBLE);
        nextBtn.setVisibility(View.VISIBLE);
        if (currentId <= 0){
            backBtn.setVisibility(View.INVISIBLE);
        } else if (currentId == stepList.size() -1) {
            nextBtn.setVisibility(View.INVISIBLE);
        }

    }


}
