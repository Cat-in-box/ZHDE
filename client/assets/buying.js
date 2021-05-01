$(document).ready(function(){

	let user = getCookie("user-id");
	if (user == null) {
		alert("Чтобы воспользоваться сервисом, ВОЙДИТЕ или ЗАРЕГИСТРИРУЙТЕСЬ");
		window.location.replace("index.html");
	}

	let scheduleId = getCookie("selected-schedule-id");
	alert(scheduleId);
	eraseCookie("selected-schedule-id");
	carriageFill()
	let occupiedPlacesList = new Array();
	getOccupiedPlaces()
	getCarriageInfo()

	function carriageFill() {
		$.ajax({
			url: "http://localhost:8080/traincompositions/carriagenumber/" + scheduleId,
			type: "GET",
			dataType: "json",
			success: function (response){
				select = document.getElementById('carriages');

				for (let i = 2; i<response+1; i++){
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
	};

	function getOccupiedPlaces() {
		$.ajax({
			url: "http://localhost:8080/tickets/getoccupiedplaces/" + scheduleId,
			type: "GET",
			dataType: "json",
			success: function (response){
				alert("ВНИМАНИЕ");
				alert(response);
				for (let i = 0; i < response.length; i++) {
					occupiedPlacesList.push(response[i]);
				}
				alert("Получили занятые места");
			},
				
			error: function(error) {
				console.log("На этот рейс не купили ни одного билета");
			}
		});
	};

	function freePlaceChecker(placeNumber, cell, i, j) {
		if (occupiedPlacesList.indexOf(Number(placeNumber))>=0) { //ТУТ
			cell.style.backgroundColor = "#FF6666";
		} else {
			cell.style.backgroundColor = "#99FF99";
			cell.addEventListener('click', clicker.bind(null, i, j));
		}
	};

	function getCarriageInfo() {
		alert(document.getElementById('carriages').options[document.getElementById('carriages').selectedIndex].text);
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
				alert("startPlaceNumber");
				alert(startPlaceNumber);

				document.getElementById('price').textContent = price;

				const table = document.getElementById("t-places");
				alert(table);
				let colCount = blocksNumber*3 + blocksNumber-1;
				let rowCount = null;
				let rowBrick = null;
				let rowPass = null;

				if (blockSeatsNumber == 2) {
					rowCount = 3;
					rowBrick = 1;
					rowPass = 2;
				} else if (blockSeatsNumber == 4) {
					rowCount = 4;
					rowBrick = 2;
					rowPass = 3;
				} else if (blockSeatsNumber == 6) {
					rowCount = 4;
					rowBrick = null;
					rowPass = 2;
				} else if (blockSeatsNumber == 9) {
					rowCount = 5;
					rowBrick = null;
					rowPass = 3;
				} else {
					alert("Упс, что-то пошло не так при построении таблицы мест(((( Сообщите мне, если увидили это сообщение!")
				}

				for (let i = 0; i < rowCount + 1; i++) {
					let row = table.insertRow(i);
					if ((rowBrick != null) && (i == rowBrick)) {
						for (let j = 0; j < colCount; j++) {
							let cell = row.insertCell(j);
							cell.innerHTML = "-";
						}
					} else if (i == rowPass) {
						for (let j = 0; j < colCount; j++) {
							let cell = row.insertCell(j);
							cell.innerHTML = "";
						}
					} else {
						for (let j = 0; j < colCount; j++) {
							let cell = row.insertCell(j);
							if (i != rowCount-1) {
								if (j % 4 == 3) {
									cell.innerHTML = "|";
								} else if (j % 2 == 0) {
									if (rowCount == 3) {
										cell.innerHTML = startPlaceNumber + Math.floor(j/2);
									} else if (rowCount == 4) {
										cell.innerHTML = startPlaceNumber + j + Math.abs(i-1);
									} else if (rowCount == 5) {
										cell.innerHTML = startPlaceNumber + Math.floor(j/2)*3 + Math.abs(i-2);
									}
									freePlaceChecker(cell.textContent, cell, i, j);
								} else {
									cell.innerHTML = "";
								}
							} else {
								if (j % 4 == 3) {
									cell.innerHTML = "|";
								} else {
										if ((rowCount == 4) && (j % 2 == 0)) {
										if (j % 2 == 0) {
											cell.innerHTML = startPlaceNumber-1 + blocksNumber*blockSeatsNumber - Math.floor(j/2);
											freePlaceChecker(cell.textContent, cell, i, j);
										} else {
											cell.innerHTML = "";
										}
									} else if (rowCount == 5) {
										cell.innerHTML = startPlaceNumber-1 + blocksNumber*blockSeatsNumber - j + Math.floor((j+1)/4)
										freePlaceChecker(cell.textContent, cell, i, j);
									}
								}
							}
						}
					}
				}
				
				rowCount = table.rows.length;
				table.deleteRow(rowCount - 1);
			},
			error: function(response) {
				console.log("Что-то пошло не так", error);
			}
		});
	};

	$("#carriages").change(function(){
		refillCarriageInfo();
	});

	function refillCarriageInfo() {
		const table = document.getElementById("t-places");
		while (table.rows.length > 0) {
			table.deleteRow(table.rows.length - 1);
		}


		let td = document.querySelectorAll('#t-schedule td');
		for (let i = 0; i < td.length; i++) {
			td[i].textContent = "";
		}
		
		getCarriageInfo();
	};

	function clicker(rowNumber, colNumber) {
		alert("тык2");
		alert("Подтвердите покупку билета");
		alert(rowNumber);
		alert(colNumber);

		let currentTr = document.querySelectorAll('#t-places tr')[rowNumber];

		//postNewTicket(currentTr.cells[colNumber].textContent);

		//window.location.replace("my-tickets.html");
    };
	
	async function postNewTicket(place) {

		let varData = {
		"clientId": getCookie("client-id"),
		"scheduleId": scheduleId,
		"railwayCarriage": document.getElementById('carriages').options[document.getElementById('carriages').selectedIndex].text,
		"place": place,
		"price": document.getElementById('price').textContent
		};
		let response = await fetch("http://localhost:8080/tickets", {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json;charset=utf-8'
			},
			body: JSON.stringify(varData)
		}, {mode: 'cors'}).then(function (response) {
			console.log(response);
			alert("Билет добавлен")
		}).catch(function (error) {
			console.log("Не удалось создать билет", error);
		});
	}

});