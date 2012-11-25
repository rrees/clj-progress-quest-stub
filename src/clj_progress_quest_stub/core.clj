(ns clj-progress-quest-stub.core

  [:import [javax.swing JLabel JPanel JFrame JProgressBar JButton]])

(defn -main [& args]
	(let [status-message (JLabel. "Your adventure awaits")
		progress-bar (JProgressBar.)
		start-button (JButton. "Begin your quest")
		panel (doto (JPanel.) (. add status-message) (. add progress-bar) (. add start-button))]
	(doto (JFrame. "Clojure Progress Quest")
		(.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
		(.setContentPane panel)
		(.pack)
		(.setVisible true))))