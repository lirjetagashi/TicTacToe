package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu);

    Button addPlayer = (Button) findViewById(R.id.addPlayerBtn);
    Button selectPlayer1 = (Button) findViewById(R.id.selectPlayer1btn);
    Button selectPlayer2 = (Button) findViewById(R.id.selectPlayer2btn);
    Button startGame = (Button) findViewById(R.id.startGameBtn);
    Button viewScoreboard = (Button) findViewById(R.id.viewScoreboardBtn);

    addPlayer.setOnClickListener(view -> {
      Intent intent = new Intent(getApplicationContext(), AddPlayerActivity.class);
      startActivity(intent);
    });

    viewScoreboard.setOnClickListener(view -> {
      Intent intent = new Intent(getApplicationContext(), ScoreboardActivity.class);
      startActivity(intent);
    });

    selectPlayer1.setOnClickListener(view -> handleSelectPlayerView("1"));
    selectPlayer2.setOnClickListener(view -> handleSelectPlayerView("2"));

    startGame.setOnClickListener(view -> {
      if (SelectPlayerActivity.selectedPlayer1 == null) {
        handleSelectPlayerView("1");
        Toast.makeText(this, "Please choose player 1", Toast.LENGTH_SHORT).show();
        return;
      }
      if (SelectPlayerActivity.selectedPlayer2 == null) {
        handleSelectPlayerView("2");
        Toast.makeText(this, "Please choose player 2", Toast.LENGTH_SHORT).show();
        return;
      }

      Intent intent = new Intent(getApplicationContext(), MainActivity.class);
      startActivity(intent);
    });
  }

  private void handleSelectPlayerView(String playerNumber) {
    Intent intent = new Intent(getApplicationContext(), SelectPlayerActivity.class);
    intent.putExtra("playerNumber", playerNumber);
    startActivity(intent);
  }
}