import 'package:http/http.dart' as http;

import 'dart:convert';
import 'dart:async';

import 'package:incities_ar/src/models/augmented_files_model.dart';

class AugmentedProvider {

  String _url = '10.0.2.2:8000'; // apiavas.dcm-system.co

  Future<List<Book>> _procesarRespuesta(Uri url) async {
    
    final resp = await http.get(url);
    final decodedData = json.decode(resp.body);
    final books = new Books.fromJsonList(decodedData['results']);

    return books.items;
  }

  Future<List<Book>> searchBooks( String query, int module) async {

    final url = Uri.http(_url,'/api/RAmaterial/$module/$query');///public
    print(url.toString());
    return await _procesarRespuesta(url);

  }

}