$(document).ready(function(){

	let user = getCookie("user-id");
	if (user == null) {
		alert("Чтобы воспользоваться сервисом, ВОЙДИТЕ или ЗАРЕГИСТРИРУЙТЕСЬ");
		window.location.replace("index.html");
	}

	tableFill()
	function tableFill() {
		$.ajax({
			url: "http://localhost:8080/tickets/my/" + user,
			type: "GET",
			dataType: "json",
			success: function (response){  
				var colCount = $('#t-my-tickets th').length;

				const table = document.getElementById("t-my-tickets");

				for (let i = 0; i < response.length; i++) {
					let row = table.insertRow(i + 1);
					for (let j = 0; j < colCount; j++) {
						let cell = row.insertCell(j);
						cell.innerHTML = response[i][j];
					}
				}
				
				var rowCount = table.rows.length;
				table.deleteRow(rowCount - 1);
				
			},
			error: function(response) {
				let td = document.querySelectorAll('#t-my-tickets td');
				td[0].textContent = "Билетов не найдено"
			}
		});
	}
	
});