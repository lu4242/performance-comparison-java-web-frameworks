// Taken from http://jumpstart.doublenegative.com.au/jumpstart/examples/ajax/multiplezoneupdate
// A class that updates a zone on any client-side event.
// Based on http://tinybits.blogspot.com/2010/03/new-and-better-zoneupdater.html
// and some help from Inge Solvoll.

ZoneUpdater = Class.create( {

	initialize : function(spec) {
		this.element = $(spec.elementId);
		this.listenerURI = spec.listenerURI;
		$(this.element).getStorage().zoneId = spec.zoneId;
		
		if (spec.clientEvent) {
			this.clientEvent = spec.clientEvent;
			this.element.observe(this.clientEvent, this.updateZone.bindAsEventListener(this));
		}
	},
	
	updateZone : function() {
		var zoneManager = Tapestry.findZoneManager(this.element);
		
		if (!zoneManager) {
			return;
		}

		var listenerURIWithValue = this.listenerURI;
		
		if (this.element.value) {
			var param = this.element.value;
			if (param) {
				listenerURIWithValue = addRequestParameter('param', param, listenerURIWithValue);
			}
		}
		
		zoneManager.updateFromURL(listenerURIWithValue);
	}
	
} )

function addRequestParameter(name, value, url) {
	if (url.indexOf('?') < 0) {
		url += '?'
	} else {
		url += '&';
	}
	value = escape(value);
	url += name + '=' + value;
	return url;
}
