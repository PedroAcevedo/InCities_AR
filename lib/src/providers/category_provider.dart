import 'dart:convert';

import 'package:http/http.dart' as http;

class _CategoryProvider {

  List<dynamic> options = [];

  _CategoryProvider(){
    //loadData();
  }

  Future<List<dynamic>> loadData(Uri url) async {
    
    final resp = await http.get(url);
    Map dataMap  = json.decode(resp.body);
    final options = dataMap['categories'];
    return options;
  }

}

final menuProvider = new _CategoryProvider();