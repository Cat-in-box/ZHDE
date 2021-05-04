$(document).ready(function(){

	let user = getCookie("user-id");
	if (user == null) {
		alert("Чтобы воспользоваться сервисом, ВОЙДИТЕ или ЗАРЕГИСТРИРУЙТЕСЬ");
		window.location.replace("index.html");
	} else {

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
						row.addEventListener('click', clicker.bind(null, i + 1));
						for (let j = 0; j < colCount; j++) {
							let cell = row.insertCell(j);
							cell.innerHTML = response[i][j];
						}
					}
					
					var rowCount = table.rows.length;
					table.deleteRow(rowCount - 1);
					
				},
				error: function(error) {
					let td = document.querySelectorAll('#t-my-tickets td');
					td[0].textContent = "Билетов не найдено"
				}
			});
		}
		

		function clicker(rowNumber) {
			let currentTr = document.querySelectorAll('#t-my-tickets tr')[rowNumber];

			var result = confirm("Хотите сдать выбранный билет?");
			if(result) { 
				deleteTicket(currentTr.cells[0].textContent)
			}
		};

		function deleteTicket(ticketId) {
			$.ajax({
				url: "http://localhost:8080/tickets/delete/" + ticketId,
				type: "DELETE",
				dataType: "json",
				success: function (response){ 
					const table = document.getElementById("t-my-tickets");
					while (table.rows.length > 2) {
						table.deleteRow(table.rows.length - 1);
					}

					let td = document.querySelectorAll('#t-my-tickets td');

					for (let i = 0; i < td.length; i++) {
						td[i].textContent = "";
					}
					tableFill()
				},
				error: function(error) {
					console.log("Что-то пошло не так", error);
				}
			});
		}
	}
});