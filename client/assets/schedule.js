$(document).ready(function(){

	stationToFill()
	DateFill()
	GetSchedule()
	function stationToFill() {
		$.ajax({
			url: "http://localhost:8080/stations/getAllNames",
			type: "GET",
			dataType: "json",
			success: function (response){
				select = document.getElementById('stations-to');

				for (var i = 0; i<response.length; i++){
					var opt = document.createElement('option');
					opt.value = i;
					opt.innerHTML = response[i];
					select.appendChild(opt);

				}

			},
			error: function(response) {
				console.log("Что-то пошло не так", error);
			}
		});
	}

	function DateFill() {
		$.ajax({
			url: "http://localhost:8080/schedules/getAllDates",
			type: "GET",
			dataType: "json",
			success: function (response){
				select = document.getElementById('date');

				for (var i = 0; i<response.length; i++){
					var opt = document.createElement('option');
					opt.value = i;
					opt.innerHTML = response[i];
					select.appendChild(opt);

				}

			},
			error: function(response) {
				console.log("Что-то пошло не так", error);
			}
		});
	}

	function GetSchedule() {
		//var fromStation = document.getElementById('stations-from').selected;
		var fromStation = document.getElementById('stations-from').options[document.getElementById('stations-from').selectedIndex].text
		alert(fromStation);
		var toStation = document.getElementById('stations-to').options[document.getElementById('stations-from').selectedIndex].text;
		alert(toStation);
		var tripDate = document.getElementById('date').options[document.getElementById('date').selectedIndex].text;
		alert(tripDate);
		$.ajax({
			url: "http://localhost:8080/schedules/getSchedule/" + fromStation + "/" + toStation + "/" + tripDate,
			type: "GET",
			dataType: "json",
			success: function (response){
				var colCount = $('#t-schedule th').length;

				const table = document.getElementById("t-schedule");

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
				let td = document.querySelectorAll('#t-schedule td');
				td[0].textContent = "Билетов не найдено"

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