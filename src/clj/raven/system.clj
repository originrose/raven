(ns raven.system
    (:require [system.core :refer [defsystem]]
              [system.components.http-kit :refer [new-web-server]]
              [raven.handler :as handler]))

(defsystem dev-system
  [:web-server (new-web-server 3000 handler/app)])
