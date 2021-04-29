$(document).ready(function(){

	let scheduleId = getCookie("selected-schedule-id");
	alert(scheduleId);
	eraseCookie("selected-schedule-id");
	carriageFill()
	getCarriageInfo()
	//GetSchedule()

	/*
	function clicker(rowNumber) {
		alert("тык2");
		alert(rowNumber);
		let currentTr = document.querySelectorAll('#t-schedule tr')[rowNumber];
		setCookie("selected-schedule-id", currentTr.cells[0].textContent, "1");

		alert(currentTr.cells[0].textContent);
		window.location.replace("buying.html");
    	};
	*/

	function carriageFill() {
		$.ajax({
			url: "http://localhost:8080/traincompositions/carriagenumber/" + scheduleId,
			type: "GET",
			dataType: "json",
			success: function (response){
				select = document.getElementById('carriages');

				for (let i = 1; i<response+1; i++){
					let opt = document.createElement('option');
					opt.value = i;
					opt.innerHTML = i;
					select.appendChild(opt);

				}


			},
			error: function(response) {
				console.log("Что-то пошло не так", error);
			}
		});
	}

	function getCarriageInfo() {
		let carriageNumber = document.getElementById('carriages').options[document.getElementById('carriages').selectedIndex].text;
		alert(carriageNumber);
		$.ajax({
			url: "http://localhost:8080/traincompositions/getcarriageinfo/" + scheduleId + "/" + carriageNumber,
			type: "GET",
			dataType: "json",
			success: function (response){
				let price = response[0]
				let blocksNumber = response[1]
                let blockSeatsNumber = response[2]
                let startPlaceNumber = response[3]

				document.getElementById('price').textContent = price;

				const table = document.getElementById("t-places");
				//let colCount = $('#t-places th').length;
				let colCount = blocksNumber*3 + blocksNumber-1;

				if (blockSeatsNumber == 2) {
					let rowCount = 3;
					let rowBrick = 1;
					let rowPass = 3;
				} else if (blockSeatsNumber == 4) {
					let rowCount = 4;
					let rowBrick = 2;
					let rowPass = 3;
				} else if (blockSeatsNumber == 6) {
					let rowCount = 4;
					let rowBrick = null;
					let rowPass = 2;
				} else if (blockSeatsNumber == 9) {
					let rowCount = 5;
					let rowBrick = null;
					let rowPass = 3;
				} else {
					alert("Упс, что-то пошло не так при построении таблицы мест(((( Сообщите мне, если увидили это сообщение!")
				}

				try {
					for (let i = 0; i < rowCount; i++) {
						let row = table.insertRow(i + 1);
						if ((rowBrick != null) && (i + 1 == rowBrick)) {
							for (let j = 0; j < colCount; j++) {
								let cell = row.insertCell(j);
								cell.innerHTML = "-";
							}
						} else if (i + 1 == rowPass) {
							for (let j = 0; j < colCount; j++) {
								let cell = row.insertCell(j);
								cell.innerHTML = "";
							}
						} else {
							for (let j = 0; j < colCount; j++) {
								let cell = row.insertCell(j);
								if (j % 2 == 0) {
									if (rowCount == 3) {
										cell.innerHTML = startPlaceNumber + Math.floor(j/2);
									} else if (rowCount == 4) {
										if (i != rowCount-1) {
											cell.innerHTML = startPlaceNumber + j + Math.abs(i-1);
										} else {
											cell.innerHTML = startPlaceNumber-1 + blocksNumber*blockSeatsNumber - Math.floor(j/2)
										}
									} else if (rowCount == 5) {
										if (i != rowCount-1) {
											cell.innerHTML = startPlaceNumber + Math.floor(j/2)*3 + Math.abs(i-2);
										} else {
											cell.innerHTML = startPlaceNumber-1 + blocksNumber*blockSeatsNumber - Math.floor(j/2) //ДОДЕЛАТЬ
										}
									}
									cell.style.backgroundColor = "#99FF99";
									cell.addEventListener('click', clicker.bind(null, i + 1, j));
								} else if (j % 4 == 3) {
									let cell = row.insertCell(j);
									cell.innerHTML = "|";
								} else {
									let cell = row.insertCell(j);
									cell.innerHTML = "";
								}
								
							}
						}
					}
				
				}
				
				let rowCount = table.rows.length;
				table.deleteRow(rowCount - 1);

				/*
				select = document.getElementById('t-places');

				for (let i = 0; i<response.length; i++){
					let opt = document.createElement('option');
					opt.value = i;
					opt.innerHTML = response[i];
					select.appendChild(opt);

				}
				*/

			},
			error: function(response) {
				console.log("Что-то пошло не так", error);
			}
		});
	}

	/*
	function GetSchedule() {
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
		
		GetSchedule();
	}
	*/
});
