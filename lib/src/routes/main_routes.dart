import 'package:flutter/material.dart';
import 'package:incities_ar/src/pages/arkit_page.dart';

import 'package:incities_ar/src/pages/menu_page.dart';
import 'package:incities_ar/src/pages/search_page.dart';
import 'package:incities_ar/src/pages/arcode_page.dart';
import 'package:incities_ar/src/pages/setcode_page.dart';
import 'package:incities_ar/src/pages/welcome_page.dart';

Map<String, WidgetBuilder> getAplicationRoutes(){

    return <String, WidgetBuilder>{
      '/'       : ( BuildContext context ) => WelcomePage(),
      'setcode'       : ( BuildContext context ) => SetCodePage(),
      'menu'       : ( BuildContext context ) => MenuPage(),
      'search'   : ( BuildContext context ) => SearchPage(),
      'AndroidRA'   : ( BuildContext context ) => ARCodePage(), //ARCodePage(),
      'IOSRA'   : ( BuildContext context ) => MyApp(),
    };

}