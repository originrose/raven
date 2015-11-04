# raven
A tiny Clojurescript notification library based on [Toastr](http://codeseven.github.io/toastr/demo.html)

[![Clojars Project](http://clojars.org/thinktopic/raven/latest-version.svg)](http://clojars.org/thinktopic/raven)

### Use

- Include raven styles
  + (include-css "css/raven.css")
  + otherwise provide custom styles
- Include `animate.css` if you want transition anitmations
  + (include-css "css/animate.min.css")
- Mount notifications component in page
```clj
(require '[raven.notify :as notify])

(defn current-page
  []
  [:div.page-wrapper
    [(session/get :current-page)]
    [notify/notifications]])
```
- Post messages
```clj
;; Info
(notify/notify "Did you know..." :type :info)

;; Success with a 500ms delay
(notify/notify "Success!" :type :success :delay 500)

;; Errors
(notify/notify "Something has gone wrong..." :type :error)
```
