import 'package:http/http.dart' as http;

import 'dart:convert';
import 'dart:async';

import 'package:incities_ar/src/models/augmented_files_model.dart';

class AugmentedProvider {

  String _url      = 'jsonblob.com';

  Future<List<Book>> _procesarRespuesta(Uri url) async {
    
    final resp = await http.get(url);
    final decodedData = json.decode(resp.body);
    final books = new Books.fromJsonList(decodedData['results']);

    return books.items;
  }

  Future<List<Book>> searchBooks( String query ) async {

    final url = Uri.https(_url,'/api/jsonBlob/698572e0-79e0-11ea-94ef-235fd7ccc81a');
    return await _procesarRespuesta(url);

  }

  Future<String> fetchCode(url) async {
    final resp = await http.get(url);//);
    final decodedData = json.decode(resp.body);
    print(decodedData['status']);
    return '${decodedData['status']["code"]}';
  }
    //https://api.myjson.com/bins/1a3h1c
    Future<String> findCode(String code) {
    code = '060bdcfa-79df-11ea-94ef-a960415d712a';
    final url =  Uri.https(_url,'/api/jsonBlob/$code');
    return fetchCode(url);
  }

}