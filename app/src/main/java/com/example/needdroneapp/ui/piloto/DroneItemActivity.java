package com.example.needdroneapp.ui.piloto;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

public class DroneItemActivity extends SQLiteOpenHelper {

    public DroneItemActivity(Context context) {
        super(context, "drone_database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table...
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade table...
    }

    public List<Drone> getAllDrones() {
        List<Drone> droneList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM drones", null);

        if (cursor.moveToFirst()) {
            do {
                Drone drone = new Drone();
                drone.setNome(cursor.getString(1));
                drone.setTipoDrone(cursor.getString(2));
                drone.setImgQualidade(cursor.getString(3));
                drone.setAutonomia(Integer.parseInt(cursor.getString(4)));
                drone.setAreaCobertura(Integer.parseInt(cursor.getString(5)));
                drone.setStatus(cursor.getString(6));
                drone.setImgSobreposicao(Integer.parseInt(cursor.getString(7)));
                drone.setFoto(cursor.getString(8));
                drone.setPilotoId(cursor.getString(9));

                // Set other drone properties...

                droneList.add(drone);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return droneList;
    }
}