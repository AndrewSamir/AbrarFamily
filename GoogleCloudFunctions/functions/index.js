var functions = require('firebase-functions');
const admin = require('firebase-admin');
// const  fetch = require('node-fetch');


admin.initializeApp(functions.config().firebase);

exports.setLastAbsentDate = functions.database
.ref('/fsol/{faslId}/data/absent/{absentId}').onWrite(event => {

  
      var eventSnapshot = event.data;
      console.log('all data',eventSnapshot._newData);
      var testJson=eventSnapshot._newData;
      var obj=JSON.parse(testJson);
      console.log('absent data',obj.absent);
      var absentData=obj.absent;


      for(var i in absentData)
{
     var id = absentData[i];
      console.log('absent item',id);
      const myURI = admin.database().ref('/fsol/').child(event.params.faslId).child('/list/').child( id).child('/attendance/');
       myURI.set(event.params.absentId);
}

   });