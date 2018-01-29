package com.hxl.xlmovie.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/6 0006.
 */

public class DetailBean implements Serializable {
    public RatingBean rating;
    public int reviews_count;
    public int wish_count;
    public String original_title;
    public int collect_count;
    public ImagesBean images;
    public String douban_site;
    public String year;
    public String alt;
    public String id;
    public String mobile_url;
    public int photos_count;
    public String pubdate;
    public String title;
    public Object do_count;
    public boolean has_video;
    public String share_url;
    public Object seasons_count;
    public String schedule_url;
    public String website;
    public boolean has_schedule;
    public Object collection;
    public Object episodes_count;
    public boolean has_ticket;
    public Object current_season;
    public String mainland_pubdate;
    public String summary;
    public String subtype;
    public int comments_count;
    public int ratings_count;
    public List<?> videos;
    public List<?> blooper_urls;
    public List<PopularCommentsBean> popular_comments;
    public List<String> languages;
    public List<WritersBean> writers;
    public List<String> pubdates;
    public List<String> tags;
    public List<String> durations;
    public List<String> genres;
    public List<TrailersBean> trailers;
    public List<String> trailer_urls;
    public List<?> bloopers;
    public List<?> clip_urls;
    public List<CastsBean> casts;
    public List<String> countries;
    public List<PhotosBean> photos;
    public List<?> clips;
    public List<DirectorsBean> directors;
    public List<PopularReviewsBean> popular_reviews;
    public List<String> aka;


    public static class RatingBean implements Serializable{
        /**
         * max : 10
         * average : 2.7
         * details : {"1":27,"3":0,"2":3,"5":2,"4":0}
         * stars : 15
         * min : 0
         */

        public int max;
        public double average;
        public DetailsBean details;
        public String stars;
        public int min;

        public static class DetailsBean implements Serializable {
            /**
             * 1 : 735
             * 3 : 3826
             * 2 : 1997
             * 5 : 560
             * 4 : 1612
             */

            @SerializedName("1")
            public int _$1;
            @SerializedName("3")
            public int _$3;
            @SerializedName("2")
            public int _$2;
            @SerializedName("5")
            public int _$5;
            @SerializedName("4")
            public int _$4;


        }

    }

    public static class ImagesBean implements Serializable{
        /**
         * small : http://img7.doubanio.com/view/movie_poster_cover/ipst/public/p2406903891.jpg
         * large : http://img7.doubanio.com/view/movie_poster_cover/lpst/public/p2406903891.jpg
         * medium : http://img7.doubanio.com/view/movie_poster_cover/spst/public/p2406903891.jpg
         */

        public String small;
        public String large;
        public String medium;


    }

    public static class PopularCommentsBean implements Serializable {
        /**
         * rating : {"max":5,"value":1,"min":0}
         * useful_count : 0
         * author : {"uid":"maggie841212","avatar":"http://img7.doubanio.com/icon/u33450230-272.jpg","signature":"一名拉玛西亚青年近卫军","alt":"http://www.douban.com/people/maggie841212/","id":"33450230","name":"笑子大魔王"}
         * subject_id : 26865690
         * content : 很不错的片子，评分还可以更低一些，并且有很大的空间
         * created_at : 2017-02-03 23:06:17
         * id : 1146632816
         */

        public RatingBeanX rating;
        public int useful_count;
        public AuthorBean author;
        public String subject_id;
        public String content;
        public String created_at;
        public String id;
    }

    public static class RatingBeanX implements Serializable{
        /**
         * max : 5
         * value : 1
         * min : 0
         */

        public int max;
        public int value;
        public int min;

    }

    public static class AuthorBean implements Serializable{
        /**
         * uid : maggie841212
         * avatar : http://img7.doubanio.com/icon/u33450230-272.jpg
         * signature : 一名拉玛西亚青年近卫军
         * alt : http://www.douban.com/people/maggie841212/
         * id : 33450230
         * name : 笑子大魔王
         */

        public String uid;
        public String avatar;
        public String signature;
        public String alt;
        public String id;
        public String name;


    }

    public static class WritersBean implements Serializable {
        /**
         * avatars : {"small":"http://img3.doubanio.com/f/movie/ca527386eb8c4e325611e22dfcb04cc116d6b423/pics/movie/celebrity-default-small.png","large":"http://img7.doubanio.com/f/movie/63acc16ca6309ef191f0378faf793d1096a3e606/pics/movie/celebrity-default-large.png","medium":"http://img3.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png"}
         * name_en :
         * name : 纪然
         * alt : https://movie.douban.com/celebrity/1366595/
         * id : 1366595
         */

        public AvatarsBean avatars;
        public String name_en;
        public String name;
        public String alt;
        public String id;


        public static class AvatarsBean implements Serializable{
            /**
             * small : http://img3.doubanio.com/f/movie/ca527386eb8c4e325611e22dfcb04cc116d6b423/pics/movie/celebrity-default-small.png
             * large : http://img7.doubanio.com/f/movie/63acc16ca6309ef191f0378faf793d1096a3e606/pics/movie/celebrity-default-large.png
             * medium : http://img3.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png
             */

            public String small;
            public String large;
            public String medium;


        }
    }

