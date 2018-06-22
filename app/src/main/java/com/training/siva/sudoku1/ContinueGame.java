package com.training.siva.sudoku1;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ContinueGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        getInitialValues();
    }

    public int a[][] = new int[9][9];
    public int b[][] = new int[9][9];
    public int c[][] = new int[9][9];

    public void getInitialValues() {
        try {
            FileInputStream fin = openFileInput("GameData.txt");
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
                    a[i][j] = fin.read();
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
                    b[i][j] = fin.read();
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
                    c[i][j] = fin.read();
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (b[i][j] != 0) {
                    setInitialValue(b[i][j], i + 1, j + 1);
                } else if (c[i][j] != 0)
                    setSavedValue(c[i][j], i + 1, j + 1);
            }
        }
    }

    public void setInitialValue(int num, int i, int j) {
        TableLayout obj2 = (TableLayout) findViewById(R.id.tableLayout);
        EditText obj1 = obj2.findViewWithTag(Integer.toString(10 * i + j));
        obj1.setText(String.valueOf(num));
        obj1.setTypeface(null, Typeface.BOLD);
        obj1.setFocusable(false);
    }

    public void setSavedValue(int num, int i, int j) {
        TableLayout obj2 = (TableLayout) findViewById(R.id.tableLayout);
        EditText obj1 = obj2.findViewWithTag(Integer.toString(10 * i + j));
        obj1.setText(String.valueOf(num));
    }

    public void checkAnswer(View view) {
        TableLayout obj2 = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < 9; y++) {
            for (int z = 0; z < 9; z++) {
                EditText obj1 = obj2.findViewWithTag(Integer.toString(10 * y + z + 11));
                String str = obj1.getText().toString();
                if (str.matches(""))
                    c[y][z] = 0;
                else
                    c[y][z] = Integer.parseInt(str);
            }
        }

        int q = 0, p = 0;
        for (int y = 0; y < 9; y++) {
            for (int z = 0; z < 9; z++) {
                if (a[y][z] == c[y][z])
                    continue;
                else if (c[y][z] == 0) {
                    p++;
                    break;
                } else {
                    q++;
                    break;
                }
            }
        }
        if (p == 0 & q == 0)
            Toast.makeText(this, "Congratulations! Sudoku Solved Successfully.", Toast.LENGTH_SHORT).show();
        else if (p == 0)
            Toast.makeText(this, "Oops! The solution is incorrect.", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Oops! The Sudoku is Incomplete.", Toast.LENGTH_SHORT).show();
    }

    public void saveGame(View view) {
        TableLayout obj2 = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < 9; y++) {
            for (int z = 0; z < 9; z++) {
                EditText obj1 = obj2.findViewWithTag(Integer.toString(10 * y + z + 11));
                String str = obj1.getText().toString();
                if (str.matches(""))
                    c[y][z] = 0;
                else
                    c[y][z] = Integer.parseInt(str);
            }
        }

        try {
            FileOutputStream fOut = openFileOutput("GameData.txt", Context.MODE_PRIVATE);
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
                    fOut.write(a[i][j]);
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
                    fOut.write(b[i][j]);
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
                    fOut.write(c[i][j]);
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Game Progress Saved Successfully", Toast.LENGTH_SHORT).show();
    }

    public void solutionDisplay(View view) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (b[i][j] != 0) {
                    setInitialValue(b[i][j], i + 1, j + 1);
                } else if (a[i][j] != 0)
                    setSolutionValue(a[i][j], i + 1, j + 1);
            }
        }
    }

    public void setSolutionValue(int num, int i, int j) {
        TableLayout obj2 = (TableLayout) findViewById(R.id.tableLayout);
        EditText obj1 = obj2.findViewWithTag(Integer.toString(10 * i + j));
        obj1.setText(String.valueOf(num));
        obj1.setTypeface(null, Typeface.NORMAL);
    }
}