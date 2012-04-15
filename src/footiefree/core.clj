
(ns footiefree.core
  (:use [compojure.core]
        [ring.adapter.jetty :as ring]
        [cheshire.core]
        [clojure.string :only [split]])
  (:require [footiefree.layout :as layout]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(def apiurl "https://api.twitter.com/1/statuses/user_timeline.json?include_entities=true&include_rts=true&screen_name=%s&count=10")

(def footy-words #{
  "goal"
  "ref"
})

(defn- is-footy-related
  [tweet]
  (let [text (.toLowerCase (:text tweet))]
    (not (some footy-words 
               (split text #"\s+")))))

(defn- get-tweets
  [nick]
  (filter is-footy-related
    (parse-stream 
      (clojure.java.io/reader 
        (format apiurl nick)) true)))

(defn- index-page
  [req]
  (if-let [nick (:nick (:params req))]
     (layout/tweets-page nick (get-tweets nick))
     (layout/index-page)))

;; Server routes

(defroutes app-routes
  (GET "/" [] index-page)
  (route/resources "/")
  (route/not-found "Not Found"))

;; Application handler

(def app
  (handler/site app-routes))

;; Added jetty handler for running on production server

(defn start [port]
  (ring/run-jetty (var app) {:port (or port 8080) :join? false}))

(defn -main []
  (let [port (Integer/parseInt (System/getenv "PORT"))]
    (start port)))

