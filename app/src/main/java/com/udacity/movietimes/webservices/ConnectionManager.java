/*
 * Copyright (C) 2013 The Android Open Source Project
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
package com.udacity.movietimes.webservices;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ramakantasamal on 8/17/15.
 *
 * This is a singleton class to provide one Volley Request queue for the networking call.
 */
public class ConnectionManager {

    private static RequestQueue mRequestQueue;


    private ConnectionManager(){}

    /**
     * This method will be used to get a single instance of Request queue for the entire application.
     * @param context
     * @return RequestQueue
     */
    public static RequestQueue getRequestQueue(Context context) {

        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(context);
        }

        return mRequestQueue;
    }

}
