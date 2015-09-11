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
package com.udacity.movietimes.utils;

/**
 * Created by ramakanta samal on 8/24/15.
 *
 * This class contain all the URL parameter for the MovieDb Api which will be use to fetch the data.
 */
public abstract class MovieUrl {

    /**
     * Below Parameter is to fetch the movie details
     */
//    public static final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
    public static final String BASE_URL = "http://api.themoviedb.org/3";
    public static final String SORT_BY_PARM = "sort_by";
    public static final String API_KEY_PARM = "api_key";
    public static final String CERT_COUNTRY_PARM = "certification_country";
    public static final String CERT_PARM = "certification";


    /** Below is for to fetch the Image of a movie */
    public static final String MOVIE_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";


    /** To Fetch the Vedio trailer of Movie */
    public static final String MOVIE_VEDIO_ID_URL = "http://api.themoviedb.org/3/movie/";
    public static final String MOVIE_VEDIO_BASE_URL = "http://img.youtube.com/vi/";
    public static final String VIDEOS = "videos";
    public static final String VEDIO_TN_SIZE = "0.jpg";


}
