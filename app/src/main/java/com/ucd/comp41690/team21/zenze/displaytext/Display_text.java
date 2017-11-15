package com.example.thom.print_text;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class Display_text extends AppCompatActivity {

    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_text);

        textView = (TextView)findViewById(R.id.textView2);
        textView.setTextSize(40);
        textView.setText("Vous allez en apprendre des choses !");

        final TextView textOne = (TextView) findViewById(R.id.textView);
        Button pushMe = (Button) findViewById(R.id.button);

        final String[] myStrings = {"pharyngite désigne une inflammation du pharynx. Le pharynx est situé à l’arrière de la bouche et a la forme d’un entonnoir. Il est impliqué dans la déglutition (passage des aliments de la bouche vers l’œsophage), la respiration (passage de l’air de la bouche vers le larynx), et la phonation (influence sur les sons produits par les cordes vocales). ","La maladie de Parkinson est une maladie neuro-dégénérative atteignant généralement l'homme après 50 ans. Elle se manifeste par un tremblement de repos, des troubles du tonus et une akinésie, diversement associés. Elle affecte également l'élocution et le \"langage corporel\". Comme beaucoup d'autres troubles neurologiques, la maladie de Parkinson est chronique, évolutive et pour le moment incurable. Elle est d'étiologie inconnue. ","blablab3","blablblblba 4"};


        pushMe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int rando = (int)  (Math.random() *4);
                textOne.setText(myStrings[rando]);
                textOne.startAnimation(AnimationUtils.loadAnimation(Display_text.this, android.R.anim.slide_in_left ));
            }
        });

    }
}
