var express = require('express');
var http = require('http');
var url = require('url');
var bodyParser = require('body-parser');
var app = express();
var pg = require('pg');
var port = process.env.PORT || 5000
var fs = require('fs');
var parse = require('csv-parse');
var async = require('async');

app.use(bodyParser.urlencoded({extended:true}));
app.use(bodyParser.json());

app.all('*', function(req, res, next) {
  res.header('Access-Control-Allow-Origin', '*');
  res.header('Access-Control-Allow-Headers', 'X-Requested-With, Authorization, Content-Type');
  next();
});

app.listen(port);
console.log('Listening at ' + port);

var baseClient;
pg.connect(process.env.DATABASE_URL, function(err, client) {
	baseClient = client;
});

var records = [];

var inputFile='list.csv';

var parser = parse({delimiter: ','}, function (err, data) {
  async.eachSeries(data, function (line, callback) {
      // when processing finishes invoke the callback to move to the next one

      // records.push({
      // 	ucDirectory_UcListing_lblOwner: line[117] + " " + line[119],
      //   ucDirectory_UcListing_lblTitle: line[119],
      //   ucDirectory_UcListing_lblAddress1: line[35],
      //   ucDirectory_UcListing_lblCity: line[39],
      //   ucDirectory_UcListing_lblStateProvince: line[40],
      //   ucDirectory_UcListing_lblZipPostal: line[38],
      //   ucDirectory_UcListing_lblFax: line[25],
      //   ucDirectory_UcListing_lblPhone1: line[23],
      //   ucDirectory_UcListing_hlEmail: line[121],
      //   ucDirectory_UcListing_hlWebsite: line[30]
      // });

  	records.push({
      	ucdirectory_uclisting_lblowner: line[117] + " " + line[119],
        ucdirectory_uclisting_lbltitle: line[119],
        ucdirectory_uclisting_lbladdress1: line[35],
        ucdirectory_uclisting_lblcity: line[39],
        ucdirectory_uclisting_lblstateprovince: line[40],
        ucdirectory_uclisting_lblzippostal: line[38],
        ucdirectory_uclisting_lblfax: line[25],
        ucdirectory_uclisting_lblphone1: line[23],
        ucdirectory_uclisting_hlemail: line[121],
        ucdirectory_uclisting_hlwebsite: line[30]
      });
      // console.log(records.length)
      // if(line[121] == "bonniepalmer@msn.com"){
      
      // console.log(line[121])
  // }
      callback();
    // });
  });
}
);
fs.createReadStream(inputFile).pipe(parser);
function search(nameKey, myArray){
	var myNewArray = [];
    for (var i=1; i < myArray.length; i++) {
        // if (myArray[i].ucDirectory_UcListing_lblOwner.toLowerCase().indexOf(nameKey.toLowerCase()) > -1 ||
        // 	myArray[i].ucDirectory_UcListing_lblTitle.toLowerCase().indexOf(nameKey.toLowerCase()) > -1 ||
        // 	myArray[i].ucDirectory_UcListing_lblAddress1.toLowerCase().indexOf(nameKey.toLowerCase()) > -1 ||
        // 	myArray[i].ucDirectory_UcListing_lblCity.toLowerCase().indexOf(nameKey.toLowerCase()) > -1 ||
        // 	myArray[i].ucDirectory_UcListing_lblStateProvince.toLowerCase().indexOf(nameKey.toLowerCase()) > -1 ||
        // 	myArray[i].ucDirectory_UcListing_lblZipPostal.toLowerCase().indexOf(nameKey.toLowerCase()) > -1 ||
        // 	myArray[i].ucDirectory_UcListing_lblFax.toLowerCase().indexOf(nameKey.toLowerCase()) > -1 ||
        // 	myArray[i].ucDirectory_UcListing_lblPhone1.toLowerCase().indexOf(nameKey.toLowerCase()) > -1 ||
        // 	myArray[i].ucDirectory_UcListing_hlEmail.toLowerCase().indexOf(nameKey.toLowerCase()) > -1 ||
        // 	myArray[i].ucDirectory_UcListing_hlWebsite.toLowerCase().indexOf(nameKey.toLowerCase()) > -1

        // 	) {
        //     myNewArray.push(myArray[i]);
        // }

        if (myArray[i].ucdirectory_uclisting_lblowner.toLowerCase().indexOf(nameKey.toLowerCase()) > -1 ||
        	myArray[i].ucdirectory_uclisting_lbltitle.toLowerCase().indexOf(nameKey.toLowerCase()) > -1 ||
        	myArray[i].ucdirectory_uclisting_lbladdress1.toLowerCase().indexOf(nameKey.toLowerCase()) > -1 ||
        	myArray[i].ucdirectory_uclisting_lblcity.toLowerCase().indexOf(nameKey.toLowerCase()) > -1 ||
        	myArray[i].ucdirectory_uclisting_lblstateprovince.toLowerCase().indexOf(nameKey.toLowerCase()) > -1 ||
        	myArray[i].ucdirectory_uclisting_lblzippostal.toLowerCase().indexOf(nameKey.toLowerCase()) > -1 ||
        	myArray[i].ucdirectory_uclisting_lblfax.toLowerCase().indexOf(nameKey.toLowerCase()) > -1 ||
        	myArray[i].ucdirectory_uclisting_lblphone1.toLowerCase().indexOf(nameKey.toLowerCase()) > -1 ||
        	myArray[i].ucdirectory_uclisting_hlemail.toLowerCase().indexOf(nameKey.toLowerCase()) > -1 ||
        	myArray[i].ucdirectory_uclisting_hlwebsite.toLowerCase().indexOf(nameKey.toLowerCase()) > -1

        	) {
            myNewArray.push(myArray[i]);
        }
    }
    return myNewArray;
}

