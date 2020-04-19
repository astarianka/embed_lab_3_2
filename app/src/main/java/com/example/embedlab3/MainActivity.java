package com.example.embedlab3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private EditText x1_value;
    private EditText x2_value;
    private EditText y1_value;
    private EditText y2_value;
    private EditText p_value;
    private EditText lambda_value;
    private EditText time_value;
    private EditText iter_value;
    private TextView out_res;
    private Button button_calc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerOnButton();
    }

    public void addListenerOnButton() {

        x1_value = (EditText) findViewById(R.id.x1_value);
        x2_value = (EditText) findViewById(R.id.x2_value);
        y1_value = (EditText) findViewById(R.id.y1_value);
        y2_value = (EditText) findViewById(R.id.y2_value);
        p_value = (EditText) findViewById(R.id.P_value);
        lambda_value = (EditText) findViewById(R.id.lambda_value);
        time_value = (EditText) findViewById(R.id.time_value);
        iter_value = (EditText) findViewById(R.id.iter_value);
        out_res = (TextView) findViewById(R.id.out_res);
        button_calc = (Button) findViewById(R.id.button);

        button_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double [] result;
                int x1, x2, y1, y2, p, iter;
                double lambda, time;

                String x1_str = x1_value.getText().toString();
                String x2_str = x2_value.getText().toString();
                String y1_str = y1_value.getText().toString();
                String y2_str = y2_value.getText().toString();
                String p_str = p_value.getText().toString();
                String lambda_str = lambda_value.getText().toString();
                String time_str = time_value.getText().toString();
                String iter_str = iter_value.getText().toString();

                x1_value.setText("");
                x2_value.setText("");
                y1_value.setText("");
                y2_value.setText("");
                p_value.setText("");
                lambda_value.setText("");
                time_value.setText("");
                iter_value.setText("");

                try {
                    x1=Integer.parseInt(x1_str);
                    x2=Integer.parseInt(x2_str);
                    y1=Integer.parseInt(y1_str);
                    y2=Integer.parseInt(y2_str);
                    p=Integer.parseInt(p_str);
                    lambda=Double.parseDouble(lambda_str);
                    time=Double.parseDouble(time_str);
                    iter=Integer.parseInt(iter_str);
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(),"Incorrect input type!", Toast.LENGTH_LONG).show();
                    return;
                }
                result = Perceptron(x1, x2, y1, y2, p, lambda,  time, iter);
                out_res.setText("A"+"("+x1+","+y1+")"+" "+"B"+"("+x2+","+y2+")"+" "+
                        "Lambda:"+lambda+", "+"Time:"+time+", "+"Iterations:"+iter+"\n"+
                        "W1:"+result[0]+" "+"W2:"+result[1]);
            }
        });
    }
    public double[] Perceptron(int x1, int x2, int y1, int y2, int p, double lambda,  double time, int iter) {
        double[] result = new double[2];
        double W_1 = 0, W_2 = 0, delta;
        double p_1 = 0, p_2 = 0;
        int iter_done = 0;
        int msec = 1000;
        int x, y;
        String msg;


        long start_time = System.currentTimeMillis();
        long current_time = start_time;

        while((p_1 >= p || p_2 <= p) && (current_time - start_time < time*msec) && (iter_done < iter)) {
                if (p_1 >= p) {
                    delta = p - p_1;
                    x = x1;
                    y = y1;
                } else {
                    delta = p - p_2;
                    x = x2;
                    y = y2;
                }
                W_1 += delta * x * lambda;
                W_2 += delta * y * lambda;
                p_1 = x1 * W_1 + y1 * W_2;
                p_2 = x2 * W_1 + y2 * W_2;
                iter_done++;
                current_time = System.currentTimeMillis();
        }

        result[0] = W_1;
        result[1] = W_2;

        msg = (iter_done == iter)?"Max amount of iterations done!":"Time is up!";
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),"Iterations done: "+iter_done, Toast.LENGTH_LONG).show();

        return result;
    }
}
