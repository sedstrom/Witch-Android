var langs = [];
langs["java"] = "JAVA";
langs["kotlin"] = "KOTLIN";

var toggleCode = function(event) {
  event.preventDefault();
  var codeContainerIndex = event.data.codeContainerIndex;
  var clazz = event.data.codeClazz;
  var container = $($(document).find(".code-container")[codeContainerIndex]);
  var codes = container.find(".code");
  for(j = 0; j < codes.length; j++) {
    var code = $(codes[j]);
    var highlight = code.find(".highlight");
    if(code.hasClass(clazz)) {
      highlight.addClass("active");
    } else {
      highlight.removeClass("active");
    }
  }

  var buttons = container.find(".toggles").children();
  for(k = 0; k < buttons.length; k++) {
    var button = $(buttons[k]);
    if(button.hasClass(clazz)) {
      button.addClass("active");
    } else {
      button.removeClass("active");
    }
  }
};

$(document).ready(function() {
  // Add toggle buttons
  var containers = $(".code-container");
  for (i = 0; i < containers.length; i++) {
    var container = $(containers[i]);
    var toggles = container.append("<div class=\"toggles u-pull-right\"></div>").find(".toggles");
    var clazzes = Object.keys(langs);
    for (j = 0; j < clazzes.length; j++) {
      var clazz = clazzes[j];
      var name = langs[clazz];
      var code = container.find("." + clazz);
      if(code.length > 0) {
        code.addClass(j);
        var button = toggles.append("<button>"+ name + "</button>").children().last();
        button.css("marginLeft", "5px");
        button.addClass(clazz);
        button.click({codeContainerIndex: i, codeClazz: clazz}, toggleCode);
      }
    }
  }

  // Select first lang by default
  var allToggles = $(".toggles");
  console.log("toggles: " + allToggles.length);
  for (i = 0; i < allToggles.length; i++) {
    var buttons = $($(allToggles[i]).children());
    $(buttons[0]).trigger("click");
    // Disable if only one lang.
    if(buttons.length < 2) {
      $(buttons[0]).attr("disabled", "disabled");
    }
  }
});
