import 'dart:convert';

import 'package:flutter/services.dart' show rootBundle;

class _CategoryProvider {

  List<dynamic> options = [];

  _CategoryProvider(){
    //loadData();
  }

  Future<List<dynamic>> loadData() async {
    
    final response = await rootBundle.loadString('data/category_data.json');  
    Map dataMap = json.decode(response);
    options = dataMap['categories'];
    return options;
  }

}

final menuProvider = new _CategoryProvider();