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
package com.udacity.movietimes.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ramakantasamal on 8/17/15.
 *
 * This class will contain the objects for list of movies and will be used to parse JSON data using GSON library.
 */
public class Movies {

    @SerializedName("results")
    private List<Movie> movieList;

    public List<Movie> getMovieList() {
        return movieList;
    }
}