    public static class TrailersBean implements Serializable {
        /**
         * medium : http://img3.doubanio.com/img/trailer/medium/2395934439.jpg?
         * title : 预告片：正式版 (中文字幕)
         * subject_id : 26865690
         * alt : https://movie.douban.com/trailer/206905/
         * small : http://img3.doubanio.com/img/trailer/small/2395934439.jpg?
         * resource_url : http://vt1.doubanio.com/201702180820/994ce6a83c4d28a0eb8b93f8f71fe45a/view/movie/M/302060905.mp4
         * id : 206905
         */

        public String medium;
        public String title;
        public String subject_id;
        public String alt;
        public String small;
        public String resource_url;
        public String id;

    }

    public static class CastsBean implements Serializable {
        /**
         * avatars : {"small":"http://img3.doubanio.com/img/celebrity/small/1403756298.69.jpg","large":"http://img3.doubanio.com/img/celebrity/large/1403756298.69.jpg","medium":"http://img3.doubanio.com/img/celebrity/medium/1403756298.69.jpg"}
         * name_en : Guoer Yin
         * name : 殷果儿
         * alt : https://movie.douban.com/celebrity/1340984/
         * id : 1340984
         */

        public AvatarsBeanX avatars;
        public String name_en;
        public String name;
        public String alt;
        public String id;

    }

    public static class AvatarsBeanX implements Serializable{
        /**
         * small : http://img3.doubanio.com/img/celebrity/small/1403756298.69.jpg
         * large : http://img3.doubanio.com/img/celebrity/large/1403756298.69.jpg
         * medium : http://img3.doubanio.com/img/celebrity/medium/1403756298.69.jpg
         */

        public String small;
        public String large;
        public String medium;


    }

    public static class PhotosBean implements Serializable {
        /**
         * thumb : http://img7.doubanio.com/view/photo/thumb/public/p2406383762.jpg
         * image : http://img7.doubanio.com/view/photo/photo/public/p2406383762.jpg
         * cover : http://img7.doubanio.com/view/photo/albumcover/public/p2406383762.jpg
         * alt : https://movie.douban.com/photos/photo/2406383762/
         * id : 2406383762
         * icon : http://img7.doubanio.com/view/photo/icon/public/p2406383762.jpg
         */

        public String thumb;
        public String image;
        public String cover;
        public String alt;
        public String id;
        public String icon;


    }

    public static class DirectorsBean implements Serializable {
        /**
         * avatars : {"small":"http://img3.doubanio.com/f/movie/ca527386eb8c4e325611e22dfcb04cc116d6b423/pics/movie/celebrity-default-small.png","large":"http://img7.doubanio.com/f/movie/63acc16ca6309ef191f0378faf793d1096a3e606/pics/movie/celebrity-default-large.png","medium":"http://img3.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png"}
         * name_en : Shilei Lu
         * name : 陆诗雷
         * alt : https://movie.douban.com/celebrity/1360707/
         * id : 1360707
         */

        public AvatarsBeanXX avatars;
        public String name_en;
        public String name;
        public String alt;
        public String id;


        public static class AvatarsBeanXX implements Serializable{
            /**
             * small : http://img3.doubanio.com/f/movie/ca527386eb8c4e325611e22dfcb04cc116d6b423/pics/movie/celebrity-default-small.png
             * large : http://img7.doubanio.com/f/movie/63acc16ca6309ef191f0378faf793d1096a3e606/pics/movie/celebrity-default-large.png
             * medium : http://img3.doubanio.com/f/movie/8dd0c794499fe925ae2ae89ee30cd225750457b4/pics/movie/celebrity-default-medium.png
             */

            public String small;
            public String large;
            public String medium;

        }
    }

    public static class PopularReviewsBean implements Serializable {
        /**
         * rating : {"max":5,"value":1,"min":0}
         * title : 国产恐怖片，注定成烂片？
         * subject_id : 26865690
         * author : {"uid":"123404248","avatar":"http://img7.doubanio.com/icon/u123404248-3.jpg","signature":"","alt":"http://www.douban.com/people/123404248/","id":"123404248","name":"世界奇妙物语"}
         * summary : 这一系列国产恐怖片太多，现在总结下国产电影拍摄门槛为什么这么低…… 1.找个导演，内地导演优先考虑(省钱)。 2.去网上热搜榜（也是经纪公司）上挑几个网红明星（省钱）。网红明星就像木偶一样被装扮上了。 3.去...
         * alt : https://movie.douban.com/review/8301338/
         * id : 8301338
         */

        public RatingBeanXX rating;
        public String title;
        public String subject_id;
        public AuthorBeanX author;
        public String summary;
        public String alt;
        public String id;


        public static class RatingBeanXX implements Serializable{
            /**
             * max : 5
             * value : 1
             * min : 0
             */

            public int max;
            public int value;
            public int min;


        }

        public static class AuthorBeanX implements Serializable{
            /**
             * uid : 123404248
             * avatar : http://img7.doubanio.com/icon/u123404248-3.jpg
             * signature :
             * alt : http://www.douban.com/people/123404248/
             * id : 123404248
             * name : 世界奇妙物语
             */

            public String uid;
            public String avatar;
            public String signature;
            public String alt;
            public String id;
            public String name;


        }
    }
}
