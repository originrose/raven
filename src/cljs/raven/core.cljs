(ns raven.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [goog.events :as events]
              [goog.history.EventType :as EventType]
              [raven.notify :refer [notifications notify]])
    (:import goog.History))

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to raven"]
   [:div [:a {:href "#/about"} "go to about page"]]])

(defn about-page []
  [:div [:h2 "About raven"]
   [:div [:a {:href "#/"} "go to the home page"]]])

(defn current-page []
  [:div
   [notifications]
   [(session/get :current-page)]])

;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (session/put! :current-page #'home-page))

(secretary/defroute "/about" []
  (session/put! :current-page #'about-page))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (hook-browser-navigation!)
  (mount-root)
  (notify "Here is a test notification!")
  (notify "And a warning!" :type :warning))
