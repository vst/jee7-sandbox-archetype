$(document).ready(function () {
	$.ajax({	
		url:"api/version",
		dataType: "json",
		success: function (data) {
			$("h1").append(" <small>v" + data.app + "</small>");
		},
		error: function (jqXHR, textStatus, errorThrown) {
			alert("An error occured when reaching the api.");
		}
	});
});