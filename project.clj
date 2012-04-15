(defproject footiefree "0.0.1"
  :description "Twitter without football..."
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [hiccup "0.3.7"]
                 [ring/ring-jetty-adapter "0.3.10"]
                 [cheshire "3.1.0"]
                 [compojure "1.0.2"]]
  :dev-dependencies [[lein-ring "0.6.3"]]
  :ring {:handler footiefree.core/app})

