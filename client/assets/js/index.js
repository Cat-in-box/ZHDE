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
			error: function(error) {
				console.log("Что-то пошло не так", error);
			}
		});
	}

	$("#b-authorisation").click(async function(){
		let login = document.getElementById("login-item").value;
		let password = document.getElementById("password-item").value;
		$.ajax({
			url: "http://localhost:8080/clients/authorisation/" + login + "/" + password,
			type: "GET",
			dataType: "json",
			success: function (response){  
				setCookie("user-id", response, "100");
				window.location.replace("schedule.html");
			},
			error: function(response) {
				if (response.status == 400) {
				alert("Неверный логин или пароль");
				} else {
				alert("Кажется, вы еще не зарегистрировались!");
				}
			}
		});
	});
	
});