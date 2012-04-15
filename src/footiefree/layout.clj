
(ns footiefree.layout
  (:use [hiccup.core :only [html]]
        [hiccup.form-helpers :only [form-to submit-button label text-field]]
        [hiccup.page-helpers :only [include-css]]
        [clojure.string :only [split]]))

(def footy-words #{
  "goal"
  "ref"
})

(defn- imageurl
  [nick]
  (format "http://api.twitter.com/1/users/profile_image/%s.format" nick))

(defn- is-footy-related
  [tweet]
  (let [text (.toLowerCase (:text tweet))]
    (not (some footy-words 
               (split text #"\s+")))))

(defn- content
  [& body]
  (html
    [:head
      [:title "Footie Free Twitter"]
      (include-css "/css/default.css")]
    [:body
      [:div.container
        [:h1.whitebox "Footie Free Twitter"]
        body
        [:div.footer "by rod"]]]))

(defn- one-tweet
  [tweet]
  (let [nick (:screen_name (:user tweet))]
    [:div.tweet.whitebox
      [:div.pic
        [:img {:src (imageurl nick)}]]
      [:div.content
        [:div.author nick]
        [:div.desc (:text tweet)]]
      [:div.clearer]]))

;; Public

(defn tweets-page [nick tweets]
  (content
    [:div.user.whitebox 
      [:img {:src (imageurl nick)}]
      [:h2 nick]
      [:div.clearer]]
    [:ul
      (for [tweet (filter is-footy-related tweets)]
        [:li (one-tweet tweet)])]))

(defn index-page []
  (content
    [:div.intro
      [:div.message "Welcome to a Football free Twitter, just enter your nickname!"]
      [:div.whitebox
        (form-to [:get "/"]
          (label "nick" "Username")
          (text-field {} "nick")
          (submit-button {} "Show me!"))]]))

