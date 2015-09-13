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
 * This is Review POJO class which is represents the reviews of a Movie from MovieDb Api
 * Created by ramakant on 9/10/2015.
 */
public class Reviews {

    @SerializedName("results")
    private List<Review> reviewList;

    public List<Review> getReviewList() {
        return reviewList;
    }

    public static class Review {
        @SerializedName("id")
        private String id;

        @SerializedName("author")
        private String author;

        @SerializedName("content")
        private String content;

        public String getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }
    }
}
