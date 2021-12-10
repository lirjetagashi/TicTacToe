package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPlayerActivity extends AppCompatActivity {

  private EditText playerName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_player);

    playerName = (EditText) findViewById(R.id.playerNameEditText);
    Button saveBtn = (Button) findViewById(R.id.saveBtn);

    saveBtn.setOnClickListener(view -> {
      DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

      try {
        Player player = Player.builder()
            .name(playerName.getText().toString())
            .wins(0)
            .losses(0)
            .ties(0)
            .build();

        dataBaseHelper.addRecord(player);

        Toast.makeText(getApplicationContext(), "Player " + player.getName() + " is added!", Toast.LENGTH_SHORT).show();

        playerName.setText("");
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
      } catch (Exception e) {
        Toast.makeText(getApplicationContext(), "Error Adding Player!", Toast.LENGTH_SHORT).show();
      }
    });
  }

}