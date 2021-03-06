(function(factory){
  if (typeof define === 'function' && define.amd) {
    define(['jquery'], factory);
  } else if (typeof exports === 'object') {
    factory(require('jquery'));
  } else {
    factory(jQuery);
  }
}(function($) {
  $.fn.qubit = function(options) {
    return this.each(function() {
      new Qubit(this, options);
    });
  };
  var Qubit = function(el) {
    var self = this;
    this.scope = $(el);
    var handler = function(e) {
      if (!self.suspendListeners) {
        self.process(e.target);
      }
    };
    this.scope.on('change', 'input[type=checkbox]', handler);
    // workaround for IE<10
    if (document.documentMode && document.documentMode <= 9) {
      this.scope.on('click', 'input[type=checkbox]:indeterminate', handler);
    }
    //this.processParents();
  };
  Qubit.prototype = {
    
	currentItem: null,
 
    itemSelector: 'li',
    process: function(checkbox) {
      console.log("inside process");
      checkbox = $(checkbox);
      
      console.log("checkBox");
      console.log(checkbox);
      
      currentItem = checkbox;
      
      var parentItems = checkbox.parentsUntil(this.scope, this.itemSelector);
      var self = this;
      try {
        this.suspendListeners = true;
        // all children inherit my state
        console.log("parents:::");
        console.log(parentItems);
        
        console.log("curParent:");
        console.log(parentItems.eq(0));
        if( checkbox.prop( 'checked' ) !== true ) {

            parentItems.eq(0).find('input[type=checkbox]').not(':disabled')
              .filter(checkbox.prop('checked') ? ':not(:checked)' : ':checked')
              .each(function() {
            	  console.log("item:");
            	  console.log(this);
                if (!$(this).children().hasClass('hidden')) {
                	console.log("object:");
                	console.log(self);
                	self.setChecked($(this), checkbox.prop('checked'));
                }
              });
        }
        /*
        parentItems.eq(0).find('input[type=checkbox]').not(':disabled')
          .filter(checkbox.prop('checked') ? ':not(:checked)' : ':checked')
          .each(function() {
        	  console.log("item:");
        	  console.log(this);
            if (!$(this).children().hasClass('hidden')) {
            	console.log("object:");
            	console.log(self);
            	self.setChecked($(this), checkbox.prop('checked'));
            }
          });
        */

    	self.setChecked(checkbox, checkbox.prop('checked'));
    	

        if( checkbox.prop( 'checked' ) === true ) {

            this.processParents();
        
        }
    	
      } finally {
        this.suspendListeners = false;
      }
    },
    processParents: function() {
    	
        var self = this, changed = false;
        this.scope.find('input[type=checkbox]').not(':disabled').each(function() {
        var $this = $(this);
        var parent = $this.closest(self.itemSelector);
        
        if($(this).attr('id') == currentItem.attr('id')){
        	return;
        	
        }
        var currrentCatParent = currentItem.closest("ol").siblings("input");
        var thisCatParent = $(this).closest("ol").siblings("input");
        
        if( thisCatParent != undefined && thisCatParent.attr('id') == currrentCatParent.attr('id')) {
        	
        	return;
        }
        
        var children = parent.find('input[type=checkbox]').not(':disabled').not($this);
        var numChecked = children.filter(function() {
          return $(this).prop('checked') || $(this).prop('indeterminate');
        }).length;

        if (children.length) {
          if (numChecked === 0) {
           // self.setChecked($this, false) && (changed = true);
          }
          else if (numChecked == children.length) {
            self.setChecked($this, true) && (changed = true);
          }
          else {
            self.setChecked($this, true) && (changed = true);  //changed here from intedeterminate to checked
          }
        }
        else {
          self.setIndeterminate($this, false) && (changed = true);
        }
      });
      if (changed) this.processParents();
    },
    setChecked: function(checkbox, value, event) {
      var changed = false;
      if (checkbox.prop('indeterminate')) {
        checkbox.prop('indeterminate', false);
        changed = true;
      }
      if (checkbox.prop('checked') != value) {
        checkbox.prop('checked', value).trigger('change');
        changed = true;
      }
      return changed;
    },
    setIndeterminate: function(checkbox, value) {
      if (value) {
        checkbox.prop('checked', false);
      }
      if (checkbox.prop('indeterminate') != value) {
        checkbox.prop('indeterminate', value);
        return true;
      }
    }
  };
}));
