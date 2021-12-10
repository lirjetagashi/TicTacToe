package com.example.tictactoe;

import static android.view.ViewGroup.LayoutParams;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ScoreboardActivity extends AppCompatActivity {

  private TableLayout tableLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scoreboard);
    tableLayout = (TableLayout) findViewById(R.id.scoreboardTable);
    Button menuBtn = (Button) findViewById(R.id.menuBtn);


    menuBtn.setOnClickListener(view -> {
      Intent i = new Intent(getApplicationContext(), MenuActivity.class);
      startActivity(i);
    });
    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
    List<Player> allPlayers = dataBaseHelper.getAllPlayers(true);
    fillData(allPlayers);
  }

  private void fillData(List<Player> players) {

    for (Player player : players) {
      TableRow tableRow = new TableRow(this);
      tableRow.setLayoutParams(new TableRow.LayoutParams(
          TableRow.LayoutParams.MATCH_PARENT,
          TableRow.LayoutParams.WRAP_CONTENT));

      tableRow.setOnClickListener(view -> {
        Intent intent = new Intent(ScoreboardActivity.this, UpdateDeleteActivity.class);
        intent.putExtra("player", player);
        startActivity(intent);
      });

      TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
      params.setMargins(50,0,0,0);

      // Player Column
      TextView playerTextView = new TextView(this);
      playerTextView.setLayoutParams(params);
      playerTextView.setText(player.getName());
      playerTextView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
      playerTextView.setPadding(5, 5, 5, 0);
      tableRow.addView(playerTextView);

      // Wins Column
      TextView winsTextView = new TextView(this);
      winsTextView.setLayoutParams(params);
      winsTextView.setText(String.valueOf(player.getWins()));
      winsTextView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
      winsTextView.setPadding(20, 5, 5, 0);
      tableRow.addView(winsTextView);

      // Losses Column
      TextView lossesTextView = new TextView(this);
      lossesTextView.setLayoutParams(params);
      lossesTextView.setText(String.valueOf(player.getLosses()));
      lossesTextView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
      lossesTextView.setPadding(20, 5, 5, 0);
      tableRow.addView(lossesTextView);

      // Ties Column
      TextView tiesTextView = new TextView(this);
      tiesTextView.setLayoutParams(params);
      tiesTextView.setText(String.valueOf(player.getTies()));
      tiesTextView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
      tiesTextView.setPadding(20, 5, 5, 0);
      tableRow.addView(tiesTextView);

      tableLayout.addView(tableRow, new TableLayout.LayoutParams(
          TableRow.LayoutParams.MATCH_PARENT,
          TableRow.LayoutParams.WRAP_CONTENT));
    }
  }
}