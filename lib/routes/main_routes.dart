import 'package:flutter/material.dart';

import 'package:incities_ar/pages/menu_page.dart';
import 'package:incities_ar/pages/search_page.dart';
import 'package:incities_ar/pages/arcode_page.dart';
import 'package:incities_ar/pages/setcode_page.dart';

Map<String, WidgetBuilder> getAplicationRoutes(){

    return <String, WidgetBuilder>{
      '/'       : ( BuildContext context ) => SetCodePage(),
      'menu'       : ( BuildContext context ) => MenuPage(),
      'search'   : ( BuildContext context ) => SearchPage(),
      'AndroidRA'   : ( BuildContext context ) => ARCodePage(),
      'IOSRA'   : ( BuildContext context ) => ARCodePage(),
    };


}