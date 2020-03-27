import 'package:http/http.dart' as http;

import 'dart:convert';
import 'dart:async';

import 'package:incities_ar/src/models/augmented_files_model.dart';

class AugmentedProvider {

  String _url      = 'api.myjson.com';

  Future<List<Book>> _procesarRespuesta(Uri url) async {
    
    final resp = await http.get(url);
    final decodedData = json.decode(resp.body);
    final books = new Books.fromJsonList(decodedData['results']);

    return books.items;
  }

  Future<List<Book>> searchBooks( String query ) async {

    final url = Uri.https(_url,'/bins/ae9tw');
    return await _procesarRespuesta(url);

  }

  Future<String> fetchCode(url) async {
    final resp = await http.get(url);//);
    final decodedData = json.decode(resp.body);
    print(decodedData['results']);
    return decodedData['results'];
  }
  
    Future<String> findCode(String code) {
    code = '1hjiq8';
    final url =  Uri.https(_url,'/bins/$code');
    return fetchCode(url);
  }

}