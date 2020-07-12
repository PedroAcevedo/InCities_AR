import 'package:flutter/material.dart';

import 'package:incities_ar/src/pages/welcome_page.dart';
import 'package:incities_ar/src/pages/modules.dart';


Map<String, WidgetBuilder> getAplicationRoutes(){

    return <String, WidgetBuilder>{
      '/'       : ( BuildContext context ) => WelcomePage(),
      'modules'       : ( BuildContext context ) => ModulePage(),
    };

}