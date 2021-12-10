package com.example.tictactoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

  public static final String PLAYER_TABLE = "PLAYER_TABLE";
  public static final String COLUMN_ID = "ID";
  public static final String COLUMN_NAME = "NAME";
  public static final String COLUMN_WINS = "WINS";
  public static final String COLUMN_LOSSES = "LOSSES";
  public static final String COLUMN_TIES = "TIES";

  public DataBaseHelper(@Nullable Context context) {
    super(context, "tictactoe.db", null, 1);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String createPlayerTable = "CREATE TABLE " + PLAYER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT," + COLUMN_WINS + " INTEGER," + COLUMN_LOSSES + " INTEGER," + COLUMN_TIES + " INTEGER)";

    db.execSQL(createPlayerTable);

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    db.execSQL("DROP TABLE IF EXISTS " + PLAYER_TABLE);
    onCreate(db);
  }

  public void addRecord(Player player) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues cv = new ContentValues();

    cv.put(COLUMN_NAME, player.getName());
    cv.put(COLUMN_WINS, player.getWins());
    cv.put(COLUMN_LOSSES, player.getLosses());
    cv.put(COLUMN_TIES, player.getTies());

    db.insert(PLAYER_TABLE, null, cv);

    db.close();
  }

  public List<Player> getAllPlayers(boolean sorted) {
    String queryString = "SELECT * FROM " + PLAYER_TABLE + (sorted ? " ORDER BY " + COLUMN_WINS +" DESC" : "");
    List<Player> viewList = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(queryString, null);

    if (cursor.moveToFirst()) {
      do {
        Player player = Player.builder()
            .id(cursor.getInt(0))
            .name(cursor.getString(1))
            .wins(cursor.getInt(2))
            .losses(cursor.getInt(3))
            .ties(cursor.getInt(4))
            .build();

        viewList.add(player);

      } while (cursor.moveToNext());
    }

    return viewList;
  }

//  public List<String> getAllPlayerNames() {
//
//    List<String> playerNames = new ArrayList<>();
//    String queryString = "SELECT " + COLUMN_NAME + " FROM " + PLAYER_TABLE;
//    SQLiteDatabase db = this.getReadableDatabase();
//    Cursor cursor = db.rawQuery(queryString, null);
//
//    if (cursor.moveToFirst()) {
//      do {
//        playerNames.add(cursor.getString(0));
//      } while (cursor.moveToNext());
//    }
//
//    return playerNames;
//  }

  public void updatePlayer(Player player) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();

    values.put(COLUMN_NAME, player.getName());
    values.put(COLUMN_WINS, player.getWins());
    values.put(COLUMN_LOSSES, player.getLosses());
    values.put(COLUMN_TIES, player.getTies());

    String where = COLUMN_ID + "= ?";
    String[] whereArgs = {String.valueOf(player.getId())};

    db.update(PLAYER_TABLE, values, where, whereArgs);
    db.close();
  }

  public void deleteRecord(int id) {
    SQLiteDatabase db = this.getWritableDatabase();

    db.delete(PLAYER_TABLE, "ID=?", new String[]{String.valueOf(id)});
    db.close();
  }
}
