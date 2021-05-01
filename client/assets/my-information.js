$(document).ready(function(){

	let user = getCookie("user-id");
	if (user == null) {
		alert("Чтобы воспользоваться сервисом, ВОЙДИТЕ или ЗАРЕГИСТРИРУЙТЕСЬ");
		window.location.replace("index.html");
	}
	
	informationFill()
	function informationFill() {
		alert(user);
		$.ajax({
			url: "http://localhost:8080/clients/info/" + user,
			type: "GET",
			dataType: "json",
			success: function (response){
				alert(response["email"]);
				document.getElementById('email').textContent = response["email"];

			},
			error: function(response) {
				console.log("Что-то пошло не так", error);
			}
		});
	}
	
});
