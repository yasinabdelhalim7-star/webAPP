function createTable() {
	
    parentNode = document.getElementById('list');
    
    var tableEl = document.createElement("TABLE");
    tableEl.setAttribute("id", "table1");
    tableEl.setAttribute("border", "1");
    parentNode.appendChild(tableEl);

	var tr3 = document.createElement("TR");
	
	var td4 = document.createElement("TD");
	texto1 = document.createTextNode("Id");
	td4.appendChild(texto1);
	td4.setAttribute("value", "Id");
	tr3.appendChild(td4);
	
	var td5 = document.createElement("TD");
	texto2 = document.createTextNode("Company Name");
	td5.appendChild(texto2);
	td5.setAttribute("value", "Company Name");
	tr3.appendChild(td5);
	
	var td6 = document.createElement("TR");
	texto3 = document.createTextNode("City");
	td6.appendChild(texto3);
	td6.setAttribute("value", "City");
	tr6.appendChild(td1);
	
	
	tableEl.appendChild(tr3);
	
	
    for (i = 0; i < data.length; i++) {
		
        var tr1 = document.createElement("TR");
        tableEl.appendChild(tr1);
		
        createListCell(tr1, data[i].CustomerID);
        createListCell(tr1, data[i].CompanyName);
        createListCell(tr1, data[i].City);
    }
}
function createListCell(tr1, value) {
        var td1 = document.createElement("TD");
        var textNode = document.createTextNode(value);
        td1.appendChild(textNode);
        tr1.appendChild(td1);
}

createTable();