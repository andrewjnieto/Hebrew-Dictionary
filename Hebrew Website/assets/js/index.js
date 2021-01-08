$(document).ready(function() {
	$("#search-form").on("submit", function() {
		event.preventDefault();
		const searchTerm = $("#searchBar").val();
		getWords(searchTerm);
	});
});

function getWords(searchTerm) {
	let url = "https://webhooks.mongodb-stitch.com/api/client/v2.0/app/hebrewdictionary-bqhaq/service/hebrew-get/incoming_webhook/getHebrew?word=";
	$.ajax({
		url:  url + searchTerm,
		method: 'GET',
		dataType: 'json',
		success: function(data) {
			outputWords(data);
		},
		error: function(data) {
			console.log('There was an error getting the words and the definitions.')
		}
	});
}

function outputWords(data) {
	let output = "<h2 id = \"results-header\">Results</h2>";
	for(let i = 0; i < data.length; i++) {
		output += outputWord(i + 1, data[i]);
	}
	$("#result-container").html(output);
}

function outputWord(index, def) {
	let results = $("#result-container").get(0);
	let result = '<div class="definition">' + 
	formatHeader(index, def) +
	formatAllDefs(def["all_defs"]) +
	formatDetails(def) +
	'</div>';
	return result;
}


function formatHeader(index, def) {
	let header = "<h3>";
	header += `${index}) ${def.no_niqqud} • ${def.niqqud} (${def.morph})`;
	return header + "</h3>";
}

function formatDetails(def) {
	return `<small><strong>Strong's Definition</strong>: ${def.strongs_def}</small><br>` +
		   `<small><strong>King James Definition</strong>: ${def.kjv_def}</small>`;
}

function formatAllDefs(all_defs) {
	let result = '<ol class="def-list">'
	for(let i = 0; i < all_defs.length; i++) {
		const obj = all_defs[i];
		if(obj["definition"]) {
			result += `<li class ="def-item"> ${obj["definition"]} </li>`;
		}else if(obj["senses"]) {
			result += formatAllDefs(obj["senses"]);
		}
	}
	return result + "</ol>"
}
