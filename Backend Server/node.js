var express = require('express');
var http = require('http');
var url = require('url');
var bodyParser = require('body-parser');
var app = express();
var pg = require('pg');
var port = process.env.PORT || 5000

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


app.get('/', function (req, res) {
    
    if(req.param('q') != undefined){

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
		'LOWER(ucDirectory_UcListing_lblOwner) like \'%' + req.param('q') + '%\' OR ' + 
		'LOWER(ucDirectory_UcListing_lblTitle) like \'%' + req.param('q') + '%\' OR ' + 
		'LOWER(ucDirectory_UcListing_lblAddress1) like \'%' + req.param('q') + '%\' OR ' + 
		'LOWER(ucDirectory_UcListing_lblCity) like \'%' + req.param('q') + '%\' OR ' + 
		'LOWER(ucDirectory_UcListing_lblStateProvince) like \'%' + req.param('q') + '%\' OR ' + 
		'LOWER(ucDirectory_UcListing_lblZipPostal) like \'%' + req.param('q') + '%\' OR ' + 
		'LOWER(ucDirectory_UcListing_lblFax) like \'%' + req.param('q') + '%\' OR ' + 

		'LOWER(ucDirectory_UcListing_lblPhone1) like \'%' + req.param('q') + '%\' OR ' + 
		'LOWER(ucDirectory_UcListing_hlEmail) like \'%' + req.param('q') + '%\' OR ' + 
		'LOWER(ucDirectory_UcListing_hlWebsite) like \'%' + req.param('q')

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


    }else{
    	res.send('No Support yet');	
    }
    // res.send('Hello World!');
    
});










