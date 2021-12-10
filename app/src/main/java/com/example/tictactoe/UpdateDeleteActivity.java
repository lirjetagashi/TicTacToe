package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateDeleteActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_delete);

    TextView updatePlayerTxt = (TextView) findViewById(R.id.playerNameUpdateText);
    Button updatePlayerBtn = (Button) findViewById(R.id.updatePlayerBtn);
    Button deletePlayerBtn = (Button) findViewById(R.id.deletePlayerBtn);
    Button mainMenuBtn = (Button) findViewById(R.id.mainMenuBtn);

    Player player = (Player) getIntent().getSerializableExtra("player");
    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

    updatePlayerTxt.setText(player.getName());

    updatePlayerBtn.setOnClickListener(view -> {
      player.setName(updatePlayerTxt.getText().toString());
      dataBaseHelper.updatePlayer(player);
      Toast.makeText(this, "Player updated", Toast.LENGTH_SHORT).show();
      Intent i = new Intent(getApplicationContext(), ScoreboardActivity.class);
      startActivity(i);
    });

    deletePlayerBtn.setOnClickListener(view -> {
      dataBaseHelper.deleteRecord(player.getId());
      Toast.makeText(this, "Player deleted", Toast.LENGTH_SHORT).show();
      Intent i = new Intent(getApplicationContext(), ScoreboardActivity.class);
      startActivity(i);
    });

    mainMenuBtn.setOnClickListener(view -> {
      Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
      startActivity(intent);
    });

  }
}