 ;(function() {
    	  "use strict";
    	  const newBarContainer = document.getElementsByClassName("container")[0];
    	  const addBarButton = document.getElementsByTagName("button")[0];
    	  const progressBar = document.getElementsByClassName("bar-container")[0];
    	  var progressComplete = Promise.resolve();

    	  addBarButton.addEventListener("click", () => {
    		  const newBar = progressBar.cloneNode(true);
    		  newBarContainer.appendChild(newBar);

    		  progressComplete = progressComplete.then(() => {
	    	      return new Promise((startNextbar) => {
	    	    	  setTimeout(() => {
	    	    		  newBar.children[0].classList.add("active");
	    	    		  setTimeout(startNextbar, 1000);
	    	        }, 100);
	    	      });
    		  })
    	  });
})();