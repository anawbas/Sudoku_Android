package com.training.siva.sudoku1;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class SudokuSolver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_solver);
        Toast.makeText(this, "Enter the known values and wait for the app to solve the remaining for you :)", Toast.LENGTH_LONG).show();
    }

    public int a[][] = new int[9][9];
    public int b[][] = new int[9][9];

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
        obj1.setTypeface(null, Typeface.NORMAL);
    }

    public void solve(View view) {
        TableLayout obj2 = (TableLayout) findViewById(R.id.tableLayout);
        for (int y = 0; y < 9; y++) {
            for (int z = 0; z < 9; z++) {
                EditText obj1 = obj2.findViewWithTag(Integer.toString(10 * y + z + 11));
                String str = obj1.getText().toString();
                if (str.matches(""))
                    b[y][z] = 0;
                else
                    b[y][z] = Integer.parseInt(str);
            }
        }

        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                a[i][j] = b[i][j];

        if (solve(0, 0, a)) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (b[i][j] != 0) {
                        setInitialValue(b[i][j], i + 1, j + 1);
                    } else if (a[i][j] != 0)
                        setSavedValue(a[i][j], i + 1, j + 1);
                }
            }
            Toast.makeText(this, "Sudoku Solved :)", Toast.LENGTH_LONG).show();

        } else
            Toast.makeText(this, "The given Sudoku doesn't have any solution :|", Toast.LENGTH_SHORT).show();
    }

    static boolean solve(int i, int j, int[][] cells) {
        if (i == 9) {
            i = 0;
            if (++j == 9)
                return true;
        }
        if (cells[i][j] != 0)
            return solve(i + 1, j, cells);

        for (int val = 1; val <= 9; ++val) {
            if (legal(i, j, val, cells)) {
                cells[i][j] = val;
                if (solve(i + 1, j, cells))
                    return true;
            }
        }
        cells[i][j] = 0;
        return false;
    }

    static boolean legal(int i, int j, int val, int[][] cells) {
        for (int k = 0; k < 9; ++k)
            if (val == cells[k][j])
                return false;

        for (int k = 0; k < 9; ++k)
            if (val == cells[i][k])
                return false;

        int boxRowOffset = (i / 3) * 3;
        int boxColOffset = (j / 3) * 3;
        for (int k = 0; k < 3; ++k)
            for (int m = 0; m < 3; ++m)
                if (val == cells[boxRowOffset + k][boxColOffset + m])
                    return false;

        return true;
    }
}