
(ns footiefree.layout
  (:use [hiccup.core :only [html]]
        [hiccup.form-helpers :only [form-to submit-button label text-field]]
        [hiccup.page-helpers :only [include-css]]
        [clojure.string :only [split]]))

(def footy-words #{
  "goal"
  "ref"
})

(defn- is-footy-related
  [tweet]
  (let [text (.toLowerCase (:text tweet))]
    (not (some footy-words 
               (split text #"\s+")))))

(defn- content
  [body]
  (html
    [:head
      [:title "Footie Free Twitter"]
      (include-css "/css/default.css")]
    [:body
      [:h1 "Footie Free Twitter"]
      (form-to [:get "/"]
        (label "nick" "Username")
        (text-field {} "nick")
        (submit-button {} "Show me!"))
      body
      [:div "by rod"]]))

;; Public

(defn tweets-page [tweets]
  (content
    [:ul
      (for [tweet (filter is-footy-related tweets)]
        [:li (:text tweet)])]))

(defn index-page []
  (content
    [:div.intro
      "Welcome to a Football free Twitter, just enter your nickname!"]))

