$(document).ready(function(){
	$("#btn1").click(function(){
		$.ajax({
			url: "http://localhost:8080/tags",
			type: "GET",
			dataType: "json",
			success: function (response){
				console.log(response);
			},
			error: function(response) {
				console.log("Что-то пошло не так", error);
			}
		});
	});
	
	$("#authorisation").click(async function(){
		alert(document.getElementById("login-item").value)
		alert(document.getElementById("password-item").value)
		let varData = {
			"login": document.getElementById("login-item").value,
			"password": document.getElementById("password-item").value
		};
		let response = await fetch("http://localhost:8080/tags", {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json;charset=utf-8'
			},
			body: JSON.stringify(varData)
		});
	});

	
});