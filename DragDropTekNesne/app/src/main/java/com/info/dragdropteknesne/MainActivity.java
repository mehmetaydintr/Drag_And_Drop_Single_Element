package com.info.dragdropteknesne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private RelativeLayout relativeLayout;

    private static final String BUTTON_ETIKET = "DRAG BUTTON";

    private RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        relativeLayout = findViewById(R.id.rl);

        button.setTag(BUTTON_ETIKET);

        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());

                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData clipData = new ClipData(view.getTag().toString(), mimeTypes, item);

                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

                view.startDrag(clipData, shadowBuilder,view, 0);

                view.setVisibility(View.INVISIBLE);

                return true;
            }
        });

        relativeLayout.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {

                switch (dragEvent.getAction()){
                    case DragEvent.ACTION_DRAG_STARTED:
                        layoutParams = (RelativeLayout.LayoutParams) button.getLayoutParams();
                        Log.e("sonuç", "ACTION_DRAG_STARTED");
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.e("sonuç", "ACTION_DRAG_ENTERED");
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.e("sonuç", "ACTION_DRAG_EXITED");
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.e("sonuç", "ACTION_DRAG_LOCATION");
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.e("sonuç", "ACTION_DRAG_ENDED");
                        break;
                    case DragEvent.ACTION_DROP:
                        layoutParams.leftMargin = (int) dragEvent.getX();
                        layoutParams.topMargin = (int) dragEvent.getY();

                        View gorselNesne = (View) dragEvent.getLocalState();
                        ViewGroup eskiTasarimAlani = (ViewGroup) gorselNesne.getParent();
                        eskiTasarimAlani.removeView(gorselNesne);

                        RelativeLayout hedefTasarimAlani = (RelativeLayout) view;
                        hedefTasarimAlani.addView(gorselNesne, layoutParams);

                        gorselNesne.setVisibility(View.VISIBLE);

                        Log.e("sonuç", "ACTION_DROP");
                        break;
                    default:
                        break;
                }

                return true;
            }
        });

    }
}