app.get('/', function (req, res) {
    
    if(req.query.q2 != undefined){

	    // connectionNode.query('SELECT * from main where ucDirectory_UcListing_lblOwner like "%?%";',
	    //     [req.param('q')], function (err, rows, fields) {
	    //     if (err) {
	    //         console.log(err);
	    //         res.json('error');
	    //     } else {
	    //         console.log(rows);
	    //         res.json(rows);
	    //     }
	    // });
		
		var currentQuery = 'SELECT * from main where ' + 
		'LOWER(ucDirectory_UcListing_lblOwner) like \'%' + req.query.q2 + '%\' OR ' + 
		'LOWER(ucDirectory_UcListing_lblTitle) like \'%' + req.query.q2 + '%\' OR ' + 
		'LOWER(ucDirectory_UcListing_lblAddress1) like \'%' + req.query.q2 + '%\' OR ' + 
		'LOWER(ucDirectory_UcListing_lblCity) like \'%' + req.query.q2 + '%\' OR ' + 
		'LOWER(ucDirectory_UcListing_lblStateProvince) like \'%' + req.query.q2 + '%\' OR ' + 
		'LOWER(ucDirectory_UcListing_lblZipPostal) like \'%' + req.query.q2 + '%\' OR ' + 
		'LOWER(ucDirectory_UcListing_lblFax) like \'%' + req.query.q2 + '%\' OR ' + 

		'LOWER(ucDirectory_UcListing_lblPhone1) like \'%' + req.query.q2 + '%\' OR ' + 
		'LOWER(ucDirectory_UcListing_hlEmail) like \'%' + req.query.q2 + '%\' OR ' + 
		'LOWER(ucDirectory_UcListing_hlWebsite) like \'%' + req.query.q2

		+ '%\' order by ucDirectory_UcListing_lblOwner';

	    var query = baseClient.query(currentQuery);
		// res.json(currentQuery);
		
		// query.on('row', function(row) {
		// 	res.json(row);
		// });

		query.on("row", function (row, result) {
		    result.addRow(row);
		});

		query.on("end", function (result) {
		    res.json(result.rows);
		    // baseClient.end();
		});


    }else if(req.query.q != undefined){
    	// res.send(req.query.q);	
    	res.json(search(req.query.q, records));
    }else {
    	res.send('No Support yet');	
    }
    // res.send('Hello World!');
    
});








