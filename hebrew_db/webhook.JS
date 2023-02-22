/*
This webhook's purpose is to call the database and get words/definitions as inputted by the user.
Last Updated: 1/8/2021
By: Andrew Nieto
*/
// This function is the webhook's request handler.
exports = function(payload, response) {
    // Data can be extracted from the request as follows:
    const mongodb = context.services.get("mongodb-atlas");
    const hebrewCol = mongodb.db("hebrew").collection("hebrew");
    const projection = {_id : 0,
                        hebID : 0,
                        xlit: 0,
                        pron: 0
    };
    const sizeOfQuery = Object.keys(payload.query).length;
    const {word} = payload.query;
    if(sizeOfQuery != 1) {
      return {"status" : "fail", "msg" : "Only a word may be specified in the request-uri"};
    }
    if(!word) {
      return {"status" : "fail", "msg" : "A word must be specified in the request-uri"};
    }
    console.log(`User has attempted to find words similar to ${word}`);
    return hebrewCol
           .find({ $text: { $search: word} }, projection )
           .toArray();
};


//Function that determines whether a particular word is Hebrew
function isHebrewWord(str) {
  for(var index = 0; index < str.length; index++) {
    const curCharCode = str.charCodeAt(index);
    if(curCharCode < 1424 || curCharCode > 1535 ) {
      return false;
    }
  }
  return true;
}

//Function that determines whether a particular word is Hebrew
function isEnglishWord(str) {
  return /^[a-zA-Z]+$/.test(str)
}