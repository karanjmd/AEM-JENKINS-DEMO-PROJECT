(function (document, $, ns) {
    "use strict";
 $(document).on("click", ".cq-dialog-submit", function (e) {
        e.stopPropagation();
        e.preventDefault();
            var $form = $(this).closest("form.foundation-form"),
            resultLimit = $form.find("[name='./resultLimit']").val(),
            message, clazz = "coral-Button " ;


        if((resultLimit != "") && (resultLimit != null)) {
             console.log("resultLimit:"+resultLimit) ;  

            if(resultLimit>=11) {
                 $form.submit();

			}else{
                   ns.ui.helpers.prompt({
                title: Granite.I18n.get("Invalid Input"),
                message: "Result limit can not be less than 11",
                actions: [{
                    id: "CANCEL",
                    text: "CANCEL",
                    className: "coral-Button"
                }],
            callback: function (actionId) {
                if (actionId === "CANCEL") {
                }
            }
        });
			}
		}else
        {
             $form.submit();
        }
    });
})(document, Granite.$, Granite.author);