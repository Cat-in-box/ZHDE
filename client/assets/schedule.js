$(document).ready(function(){

	$(document).ready(function(){
		stationToFill()
		dateFill()
		getSchedule()
	});

	function clicker(rowNumber) {
		alert("тык2");
		alert(rowNumber);
		let currentTr = document.querySelectorAll('#t-schedule tr')[rowNumber];
		setCookie("selected-schedule-id", currentTr.cells[0].textContent, "1");

		alert(currentTr.cells[0].textContent);
		window.location.replace("buying.html");
    	};

	function stationToFill() {
		$.ajax({
			url: "http://localhost:8080/stations/getAllNames",
			type: "GET",
			dataType: "json",
			success: function (response){
				select = document.getElementById('stations-to');

				for (let i = 0; i<response.length; i++){
					let opt = document.createElement('option');
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

	function dateFill() {
		$.ajax({
			url: "http://localhost:8080/schedules/getAllDates",
			type: "GET",
			dataType: "json",
			success: function (response){
				select = document.getElementById('date');

				for (let i = 0; i<response.length; i++){
					let opt = document.createElement('option');
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

	function getSchedule() {
		//var fromStation = document.getElementById('stations-from').selected;
		let fromStation = document.getElementById('stations-from').options[document.getElementById('stations-from').selectedIndex].text
		alert(fromStation);
		let toStation = document.getElementById('stations-to').options[document.getElementById('stations-to').selectedIndex].text;
		alert(toStation);
		let tripDate = document.getElementById('date').options[document.getElementById('date').selectedIndex].text;
		alert(tripDate);
		$.ajax({
			url: "http://localhost:8080/schedules/getSchedule/" + fromStation + "/" + toStation + "/" + tripDate,
			type: "GET",
			dataType: "json",
			success: function (response){
				let colCount = $('#t-schedule th').length;

				const table = document.getElementById("t-schedule");

				for (let i = 0; i < response.length; i++) {
					let row = table.insertRow(i + 1);
					row.addEventListener('click', clicker.bind(null, i + 1));
					for (let j = 0; j < colCount; j++) {
						let cell = row.insertCell(j);
						cell.innerHTML = response[i][j];
					}
				}
				
				let rowCount = table.rows.length;
				table.deleteRow(rowCount - 1);
				
			},
			error: function(response) {
				let td = document.querySelectorAll('#t-schedule td');
				td[0].textContent = "Билетов не найдено"

			}
		});
	}

	$("#stations-to").change(function(){
		refillSchedule();
	})

	$("#date").change(function(){
		refillSchedule();
	})

	function refillSchedule() {
		const table = document.getElementById("t-schedule");
		while (table.rows.length > 2) {
			table.deleteRow(table.rows.length - 1);
		}

		let td = document.querySelectorAll('#t-schedule td');
		td[0].textContent = "Билетов не найдено"

		for (let i = 0; i < td.length; i++) {
			td[i].textContent = "";
		}
		
		getSchedule();
	}
	
});
