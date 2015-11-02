(ns handler
 (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :refer [not-found resources]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [include-js include-css]]
            [prone.middleware :refer [wrap-exceptions]]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :as response]
            [environ.core :refer [env]]))

(def home-page
  (html
   [:html
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1"}]
     (include-css "css/raven.css")
     (include-css "css/animate.min.css")
     (include-css (if (env :dev) "css/site.css" "css/site.min.css"))]
    [:body
     [:div#app
      [:h3 "ClojureScript has not been compiled!"]
      [:p "please run "
       [:b "lein figwheel"]
       " in order to start the compiler"]]
     (include-js "js/app.js")]]))

(defroutes* endpoints
  (GET* "/" [] home-page)
  (GET* "/helloworld" []
        (response/ok {:message "hello world!!!"}))
  (resources "/")
  (not-found "Not Found"))

(defapi raven-api
  (swagger-ui "/swagger")
  (swagger-docs
   {:info {:title "raven"}})
  endpoints)

(def app
  (let [handler (wrap-defaults raven-api site-defaults)]
    (if (env :dev)
      (-> handler
          wrap-exceptions
          wrap-reload)
      handler)))
