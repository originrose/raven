(ns raven.notify
  (:require [reagent.core :as reagent :refer [atom]]))


(defonce pending-notifications* (atom []))


(defn remove-notification
  ([state* m] (remove-notification state* m 0))
  ([state* m delay]
   (let [node (:node @state*)]
     ;; first animate out
     (js/setTimeout
      (fn [_]
        (swap! state* assoc :animate-out? true)
        ;; on animation end remove this from pending-notifications*
        (.addEventListener node
          "animationend"
          (fn [_] (swap! pending-notifications* #(remove (partial = m) %)))
          false))
      delay))))


(defn notification
  [m]
  (let [state* (atom {:animate-out false
                      :node nil})]
    (reagent/create-class
     {:component-did-mount
      (fn [this]
        (let [node (reagent/dom-node this)]
          (swap! state* assoc :node node)
          (remove-notification state* m (:delay m))))
      :reagent-render
      (fn [m]
        [:div.notification.animated {:class (str (name (:type m)) " "
                                                 (if (:animate-out? @state*)
                                                   "fadeOutRight"
                                                   "fadeInRight"))
                            :on-click (fn [ev]
                                        (remove-notification state* m)
                                        (.stopPropagation ev))}
         (when (:title m)
           [:h3 (:title m)])
         [:pre (:message m)]])})))


(defn notifications
  []
  (fn []
    [:div.notify-wrapper
     (for [n @pending-notifications*]
       ^{:key n}
       [notification n])]))


(defn notify
  ([message]
   (notify message :success))
  ([message & {:keys [type delay]
               :or {type :info
                    delay 5000}}]
   (let [opts {:type type
               :delay delay
               :message message}]
     (swap! pending-notifications* #(conj % opts)))))


(defn current-notification-count
  []
  (count @pending-notifications*))
