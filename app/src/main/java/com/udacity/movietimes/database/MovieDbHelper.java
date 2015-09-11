/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.udacity.movietimes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.udacity.movietimes.database.MovieContract.*;

/**
 * This is Db Helper class which will be use to create and drop Movie Database and Table under the database
 *
 * Created by ramakant on 9/6/2015.
 */
public class MovieDbHelper extends SQLiteOpenHelper{


    public MovieDbHelper(Context context) {
        super(context, MovieContract.DATABASE_NAME, null, MovieContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MovieEntry.CREATE_TABLE);
        db.execSQL(TrailerEntry.CREATE_TABLE);
        db.execSQL(ReviewEntry.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MovieEntry.DROP_TABLE);
        db.execSQL(TrailerEntry.DROP_TABLE);
        db.execSQL(ReviewEntry.DROP_TABLE);
        onCreate(db);
    }
}
