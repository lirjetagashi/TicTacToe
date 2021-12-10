package com.example.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SelectPlayerActivity extends AppCompatActivity {

  public static Player selectedPlayer1;
  public static Player selectedPlayer2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select_player);

    TextView selectPlayer = findViewById(R.id.selectPlayerTxt);
    ListView playerListView = findViewById(R.id.playerListView);
    playerListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

    String playerLabel = getString(R.string.select_player_label, getIntent().getStringExtra("playerNumber"));
    selectPlayer.setText(playerLabel);

    DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
    List<Player> allPlayers = dataBaseHelper.getAllPlayers(false);
    List<String> allPlayerNames = new ArrayList<>();
    for (Player player : allPlayers) {
      allPlayerNames.add(player.getName());
    }
    ArrayAdapter<String> adapter = new ListItem(
        this,
        R.layout.support_simple_spinner_dropdown_item,
        allPlayerNames,
        getDisabledPlayers(allPlayers)
    );
    playerListView.setAdapter(adapter);
    playerListView.setOnItemClickListener(onListItemClick(allPlayers));
  }

  private List<Integer> getDisabledPlayers(List<Player> players) {
    ArrayList<Integer> indexes = new ArrayList<>();

    if (selectedPlayer1 != null) {
      indexes.add(players.indexOf(selectedPlayer1));
    }

    if (selectedPlayer2 != null) {
      indexes.add(players.indexOf(selectedPlayer2));
    }

    return indexes;
  }

  private AbsListView.OnItemClickListener onListItemClick(List<Player> players) {
    return (adapterView, view, index, l) -> {
      if (getIntent().getStringExtra("playerNumber").equals("1")) {
        selectedPlayer1 = players.get(index);
      } else {
        selectedPlayer2 = players.get(index);
      }

      Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
      startActivity(intent);
    };
  }

  class ListItem extends ArrayAdapter<String> {

    private List<Integer> disabledIndexes;

    public ListItem(@NonNull Context context, int resource, @NonNull List<String> objects, List<Integer> disabledIndexes) {
      super(context, resource, objects);
      this.disabledIndexes = disabledIndexes;
    }

    @Override
    public boolean isEnabled(int position) {
      return !disabledIndexes.contains(position);
    }
  }
}