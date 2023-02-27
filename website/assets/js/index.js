$(document).ready(function() {
	$("#search-form").on("submit", function() {
		event.preventDefault();
		const searchTerm = $("#searchBar").val();
		getWords(searchTerm);
	});
});

function getWords(searchTerm) {
	let url = "https://data.mongodb-api.com/app/hebrewdictionary-bqhaq/endpoint/hebrew/search?text=";
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
	formatDefinitions(def) +
	formatVariants(index, def['variants']) + 
	'</div>';
	return result;
}


function formatHeader(index, def) {
	let header = "<h3>";
	header += `${index}) ${def.no_niqqud} • ${def.niqqud} (${def.morph})`;
	return header + "</h3>";
}

function formatDefinitions(def) {
	let definitions = '<div class="definitions">';
	definitions += formatAllDefs(def["all_defs"]);
	definitions += formatAdditionalDefs(def);
	definitions += '</div>';
	return definitions;
}

function formatAllDefs(all_defs) {
	let result = '<ol class="def-list">';
	for(let i = 0; i < all_defs.length; i++) {
		const obj = all_defs[i];
		if(obj["def"]) {
			result += `<li class ="def-item"> ${obj["def"]} </li>`;
		}else if(obj["senses"]) {
			result += formatAllDefs(obj["senses"]);
		}
	}
	return result + "</ol>";
}

function formatAdditionalDefs(def) {
	return `<div class="additional-defs"><h6 class="add-defs-title">Additional Definitions</h6>`+ 
		   `<small class="additional"><strong>• Strong's Definition</strong>: ${def.strongs_def}</small><br>`+
		   `<small class="additional"><strong>• King James Definition</strong>: ${def.kjv_def}</small></div>`;
}

function formatVariants(index, variants) {
	let header_tags = `<details class="variants-list"><summary class="variants-list-summary">Variants</summary>`;
	let list_tags = `<ol class="group-list">`
	for (let i = 0; i < variants.length; i++) {
		list_tags += `<li class="variant-item">${variants[i]}</li>`;
	}
	let end_tags = "</ul></details>"
	return header_tags +list_tags + end_tags;
}

function attachListListeners() {
	const headers = document.getElementsByTagName("summary");
	for (let i = 0; i < headers.length; i++) {
		headers[i].addEventListener("click", function() {
			let list = this.nextElementSibling;
			console.log(list.tagName);
			if (list && list.tagName === "UL") {
				if (list.style.display === "none") {
					list.style.display = "block";
				} else {
					list.style.display = "none";
				}
			}
		});
	}
}