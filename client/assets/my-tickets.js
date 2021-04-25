$(document).ready(function(){

	tableFill()
	function tableFill() {
		$.ajax({
			url: "http://localhost:8080/tickets/my/2",
			type: "GET",
			dataType: "json",
			success: function (response){  
				let td = document.querySelectorAll('#t-my-tickets td');
				var colCount = $('#t-my-tickets th').length;

				const table = document.getElementById("t-my-tickets");

				for (let i = 0; i < response.length; i++) {

					let row = table.insertRow(i + 1);
					let cell1 = row.insertCell(0);
					let cell2 = row.insertCell(1);
					let cell3 = row.insertCell(2);
					let cell4 = row.insertCell(3);
					let cell5 = row.insertCell(4);
					let cell6 = row.insertCell(5);
					let cell7 = row.insertCell(6);
					let cell8 = row.insertCell(7);

					cell1.innerHTML = response[i][0];
					cell2.innerHTML = response[i][1];
					cell3.innerHTML = response[i][2];
					cell4.innerHTML = response[i][3];
					cell5.innerHTML = response[i][4];
					cell6.innerHTML = response[i][5];
					cell7.innerHTML = response[i][6];
					cell8.innerHTML = response[i][7];
				}

				
			},
			error: function(response) {
				console.log("Что-то пошло не так", error);
			}
		});
	}
	
});