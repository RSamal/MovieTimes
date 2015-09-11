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
