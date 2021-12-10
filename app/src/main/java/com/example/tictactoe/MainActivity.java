package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences.Editor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private final Button[] squares = new Button[9];
  private int round;
  boolean activePlayer;
  Player player1 = SelectPlayerActivity.selectedPlayer1;
  Player player2 = SelectPlayerActivity.selectedPlayer2;

  private SharedPreferences savedValues;

  /*
   player1 will be set as 1
   player2 will be set as 2
   empty will be set as 0
   */
  int[] gameState = {0, 0, 0, 0, 0, 0, 0, 0, 0};

  int[][] winningPositions = {
      {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
      {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
      {0, 4, 8}, {2, 4, 6}           // cross
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    TextView playerOneTextView = findViewById(R.id.player1TextView);
    TextView playerTwoTextView = findViewById(R.id.player2TextView);
    Button viewScoreboard = findViewById(R.id.viewScoreboardBtn);

    for (int i = 0; i < squares.length; i++) {
      String buttonID = "button" + i;
      int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
      squares[i] = findViewById(resourceID);
      squares[i].setOnClickListener(this);
    }

    round = 0;
    activePlayer = true;

    playerOneTextView.setText(player1.getName());
    playerTwoTextView.setText(player2.getName());

    viewScoreboard.setOnClickListener(view -> {
      Intent intent = new Intent(getApplicationContext(), ScoreboardActivity.class);
      startActivity(intent);
    });
  }

  @Override
  public void onClick(View view) {
    if (!((Button) view).getText().toString().equals("")) {
      return;
    }
    String buttonID = view.getResources().getResourceEntryName(view.getId()); // button0
    int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1)); //0

    if (activePlayer) {
      ((Button) view).setText("X");
      gameState[gameStatePointer] = 1;
    } else {
      ((Button) view).setText("O");
      gameState[gameStatePointer] = 2;
    }
    round++;
    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

    if (checkWinner()) {
      if (activePlayer) {
        player1.setWins(player1.getWins() + 1);
        player2.setLosses(player2.getLosses() + 1);
        dataBaseHelper.updatePlayer(player1);
        dataBaseHelper.updatePlayer(player2);
        Toast.makeText(this, player1.getName() + " wins!", Toast.LENGTH_SHORT).show();
      } else {
        player2.setWins(player2.getWins() + 1);
        player1.setLosses(player1.getLosses() + 1);
        dataBaseHelper.updatePlayer(player1);
        dataBaseHelper.updatePlayer(player2);
        Toast.makeText(this, player2.getName() + " wins!", Toast.LENGTH_SHORT).show();
      }
      newGame();
    } else if (round == 9) {
      newGame();
      player1.setTies(player1.getTies() + 1);
      player2.setTies(player2.getTies() + 1);
      dataBaseHelper.updatePlayer(player1);
      dataBaseHelper.updatePlayer(player2);
      Toast.makeText(this, "Game Draw !!!!", Toast.LENGTH_SHORT).show();
    } else {
      activePlayer = !activePlayer;
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    // save the instance variables
    Editor editor = savedValues.edit();
    editor.putBoolean("activePlayer", activePlayer);
    editor.putInt("round", round);

    for (int i = 0; i < 9; i++) {
      editor.putString("button" + i, squares[i].getText().toString());
      editor.putInt("gameState" + i, gameState[i]);
    }
    editor.apply();
  }

  @Override
  public void onResume() {
    super.onResume();
    // get the instance variables
    activePlayer = savedValues.getBoolean("activePlayer", true);
    round = savedValues.getInt("round", 0);

    for (int i = 0; i < 9; i++) {
      squares[i].setText(savedValues.getString("button" + i, ""));
      gameState[i] = savedValues.getInt("gameState" + i, 0);
    }
  }

  public boolean checkWinner() {
    for (int[] winningPosition : winningPositions) {
      if (gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
          gameState[winningPosition[2]] == gameState[winningPosition[0]] &&
          gameState[winningPosition[1]] != 0) {
        return true;
      }
    }

    return false;
  }

  public void newGame() {
    round = 0;
    activePlayer = true;

    for (int i = 0; i < squares.length; i++) {
      gameState[i] = 0;
      squares[i].setText("");
    }
  }

}