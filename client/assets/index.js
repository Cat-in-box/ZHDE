$(document).ready(function(){

	tableFill()
	function tableFill() {
		$.ajax({
			url: "http://localhost:8080/trips/top5",
			type: "GET",
			dataType: "json",
			success: function (response){  
				let td = document.querySelectorAll('#top5 td');
				
				for( let i = 0; i < td.length; i++ ) {
					td[i].textContent = response[Math.floor(i/3)][i%3];
				}

			},
			error: function(response) {
				console.log("Что-то пошло не так", error);
			}
		});
	}

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