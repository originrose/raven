(ns raven.styles
  (:require [garden.def :refer [defstyles]]
            [garden.units :refer [percent px]]
            [garden.color :as color :refer [hsl rgb rgba]]))

(defstyles raven-styles
  [[:.notify-wrapper {:max-width "33%"
                      :position :fixed
                      :padding-right (px 10)
                      :top (px 50)
                      :right (px 25)
                      :z-index 10000}

    [:.notification {:border "2px solid lightgrey"
                     :font-size (px 13)
                     :background (rgb 240 240 240)
                     :padding (px 10)
                     :box-shadow "0px 0px 6px rgba(0,0,0,0.15)"
                     :color :white
                     :text-shadow "0px 0px 3px rgba(255, 255, 255, 0.5)"
                     :opacity 0.8
                     :border-radius (px 5)}

     [:&.success {:background-color "#51a351"}]
     [:&.warning {:background-color "#f89406"}]
     [:&.error   {:background-color "#bd362f"}]
     [:&.info    {:background-color "#2f96b4"}]
     [:&:hover   {:opacity 1
                  :text-shadow :none}]

     [:a.remove {:position :absolute
                 :top (px 3)
                 :right (px 3)
                 :color (rgb 200 200 200)}
      [:&:hover {:color :grey}]]]]])
