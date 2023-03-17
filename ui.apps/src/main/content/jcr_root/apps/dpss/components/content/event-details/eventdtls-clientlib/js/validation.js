(function (document, $, ns) {
    "use strict";

    $(document).on("click", ".cq-dialog-submit", function (e) {
        e.stopPropagation();
        e.preventDefault();
            var $form = $(this).closest("form.foundation-form"),
            startdate = $form.find("[name='./startDate']").val(),
            enddate = $form.find("[name='./endDate']").val(),
            message, clazz = "coral-Button " ;


        if((startdate != "") && (startdate != null)) {
             console.log("startdatr:"+startdate) ;  
              console.log("end date:"+enddate) ; 
            const dateIsAfter = moment(enddate).isAfter(moment(startdate));
             console.log("compare:"+ dateIsAfter) ;
            if(dateIsAfter) {
                 $form.submit();

			}else{
                   ns.ui.helpers.prompt({
                title: Granite.I18n.get("Invalid Input"),
                message: "Please Enter a valid End Date",
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