(ns raven.system
    (:require [system.core :refer [defsystem]]
              
              [system.components.http-kit :refer [new-web-server]]
              [think.service.config :as config]
              [raven.handler :as handler]))

(defsystem dev-system
  [:web-server (new-web-server 3000 handler/app)
   ])

(defsystem prod-system
  [:web-server (new-web-server (config/get-config :web-server-port) handler/app)
   ])
